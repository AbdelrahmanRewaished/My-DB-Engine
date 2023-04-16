package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import engine.elements.PageInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.operations.paramters.DeleteFromTableParams;
import utilities.FileHandler;
import utilities.KeySearching;
import utilities.validation.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;
import java.util.Hashtable;

public class Deletion{
    private final DeleteFromTableParams params;
    private final String tableName;
    private final Hashtable<String, Object> colNameValue;
    public Deletion(DeleteFromTableParams params) {
        this.params = params;
        this.tableName = params.getTableName();
        this.colNameValue = params.getColNameValue();
    }

    private boolean areInputValuesMatchingCurrentRecord(Record currentRecord) {
        for(String columnName: colNameValue.keySet()) {
            if(! currentRecord.get(columnName).equals(colNameValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    private int deleteRecordContainingClusteringKey(Table table) {
        // Binary Search over the Pages
        String clusteringKey = table.getClusteringKey();
        int requiredPageIndexToDeleteFrom = KeySearching.findPageToLookIn(table, colNameValue.get(clusteringKey));
        if(requiredPageIndexToDeleteFrom == -1) {
            return 0;
        }
        // Binary search over records in the Page found
        PageInfo pageInfo = table.getPagesInfo().get(requiredPageIndexToDeleteFrom);
        Page page = Page.deserializePage(pageInfo);
        int requiredRecordIndex = KeySearching.findRecordIndex(page, clusteringKey, (Comparable) colNameValue.get(clusteringKey));
        if(requiredRecordIndex == -1) {
            return 0;
        }
        Record record = page.get(requiredRecordIndex);
        // Check if the all other values colNameValue exist in the record found
        // otherwise return 0
        if(! areInputValuesMatchingCurrentRecord(record)) {
            return 0;
        }
        page.removeRecord(requiredRecordIndex, clusteringKey);
        if(! page.isEmpty()) {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        else {
            FileHandler.deleteFile(pageInfo.getLocation());
        }
        return 1;
    }
    private int deleteAllMatchingValues(Table table) {
        int recordsDeleted = 0;
        for(int i = 0; i < table.getPagesInfo().size();) {
            PageInfo currentPageInfo = table.getPagesInfo().get(i);
            Page currentPage = Page.deserializePage(currentPageInfo);
            currentPage.setPageInfo(currentPageInfo);
            for(int j = 0; j < currentPage.size();) {
                Record currentRecord = currentPage.get(j);
                boolean toDeleteRecord = areInputValuesMatchingCurrentRecord(currentRecord);
                if(toDeleteRecord) {
                    currentPage.removeRecord(j, table.getClusteringKey());
                    recordsDeleted++;
                }
                else {
                    j++;
                }
            }
            if(currentPage.isEmpty()) {
                table.removePageInfo(i);
                FileHandler.deleteFile(currentPageInfo.getLocation());
            }
            else {
                Serializer.serialize(currentPageInfo.getLocation(), currentPage);
                i++;
            }
        }
        return recordsDeleted;
    }
    public synchronized int deleteFromTable() throws DBAppException {
        Validator.checkDeleteValidity(tableName, colNameValue);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        String clusteringKey = table.getClusteringKey();
        int recordsDeleted;
        if (colNameValue.containsKey(clusteringKey)) {
            recordsDeleted = deleteRecordContainingClusteringKey(table);
        } else {
            recordsDeleted = deleteAllMatchingValues(table);
        }
        if (recordsDeleted > 0) {
            Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        }
        return recordsDeleted;
    }
}
