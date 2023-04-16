package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import engine.elements.PageInfo;
import engine.elements.Table;
import engine.elements.Record;
import engine.operations.paramters.InsertIntoTableParams;
import utilities.validation.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class Insertion {
    private InsertIntoTableParams params;
    private String tableName;
    private Record record;
    public Insertion(InsertIntoTableParams params) {
        this.params = params;
        tableName = params.getTableName();
        record = params.getRecord();
    }

    public int getRequiredRecordIndex(Page page, String clusteringKey, Object keyValue) {
        int left = 0, right = page.size() - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            Hashtable<String, Object> currentRecord = page.get(mid);
            Comparable currentClusteringKey = (Comparable) currentRecord.get(clusteringKey);
            if(currentClusteringKey.compareTo(keyValue) > 0) {
                right = mid - 1;
            }
            else if(currentClusteringKey.compareTo(keyValue) < 0) {
                left = mid + 1;
            }
            else {
                return -1;
            }
        }
        return left;
    }
    public int getRequiredPageInfoIndex(Table table, Object keyValue) {
        List<PageInfo> pages = table.getPagesInfo();
        int left = 0, right = pages.size() - 1;
        int mid = 0;
        while(left <= right) {
            mid = (left + right) / 2;
            PageInfo currentPage = pages.get(mid);
            Comparable min = currentPage.getMinimumContainedKey();
            Comparable max = currentPage.getMaximumContainedKey();
            if(min.compareTo(keyValue) > 0) {
                right = mid - 1;
            }
            else if(max.compareTo(keyValue) < 0 && currentPage.isFull()) {
                left = mid + 1;
            }
            else if(max.compareTo(keyValue) < 0) {
                return mid;
            }
            else if(max.compareTo(keyValue) == 0 || min.compareTo(keyValue) == 0) {
                return -1;
            }
            else {
                return mid;
            }
        }
        if(left > 0) {
            return left;
        }
        if(left == 0) {
            return mid;
        }
        return left;
    }

    private String getNextPageFileLocation(List<PageInfo> pagesInfo) {
        if(pagesInfo.isEmpty()) {
            return "/0.txt";
        }
        String[] splitter = pagesInfo.get(pagesInfo.size() - 1).getLocation().split("/");
        int lastPageNumber = Integer.parseInt(splitter[splitter.length - 1].split(".txt")[0]);
        return "/" + (lastPageNumber + 1) + ".txt";
    }
    private Page addNewPage(Table table) {
        PageInfo pageInfo = new PageInfo(table.getFolderLocation() + getNextPageFileLocation(table.getPagesInfo()));
        table.addPageInfo(pageInfo);
        return new Page(pageInfo);
    }

    private Page getPageToInsertIn(Table table, int requiredPageInfoIndex) {
        if(requiredPageInfoIndex == table.getPagesInfo().size()) {
            return addNewPage(table);
        }
        PageInfo requiredPageInfo = table.getPagesInfo().get(requiredPageInfoIndex);
        return Page.deserializePage(requiredPageInfo);
    }
    private void checkIfPrimaryKeyAlreadyExists(Table table, Object clusteringValue, int index) throws DBAppException {
        if(index == -1) {
            throw new DBAppException(String.format("Primary Key of value '%s' already exists in Table '%s'", clusteringValue.toString(), table.getName()));
        }
    }
    private void adjustTablePages(Table table, int nextPageIndex, Page currentPage) {
        List<PageInfo> pagesInfo = table.getPagesInfo();
        boolean isOutOfBound = true;
        while(nextPageIndex < pagesInfo.size() && isOutOfBound) {
            Record nextRecord = currentPage.removeRecord(currentPage.size() - 1, table.getClusteringKey());
            Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
            PageInfo nextPageInfo = pagesInfo.get(nextPageIndex);
            currentPage = Page.deserializePage(nextPageInfo);
            isOutOfBound = ! currentPage.addRecord(0, table.getClusteringKey(), nextRecord);
            nextPageIndex++;
        }
        if(isOutOfBound) {
            Record nextRecord = currentPage.removeRecord(currentPage.size() - 1, table.getClusteringKey());
            Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
            currentPage = addNewPage(table);
            currentPage.addRecord(0, table.getClusteringKey(), nextRecord);
        }
        Serializer.serialize(currentPage.getPageInfo().getLocation(), currentPage);
    }
    public synchronized void insertIntoTable() throws DBAppException {
        Validator.checkInsertionInputsValidity(tableName, record);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        Object clusteringValue = record.get(table.getClusteringKey());
        int requiredPageInfoIndex = getRequiredPageInfoIndex(table, clusteringValue);
        checkIfPrimaryKeyAlreadyExists(table, clusteringValue, requiredPageInfoIndex);
        Page page = getPageToInsertIn(table, requiredPageInfoIndex);
        int requiredRecordIndex = getRequiredRecordIndex(page, table.getClusteringKey(), clusteringValue);
        checkIfPrimaryKeyAlreadyExists(table, clusteringValue, requiredRecordIndex);
        boolean isOverFullPage = ! page.addRecord(requiredRecordIndex, table.getClusteringKey(), record);
        if(isOverFullPage) {
            adjustTablePages(table, requiredPageInfoIndex + 1, page);
        }
        else {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
    }
}
