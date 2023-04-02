package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import engine.elements.PageInfo;
import engine.elements.Record;
import engine.elements.Table;
import utilities.FileHandler;
import utilities.KeySearching;
import utilities.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;
import java.util.Hashtable;

public class Deletion {
    private Deletion(){}

    private static boolean areInputValuesMatchingCurrentRecord(Record currentRecord, Hashtable<String, Object> colNameValue) {
        for(String columnName: colNameValue.keySet()) {
            if(! currentRecord.get(columnName).equals(colNameValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    private static int deleteRecordContainingClusteringKey(Table table, Hashtable<String, Object> colNameValue) {
        // Binary Search over the Pages
        String clusteringKey = table.getClusteringKey();
        int requiredPageIndexToDeleteFrom = KeySearching.findPageToLookIn(table, colNameValue.get(clusteringKey));
        if(requiredPageIndexToDeleteFrom == -1) {
            return 0;
        }
        // Binary search over records in the Page found
        String requiredPageLocation = table.getPagesInfo().get(requiredPageIndexToDeleteFrom).getLocation();
        Page page = (Page) Deserializer.deserialize(requiredPageLocation);
        int requiredRecordIndex = KeySearching.findRecordIndex(page, clusteringKey, (Comparable) colNameValue.get(clusteringKey));
        if(requiredRecordIndex == -1) {
            return 0;
        }
        Record record = page.get(requiredRecordIndex);
        // Check if the all other values colNameValue exist in the record found
        // otherwise return 0
        if(! areInputValuesMatchingCurrentRecord(record, colNameValue)) {
            return 0;
        }
        page.removeRecord(requiredRecordIndex, clusteringKey);
        if(! page.isEmpty()) {
            Serializer.serialize(page.getPageInfo().getLocation(), page);
        }
        else {
            FileHandler.deleteFile(requiredPageLocation);
        }
        return 1;
    }
    private static int deleteAllMatchingValues(Table table, Hashtable<String, Object> colNameValue) {
        int recordsDeleted = 0;
        for(int i = 0; i < table.getPagesInfo().size();) {
            PageInfo currentPageInfo = table.getPagesInfo().get(i);
            Page currentPage = (Page)Deserializer.deserialize(currentPageInfo.getLocation());
            for(int j = 0; j < currentPage.size();) {
                Record currentRecord = currentPage.get(j);
                boolean toDeleteRecord = areInputValuesMatchingCurrentRecord(currentRecord, colNameValue);
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
    public static int deleteFromTable(String tableName, Hashtable<String, Object> colNameValue) throws DBAppException {
        Validator.checkDeleteValidity(tableName, colNameValue);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        String clusteringKey = table.getClusteringKey();
        int recordsDeleted;
        if(colNameValue.containsKey(clusteringKey)) {
            recordsDeleted = deleteRecordContainingClusteringKey(table, colNameValue);
        }
        else {
            recordsDeleted = deleteAllMatchingValues(table, colNameValue);
        }
        if(recordsDeleted > 0) {
            Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        }
        return recordsDeleted;
    }
}
