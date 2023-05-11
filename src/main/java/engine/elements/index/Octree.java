package engine.elements.index;


import engine.elements.Record;
import engine.elements.Table;
import engine.operations.selection.TableRecordInfo;
import utilities.serialization.Deserializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Octree implements Serializable {

    private OctreeNode root;
    private final Boundary treeEntireBoundary;
    private IndexMetaInfo indexMetaInfo;
    public Octree(IndexMetaInfo indexMetaInfo, Boundary boundary) {
        this.indexMetaInfo = indexMetaInfo;
        this.treeEntireBoundary = boundary;
    }

    public IndexMetaInfo getIndexMetaInfo() {
        return indexMetaInfo;
    }

    public void setIndexMetaInfo(IndexMetaInfo indexMetaInfo) {
        this.indexMetaInfo = indexMetaInfo;
    }

    public static Octree deserializeIndex(IndexMetaInfo indexMetaInfo) {
        Octree index = (Octree) Deserializer.deserialize(indexMetaInfo.getIndexFileLocation());
        index.setIndexMetaInfo(indexMetaInfo);
        return index;
    }
    public void insert(Table table, TableRecordInfo entry) {
        if(root == null) {
            root = new OctreeNode(treeEntireBoundary);
        }
        insertRecursively(table, root, entry);
    }

    private void insertRecursively(Table table, OctreeNode node, TableRecordInfo entry) {
        if(node.isALeaf()) {
            node.addEntry(entry);
            if(node.isOverFlown()) {
                node.initializeChildren();
                node.distributeDataInChildren(table);
            }
            return;
        }
        Record record = Record.getRecord(table, entry);
        for(OctreeNode child: node.getChildren()) {
            if(child.getBoundary().isRecordInBounds(record)) {
                insertRecursively(table, child, entry);
                break;
            }
        }
    }


    public List<TableRecordInfo> delete(Table table, Hashtable<String, Object> recordToDelete) {
        if(root == null) {
            return new ArrayList<>();
        }
        List<TableRecordInfo> deletedRecordsInfo;
        if(root.isALeaf()) {
             deletedRecordsInfo = root.deleteMatchingEntries(table, recordToDelete);
        }
        else {
            deletedRecordsInfo = deleteRecursively(table, root, recordToDelete);
        }
        if(root.getEntries().isEmpty()) {
            root = null;
        }
        return deletedRecordsInfo;
    }

    private List<TableRecordInfo> deleteRecursively(Table table, OctreeNode node, Hashtable<String, Object> recordToDelete) {
        List<TableRecordInfo> deletedRecordsInfo = new ArrayList<>();
        for(OctreeNode child: node.getChildren()) {
            if(! child.getBoundary().isRecordInBounds((Record) recordToDelete)) {
                continue;
            }
            if(! child.isALeaf()) {
                deletedRecordsInfo.addAll(deleteRecursively(table, child, recordToDelete));
            }
            else {
                deletedRecordsInfo.addAll(child.deleteMatchingEntries(table, recordToDelete));
            }
        }
        if(node.isHavingLeafChildren() && node.isUnderFlown()) {
            node.distributeDataFromChildren();
            node.nullifyChildren();
        }
        return deletedRecordsInfo;
    }
    public List<TableRecordInfo> deleteAllRecords() {
        if(root == null) {
            return new ArrayList<>();
        }
        List<TableRecordInfo> deletedRecords = deleteAllRecordsRecursively(root);
        root = null;
        return deletedRecords;
    }
    private List<TableRecordInfo> deleteAllRecordsRecursively(OctreeNode node) {
        List<TableRecordInfo> deletedRecordsInfo = new ArrayList<>();
        if(node.isALeaf()) {
            deletedRecordsInfo.addAll(node.getEntries());
            node.getEntries().clear();
            return deletedRecordsInfo;
        }
        else {
            for(OctreeNode child: node.getChildren()) {
                deletedRecordsInfo.addAll(deleteAllRecordsRecursively(child));
            }
        }
        return deletedRecordsInfo;
    }

    public int refreshRecordWithPrimaryKey(Table table, Comparable clusteringKeyValue) {
        if(root == null) {
            return 0;
        }
        Hashtable<String, Object> recordToDelete = new Hashtable<>();
        recordToDelete.put(table.getClusteringKey(), clusteringKeyValue);
        List<TableRecordInfo> recordsInfoToBeInserted = delete(table, recordToDelete);
        for(TableRecordInfo recordInfo: recordsInfoToBeInserted) {
            insert(table, recordInfo);
        }
        return recordsInfoToBeInserted.size();
    }
    public int refreshAllRecords(Table table) {
        List<TableRecordInfo> deletedRecords = deleteAllRecords();
        for(TableRecordInfo recordInfo: deletedRecords) {
            insert(table, recordInfo);
        }
        return deletedRecords.size();
    }

    @Override
    public String toString() {
        return "Octree{" +
                "root=" + root +
                ", treeEntireBoundary=" + treeEntireBoundary +
                '}';
    }
}

