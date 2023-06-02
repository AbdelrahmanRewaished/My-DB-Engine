package engine.elements.index;

import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import engine.operations.deletion.Deletion;
import engine.operations.selection.SelectFromTableParams;
import utilities.FileHandler;
import utilities.PropertiesReader;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.serialization.Serializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static engine.elements.index.OctreeLocations.*;


@SuppressWarnings("rawtypes")
class OctreeNode implements Serializable {
    private static final int maxEntriesPerNode = Integer.parseInt(PropertiesReader.getProperty("MaximumEntriesinOctreeNode"));

    private final Boundary boundary;

    private List<IndexRecordsInfo> entries;

    private final OctreeNode[] children;

    @Serial
    private static final long serialVersionUID = 166228508102341865L;

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
    void addRecord(Table table, IndexRecordInfo recordInfo) {
        for(IndexRecordsInfo entry: entries) {
           if(entry.contains(table, recordInfo)) {
               entry.add(recordInfo);
               return;
           }
        }
        IndexRecordsInfo recordsInfo = new IndexRecordsInfo();
        recordsInfo.add(recordInfo);
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

    private class Point {
        private int recordsInfoIndex, recordInfoIndex;
        private int pageNumber;

        public Point(int recordsInfoIndex, int recordInfoIndex, int pageNumber) {
            this.recordsInfoIndex = recordsInfoIndex;
            this.recordInfoIndex = recordInfoIndex;
            this.pageNumber = pageNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point point)) return false;
            return pageNumber == point.pageNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pageNumber);
        }
    }
    private Hashtable<Point, Set<Comparable>> getCombinedPagesInfoKeys() {
        Hashtable<Point, Set<Comparable>> combinedPagesInfoKeys = new Hashtable<>();
        for(int i = 0; i < entries.size(); i++) {
            IndexRecordsInfo recordsInfo = entries.get(i);
            for(int j = 0; j < recordsInfo.size(); j++) {
                IndexRecordInfo recordInfo = recordsInfo.get(j);
                Point dummyPoint = new Point(-1, -1, recordInfo.getPageNumber());
                if(! combinedPagesInfoKeys.containsKey(dummyPoint)) {
                    Set<Comparable> set = new HashSet<>();
                    combinedPagesInfoKeys.put(new Point(i, j, recordInfo.getPageNumber()), set);
                }
                combinedPagesInfoKeys.get(dummyPoint).add(recordInfo.getClusteringKeyValue());
            }
        }
        return combinedPagesInfoKeys;
    }
    int deleteMatchingEntries(Table tableContainingIndex, Hashtable<String, Object> recordToDelete) {
        int deletedRecords = 0;
        Hashtable<Point, Set<Comparable>> combinedPagesInfoKeys = getCombinedPagesInfoKeys();
        for(Point point: combinedPagesInfoKeys.keySet()) {
            Page page = Page.deserializePage(tableContainingIndex.getPagesInfo().get(point.pageNumber));
            for(Comparable clusteringKeyValue: combinedPagesInfoKeys.get(point)) {
                int recordIndexInPage = page.findRecordIndex(tableContainingIndex.getClusteringKey(), clusteringKeyValue);
                Record record = page.get(recordIndexInPage);
                if(! record.hasMatchingValues(recordToDelete)) {
                    continue;
                }
                entries.get(point.recordsInfoIndex).remove(point.recordsInfoIndex);
                deletedRecords++;
                if(entries.get(point.recordsInfoIndex).isEmpty()) {
                    entries.remove(point.recordsInfoIndex);
                }
            }
        }
        return deletedRecords;
    }
    void updateAllRecordsPageNumber(int startingOverflownPageNumber) {
        for(IndexRecordsInfo entry: entries) {
            for(IndexRecordInfo recordInfo: entry) {
                if(recordInfo.getPageNumber() > startingOverflownPageNumber) {
                    recordInfo.setPageNumber(recordInfo.getPageNumber() - 1);
                }
            }
        }
    }
    void updateRecordPageNumber(String tableClusteringKey, Record record, int newPageNumber) {
        for(IndexRecordsInfo entry: entries) {
            for(IndexRecordInfo recordInfo: entry) {
                if(record.get(tableClusteringKey).equals(recordInfo.getClusteringKeyValue())) {
                    recordInfo.setPageNumber(newPageNumber);
                }
            }
        }
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
    private Hashtable<Integer, Set<Comparable>> getCombinedPagesKeys() {
        Hashtable<Integer, Set<Comparable>> combinedPageKeys = new Hashtable<>();
        for(IndexRecordsInfo entry: entries) {
            for(IndexRecordInfo recordInfo: entry) {
                if(! combinedPageKeys.containsKey(recordInfo.getPageNumber())) {
                    Set<Comparable> keys = new HashSet<>();
                    combinedPageKeys.put(recordInfo.getPageNumber(), keys);
                }
                combinedPageKeys.get(recordInfo.getPageNumber()).add(recordInfo.getClusteringKeyValue());
            }
        }
        return combinedPageKeys;
    }

    List<Record> getMatchingRecordsIterator(Table table, SelectFromTableParams params) {
        List<Record> matchingRecords = new ArrayList<>();
        Hashtable<Integer, Set<Comparable>> combinedPagesKeys = getCombinedPagesKeys();
        for(int pageNumber: combinedPagesKeys.keySet()) {
            Page page = Page.deserializePage(table.getPagesInfo().get(pageNumber));
            Set<Comparable> currentPageKeys = combinedPagesKeys.get(pageNumber);
            for(Comparable clusteringKeyValue: currentPageKeys) {
                int recordIndexInPage = page.findRecordIndex(table.getClusteringKey(), clusteringKeyValue);
                Record record = page.get(recordIndexInPage);
                if(record.isMatchingRecord(params)) {
                    matchingRecords.add(record);
                }
            }
        }
        return matchingRecords;
    }
}
