package engine.elements.index;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.FileHandler;
import utilities.PropertiesReader;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.serialization.Serializer;

import java.io.Serializable;
import java.util.*;

import static engine.elements.index.OctreeLocations.*;


class OctreeNode implements Serializable {
    private static final int maxEntriesPerNode = Integer.parseInt(PropertiesReader.getProperty("MaximumEntriesinOctreeNode"));

    private final Boundary boundary;

    private List<IndexRecordsInfo> entries;

    private final OctreeNode[] children;

    OctreeNode(Boundary boundary) {
        this.boundary = boundary;
        entries = new Vector<>(maxEntriesPerNode);
        this.children = new OctreeNode[8];
    }

    List<IndexRecordsInfo> getEntries() {
        return entries;
    }

    OctreeNode[] getChildren() {
        return children;
    }

    Boundary getBoundary() {
        return boundary;
    }

    boolean isALeaf() {
        for(OctreeNode child: children) {
            if(child != null) {
                return false;
            }
        }
        return true;
    }
    boolean isOverFlown() {
        return entries.size() > maxEntriesPerNode;
    }
    boolean isUnderFlown() {
       for(OctreeNode child: children) {
           if(! child.getEntries().isEmpty()) {
               return false;
           }
       }
       return true;
    }
    boolean isHavingLeafChildren() {
        for(OctreeNode child: children) {
            if(child != null && ! child.isALeaf()) {
                return false;
            }
        }
        return true;
    }
    void addRecord(Table table, Record record) {
        for(IndexRecordsInfo entry: entries) {
           if(entry.contains(table, record)) {
               entry.add(record);
               return;
           }
        }
        IndexRecordsInfo recordsInfo = new IndexRecordsInfo();
        recordsInfo.add(record);
        entries.add(recordsInfo);
    }
    void addEntry(IndexRecordsInfo entry) {
        entries.add(entry);
    }

    private Hashtable<String, Comparable> getBound(List<String> indexedColumnNames, List<BoundRange> boundRanges) {
        Hashtable<String, Comparable> colNameBound = new Hashtable<>();
        for(int i = 0; i < indexedColumnNames.size(); i++) {
            String columnName = indexedColumnNames.get(i);
            Comparable columnMinValue = boundary.getLowBound().get(columnName);
            Comparable columnMaxValue = boundary.getHighBound().get(columnName);
            BoundRange range = boundRanges.get(i);
            switch (range) {
                case min -> colNameBound.put(columnName, columnMinValue);
                case middle ->
                        colNameBound.put(columnName, DatabaseTypesHandler.getMiddleValue(columnMinValue, columnMaxValue));
                case max -> colNameBound.put(columnName, columnMaxValue);
            }
        }
        return colNameBound;
    }
    void initializeChildren() {
        List<String> indexedColumnNames = new ArrayList<>(boundary.getHighBound().keySet());

        children[bottomSouthWest.getPosition()] = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.min, BoundRange.min, BoundRange.min))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.middle, BoundRange.middle))));

        children[bottomSouthEast.getPosition()]  = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.min, BoundRange.middle, BoundRange.min))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.max, BoundRange.middle))));

        children[bottomNorthWest.getPosition()]  = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.min, BoundRange.min))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.max, BoundRange.middle, BoundRange.middle))));

        children[bottomNorthEast.getPosition()]  = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.middle, BoundRange.min))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.max, BoundRange.max, BoundRange.middle))));

        children[topSouthWest.getPosition()] = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.min, BoundRange.min, BoundRange.middle))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.middle, BoundRange.max))));

        children[topSouthEast.getPosition()] = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.min, BoundRange.middle, BoundRange.middle))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.max, BoundRange.max))));

        children[topNorthWest.getPosition()] = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.min, BoundRange.middle))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.max, BoundRange.middle, BoundRange.max))));

        children[topNorthEast.getPosition()]  = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.middle, BoundRange.middle))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.max, BoundRange.max, BoundRange.max))));
    }
    void distributeDataInChildren(Table tableContainingIndex) throws DBAppException {
        Stack<OctreeNode> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            OctreeNode node = stack.pop();
            for (IndexRecordsInfo entry : node.entries) {
                Record record = entry.getMaxValuesRecord(tableContainingIndex);
                for (OctreeNode child : node.children) {
                    if (child.getBoundary().isRecordInBounds(record)) {
                        child.addEntry(entry);
                        if (child.isOverFlown()) {
                            child.initializeChildren();
                            stack.push(child);
                        }
                        break;
                    }
                }
            }
            node.entries = null;
        }
    }
    private void deleteTableRecords(Table tableContainingIndex, Record record) {
        Comparable clusteringKeyValue = (Comparable) record.get(tableContainingIndex.getClusteringKey());
        int requiredPageInfoIndex = tableContainingIndex.findPageIndexToLookIn(clusteringKeyValue);
        PageMetaInfo pageMetaInfo = tableContainingIndex.getPagesInfo().get(requiredPageInfoIndex);
        Page page = Page.deserializePage(pageMetaInfo);
        int requiredRecordIndexInPage = page.findRecordIndex(tableContainingIndex.getClusteringKey(), clusteringKeyValue);
        page.removeRecord(requiredRecordIndexInPage, tableContainingIndex.getClusteringKey());
        if(! page.isEmpty()) {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        else {
            tableContainingIndex.removePageInfo(requiredPageInfoIndex);
            FileHandler.deleteFile(pageMetaInfo.getLocation());
        }
    }
    List<Record> deleteMatchingEntries(Table tableContainingIndex, Hashtable<String, Object> recordToDelete, boolean toDeleteTableRecords) {
        List<Record> deletedRecords = new ArrayList<>();
        for(int i = 0; i < entries.size(); ) {
            IndexRecordsInfo entry = entries.get(i);
            for(int j = 0; j < entry.size(); ) {
                Record record = entry.get(j);
                if(! record.hasMatchingValues(recordToDelete)) {
                    j++;
                    continue;
                }
                deletedRecords.add(entry.remove(j));
                if(toDeleteTableRecords) {
                    deleteTableRecords(tableContainingIndex, record);
                }
            }
            if(entry.isEmpty()) {
                entries.remove(i);
            }
            else {
                i++;
            }
        }
        return deletedRecords;
    }
    void nullifyChildren() {
        entries = new ArrayList<>(maxEntriesPerNode);
        Arrays.fill(children, null);
    }
    @Override
    public String toString() {
        return "OctreeNode{" +
                "boundary=" + boundary +
                ", entries=" + entries +
                ", children=" + Arrays.toString(children) +
                '}';
    }


}
