package engine.elements.index;


import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

public class Octree implements Serializable {

    private OctreeNode root;
    private final Boundary treeEntireBoundary;
    private IndexMetaInfo indexMetaInfo;
    public Octree(IndexMetaInfo indexMetaInfo, Boundary boundary) {
        this.indexMetaInfo = indexMetaInfo;
        this.treeEntireBoundary = boundary;
    }
    @Serial
    private static final long serialVersionUID = 252701850935188606L;
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
    public void insert(Table table, Record record) throws DBAppException {
        if(root == null) {
            root = new OctreeNode(treeEntireBoundary);
        }
        insertionHelper(table, root, record);
    }

    private void insertionHelper(Table table, OctreeNode node, Record record) throws DBAppException {
        while (true) {
            if (node.isALeaf()) {
                node.addRecord(table, record);
                if (node.isOverFlown()) {
                    node.initializeChildren();
                    node.distributeDataInChildren(table);
                }
                return;
            }
            boolean foundChild = false;
            for (OctreeNode child : node.getChildren()) {
                if (child.getBoundary().isRecordInBounds(record)) {
                    node = child;
                    foundChild = true;
                    break;
                }
            }
            if (! foundChild) {
                return;
            }
        }

    }


    public List<Record> deleteMatchingRecords(Table table, Hashtable<String, Object> recordToDelete, boolean toDeleteTableRecords) {
        if(root == null) {
            return new ArrayList<>();
        }
        List<Record> deletedRecords;
        if(root.isALeaf()) {
             deletedRecords = root.deleteMatchingEntries(table, recordToDelete, toDeleteTableRecords);
        }
        else {
            deletedRecords = deletionHelper(table, root, recordToDelete, toDeleteTableRecords);
        }
        if(root.getEntries() != null && root.getEntries().isEmpty()) {
            root = null;
        }
        return deletedRecords;
    }

    private List<Record> deletionHelper(Table table, OctreeNode node, Hashtable<String, Object> recordToDelete, boolean toDeleteTableRecords) {
        List<Record> deletedRecordsInfo = new ArrayList<>();
        Stack<OctreeNode> stack = new Stack<>();
        stack.push(node);
        while(! stack.isEmpty()) {
            OctreeNode currentNode = stack.pop();
            for (OctreeNode child : currentNode.getChildren()) {
                if (!child.getBoundary().isRecordInBounds(recordToDelete)) {
                    continue;
                }
                if (!child.isALeaf()) {
                    stack.push(child);
                } else if(! child.getEntries().isEmpty()){
                     deletedRecordsInfo.addAll(child.deleteMatchingEntries(table, recordToDelete, toDeleteTableRecords));
                }
            }
            if (currentNode.isHavingLeafChildren() && currentNode.isUnderFlown()) {
                currentNode.nullifyChildren();
            }
        }
        return deletedRecordsInfo;
    }
    public List<IndexRecordsInfo> deleteAllEntries() {
        if(root == null) {
            return new ArrayList<>();
        }
        List<IndexRecordsInfo> deletedRecords = deleteAllEntriesHelper(root);
        root = null;
        return deletedRecords;
    }
    private List<IndexRecordsInfo> deleteAllEntriesHelper(OctreeNode node) {
        List<IndexRecordsInfo> deletedRecordsInfo = new ArrayList<>();
        Stack<OctreeNode> stack = new Stack<>();
        stack.push(node);
        while(!stack.isEmpty()) {
            OctreeNode currentNode = stack.pop();
            if(currentNode.isALeaf()) {
                deletedRecordsInfo.addAll(currentNode.getEntries());
                currentNode.getEntries().clear();
            }
            else {
                for(OctreeNode child: currentNode.getChildren()) {
                    stack.push(child);
                }
            }
        }
        return deletedRecordsInfo;
    }

    public void refreshRecordWithPrimaryKey(Table table, Comparable clusteringKeyValue, Hashtable<String, Object> colNameValue) throws DBAppException {
        if(root == null) {
            return;
        }
        Hashtable<String, Object> recordToDelete = new Hashtable<>();
        recordToDelete.put(table.getClusteringKey(), clusteringKeyValue);
        List<Record> recordsInfoToBeInserted = deleteMatchingRecords(table, recordToDelete, false);
        for(Record record: recordsInfoToBeInserted) {
            record.updateValues(colNameValue);
        }
        for(Record record: recordsInfoToBeInserted) {
            insert(table, record);
        }
    }
    public void refreshAllRecords(Table table, Hashtable<String, Object> colNameValue) throws DBAppException {
        if(root == null) {
            return;
        }
        List<IndexRecordsInfo> deletedRecords = deleteAllEntries();
        for(IndexRecordsInfo indexRecordsInfo: deletedRecords) {
            for(Record record: indexRecordsInfo) {
                record.updateValues(colNameValue);
                insert(table, record);
            }
        }
    }
    public boolean find(Comparable element) {
        if(root == null) {
            return false;
        }
        Stack<OctreeNode> stack = new Stack<>();
        stack.push(root);
        while(! stack.isEmpty()) {
            OctreeNode curr = stack.pop();
            if (! curr.isALeaf()) {
                for(OctreeNode child: curr.getChildren()) {
                    stack.push(child);
                }
            }
            else {
                for(IndexRecordsInfo entry: curr.getEntries()) {
                    if(entry.contains(element)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Octree{" +
                "root=" + root +
                ", treeEntireBoundary=" + treeEntireBoundary +
                '}';
    }
    public void printLeafs() {
        if(root == null) {
            System.out.println("Octree is Empty");
            return;
        }
        Stack<OctreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            OctreeNode current = stack.pop();
            if(current.isALeaf() && ! current.getEntries().isEmpty()) {
                System.out.print(current.getEntries() + " -> ");
            }
            else {
                for(OctreeNode child: current.getChildren()) {
                    if(child != null) {
                        stack.push(child);
                    }
                }
            }
        }
        System.out.println();
    }
}

