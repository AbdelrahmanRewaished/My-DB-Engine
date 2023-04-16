package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Page;
import engine.elements.PageInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.operations.paramters.UpdateTableParams;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.KeySearching;
import utilities.metadata.MetadataReader;
import utilities.validation.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;

public class Update {
    private final String tableName;
    private final String clusteringKeyValue;
    private final Record colNameNewValue;
    private final UpdateTableParams params;

    public Update(UpdateTableParams params) {
        this.params = params;
        this.tableName = params.getTableName();
        this.clusteringKeyValue = params.getClusteringKeyValue();
        this.colNameNewValue = params.getColNameValue();
    }

    private void checkIfPrimaryKeyMissing(Table table, String clusteringKeyValue, int index) throws DBAppException {
        if(index == -1) {
            throw new DBAppException(String.format("Primary Key of value '%s' does not exist in Table '%s'", clusteringKeyValue, table.getName()));
        }
    }
    private int findPageInfoIndex(Table table, String clusteringKeyType) {
        return KeySearching.findPageToLookIn(table,  DatabaseTypesHandler.getObject(clusteringKeyValue, clusteringKeyType));
    }
    private int findRecordIndex(Page page, String clusteringKey, String keyValue, String clusteringKeyType) {
        return KeySearching.findRecordIndex(page, clusteringKey, DatabaseTypesHandler.getObject(keyValue, clusteringKeyType));
    }
    public synchronized void updateTable() throws DBAppException {
        int metadataTableInfoIndex = Validator.checkUpdateValidity(tableName, clusteringKeyValue, colNameNewValue);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        String clusteringKeyType = MetadataReader.getCorrespondingColumnType(tableName, table.getClusteringKey(), metadataTableInfoIndex);
        int pageContainingKeyIndex = findPageInfoIndex(table, clusteringKeyType);
        checkIfPrimaryKeyMissing(table, clusteringKeyValue, pageContainingKeyIndex);
        PageInfo pageInfo = table.getPagesInfo().get(pageContainingKeyIndex);
        Page page = Page.deserializePage(pageInfo);
        int requiredRecordIndex = findRecordIndex(page, table.getClusteringKey(), clusteringKeyValue, clusteringKeyType);
        checkIfPrimaryKeyMissing(table, clusteringKeyValue, requiredRecordIndex);
        page.updateRecord(requiredRecordIndex, colNameNewValue);
        Serializer.serialize(pageInfo.getLocation(), page);
    }
}
