package engine.elements.index;


import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import engine.operations.selection.SelectFromTableParams;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("rawtypes")
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
    public void insert(Table table, IndexRecordInfo recordInfo) throws DBAppException {
        if(root == null) {
            root = new OctreeNode(treeEntireBoundary);
        }
        insertionHelper(table, root, recordInfo);
    }

    private void insertionHelper(Table table, OctreeNode node, IndexRecordInfo recordInfo) throws DBAppException {
        Record record = Record.getRecord(table, recordInfo);
        while (true) {
            if (node.isALeaf()) {
                node.addRecord(table, recordInfo);
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


    public int deleteMatchingRecords(Table table, Hashtable<String, Object> recordToDelete, boolean toDeleteTableRecords) {
        if(root == null) {
            return 0;
        }
        int deletedRecords = deletionHelper(table, root, recordToDelete, toDeleteTableRecords);
        if(root.getEntries() != null && root.getEntries().isEmpty()) {
            root = null;
        }
        return deletedRecords;
    }

    private int deletionHelper(Table table, OctreeNode node, Hashtable<String, Object> recordToDelete, boolean toDeleteTableRecords) {
        int deletedRecords = 0;
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
                     deletedRecords += child.deleteMatchingEntries(table, recordToDelete, toDeleteTableRecords, this);
                }
            }
            if (currentNode.isHavingLeafChildren() && currentNode.isUnderFlown()) {
                currentNode.nullifyChildren();
            }
        }
        return deletedRecords;
    }
    public void deleteAllEntries() {
        root = null;
    }
    public int updateRecordWithPrimaryKey(Table table, Comparable clusteringKeyValue, Hashtable<String, Object> colNameValue) throws DBAppException {
        if(root == null) {
            return 0;
        }
        int requiredPageIndex = table.findPageIndexToLookIn(clusteringKeyValue);
        if(requiredPageIndex == -1) {
            return 0;
        }
        PageMetaInfo pageMetaInfo = table.getPagesInfo().get(requiredPageIndex);
        Page page = Page.deserializePage(pageMetaInfo);
        int requiredRecordIndex = page.findRecordIndex(table.getClusteringKey(), clusteringKeyValue);
        if(requiredRecordIndex == -1) {
            return 0;
        }
        Record recordToDelete = page.get(requiredRecordIndex);
        deleteMatchingRecords(table, recordToDelete, false);
        recordToDelete.updateValues(colNameValue);
        Serializer.serialize(pageMetaInfo.getLocation(), page);
        insert(table, new IndexRecordInfo((Comparable) recordToDelete.get(table.getClusteringKey()), requiredPageIndex));
        return 1;
    }
    public int updateAllRecords(Table table) throws DBAppException {
        if(root == null) {
            return 0;
        }
        deleteAllEntries();
        int updatedRecords = 0;
        for(int i = 0; i < table.getPagesInfo().size(); i++) {
            PageMetaInfo pageMetaInfo = table.getPagesInfo().get(i);
            Page page = Page.deserializePage(pageMetaInfo);
            for(Record record: page) {
                insert(table, new IndexRecordInfo((Comparable) record.get(table.getClusteringKey()), i));
            }
            updatedRecords += page.size();
            Serializer.serialize(pageMetaInfo.getLocation(), page);
        }
        return updatedRecords;
    }
    public void updateRecordPageNumber(Table table, Record record, int newPageNumber) {
        if(root == null) {
            return;
        }
        OctreeNode currentNode = root;
        while(true) {
            if(currentNode.isALeaf()) {
                currentNode.updateRecordInfo(table, record, newPageNumber);
                return;
            }
            boolean childFound = false;
            for(OctreeNode child: currentNode.getChildren()) {
                if(child.getBoundary().isRecordInBounds(record)) {
                    currentNode = child;
                    childFound = true;
                    break;
                }
            }
            if(! childFound) {
                break;
            }
        }
    }
    public Iterator<Record> getMatchingRecordsIterator(Table table, SelectFromTableParams params) {
        if(root == null) {
            return Collections.emptyIterator();
        }
        List<Record> selectedRecords = new Vector<>();
        Stack<OctreeNode> stack = new Stack<>();
        stack.push(root);
        while(! stack.isEmpty()) {
            OctreeNode currentNode = stack.pop();
            if(currentNode.isALeaf()) {
                selectedRecords.addAll(currentNode.getMatchingRecordsIterator(table, params));
                continue;
            }
            for(OctreeNode child: currentNode.getChildren()) {
                if(child.getBoundary().isSqlTermsInBounds(params)) {
                    stack.push(child);
                }
            }
        }
        return selectedRecords.iterator();
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

