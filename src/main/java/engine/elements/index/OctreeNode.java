package engine.elements.index;

import engine.DBApp;
import engine.elements.Page;
import engine.elements.Record;
import engine.elements.Table;
import engine.operations.selection.TableRecordInfo;
import utilities.PropertiesReader;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.serialization.Serializer;

import java.io.Serializable;
import java.util.*;

import static engine.elements.index.OctreeLocations.*;


class OctreeNode implements Serializable {
    private static final int maxEntriesPerNode = Integer.parseInt(PropertiesReader.getProperty("MaximumEntriesinOctreeNode"));

    private final Boundary boundary;

    private List<TableRecordInfo> entries;

    private final OctreeNode[] children;

    OctreeNode(Boundary boundary) {
        this.boundary = boundary;
        entries = new Vector<>(maxEntriesPerNode);
        this.children = new OctreeNode[8];
    }

    List<TableRecordInfo> getEntries() {
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
        int total= 0;
        for(OctreeNode node: children) {
            total += node.getEntries().size();
        }
        return total <= maxEntriesPerNode;
    }
    boolean isHavingLeafChildren() {
        for(OctreeNode child: children) {
            if(! child.isALeaf()) {
                return false;
            }
        }
        return true;
    }
    void addEntry(TableRecordInfo entry) {
        entries.add(entry);
    }

    private Hashtable<String, Comparable> getBound(List<String> indexedColumnNames, List<BoundRange> boundRanges) {
        Hashtable<String, Comparable> colNameBound = new Hashtable<>();
        for(int i = 0; i < indexedColumnNames.size(); i++) {
            String columnName = indexedColumnNames.get(i);
            Comparable columnMinValue = boundary.getLowBound().get(columnName);
            Comparable columnMaxValue = boundary.getHighBound().get(columnName);
            BoundRange range = boundRanges.get(i);
            switch(range) {
                case min: colNameBound.put(columnName, columnMinValue); break;
                case middle: colNameBound.put(columnName, DatabaseTypesHandler.getMiddleValue(columnMinValue, columnMaxValue)); break;
                case max: colNameBound.put(columnName, columnMaxValue);
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

        children[topNorthWest.getPosition()] = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.middle, BoundRange.min))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.max, BoundRange.max, BoundRange.middle))));

        children[topNorthEast.getPosition()]  = new OctreeNode(new Boundary(getBound(indexedColumnNames, Arrays.asList(BoundRange.middle, BoundRange.middle, BoundRange.middle))
                , getBound(indexedColumnNames, Arrays.asList(BoundRange.max, BoundRange.max, BoundRange.max))));
    }

    void distributeDataInChildren(Table tableContainingIndex) {
        for(TableRecordInfo entry: entries) {
            Record record = Record.getRecord(tableContainingIndex, entry);
            for(OctreeNode child: children) {
                if(child.getBoundary().isRecordInBounds(record)) {
                    child.addEntry(entry);
                    if(child.isOverFlown()) {
                        child.distributeDataInChildren(tableContainingIndex);
                    }
                    break;
                }
            }
        }
        entries = null;
    }
    List<TableRecordInfo> deleteMatchingEntries(Table tableContainingIndex, Hashtable<String, Object> recordToDelete) {
        List<TableRecordInfo> deletedRecordsInfo = new ArrayList<>();
        Hashtable<Integer, List<Integer>> groupedRecordsReferences = getSameGroupedRecordsReferences();
        for(int pageInfoIndex: groupedRecordsReferences.keySet()) {
            Page page = Page.deserializePage(tableContainingIndex.getPagesInfo().get(pageInfoIndex));
            for(int recordIndexInPage: groupedRecordsReferences.get(pageInfoIndex)) {
                Record record = page.get(recordIndexInPage);
                if(record.hasMatchingValues(recordToDelete)) {
                    page.removeRecord(recordIndexInPage, tableContainingIndex.getClusteringKey());
                    TableRecordInfo deletedRecordInfo = new TableRecordInfo(pageInfoIndex, recordIndexInPage);
                    entries.remove(deletedRecordInfo);
                    deletedRecordsInfo.add(deletedRecordInfo);
                }
            }
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        return deletedRecordsInfo;
    }
    private Hashtable<Integer, List<Integer>> getSameGroupedRecordsReferences() {
        Hashtable<Integer, List<Integer>> groupedPageReferences = new Hashtable<>();
        for(TableRecordInfo recordInfo: entries) {
            int pageInfoIndex = recordInfo.getPageInfoIndex(), recordIndexInPage = recordInfo.getRecordIndexInPage();
            if(groupedPageReferences.containsKey(pageInfoIndex)) {
                groupedPageReferences.get(pageInfoIndex).add(recordIndexInPage);
            }
            else {
                groupedPageReferences.put(pageInfoIndex, new ArrayList<>());
            }
        }
        return groupedPageReferences;
    }
    void nullifyChildren() {
        Arrays.fill(children, null);
    }
    void distributeDataFromChildren() {
        entries = new Vector<>(maxEntriesPerNode);
        for(OctreeNode child: children) {
            entries.addAll(child.entries);
        }
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
