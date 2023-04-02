package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import engine.elements.PageInfo;
import engine.elements.Table;
import engine.elements.Record;
import utilities.DatabaseTypesHandler;
import utilities.KeySearching;
import utilities.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;
import java.util.Hashtable;

public class Update {
    private Update(){}

    private static void checkIfPrimaryKeyMissing(Table table, String clusteringKeyValue, int index) throws DBAppException {
        if(index == -1) {
            throw new DBAppException(String.format("Primary Key of value '%s' does not exist in Table '%s'", clusteringKeyValue, table.getName()));
        }
    }
    private static int findPageInfoIndex(Table table, String clusteringKeyValue) {
        return KeySearching.findPageToLookIn(table,  DatabaseTypesHandler.getObject(clusteringKeyValue));
    }
    private static int findRecordIndex(Page page, String clusteringKey, String keyValue) {
        return KeySearching.findRecordIndex(page, clusteringKey, DatabaseTypesHandler.getObject(keyValue));
    }
    public static void updateTable(String tableName, String clusteringKeyValue, Record colNameNewValue) throws DBAppException {
        Validator.checkUpdateValidity(tableName, clusteringKeyValue, colNameNewValue);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        int pageContainingKeyIndex = findPageInfoIndex(table, clusteringKeyValue);
        checkIfPrimaryKeyMissing(table, clusteringKeyValue, pageContainingKeyIndex);
        PageInfo pageInfo = table.getPagesInfo().get(pageContainingKeyIndex);
        Page page = Page.deserializePage(pageInfo);
        int requiredRecordIndex = findRecordIndex(page, table.getClusteringKey(), clusteringKeyValue);
        checkIfPrimaryKeyMissing(table, clusteringKeyValue, requiredRecordIndex);
        page.updateRecord(requiredRecordIndex, colNameNewValue);
        Serializer.serialize(pageInfo.getLocation(), page);
    }
}
