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
    private int findPageInfoIndex(Table table, Comparable clusteringKeyValue) {
        return KeySearching.findPageToLookIn(table,  clusteringKeyValue);
    }
    private int findRecordIndex(Page page, String clusteringKey, Comparable clusteringKeyValue) {
        return KeySearching.findRecordIndex(page, clusteringKey, clusteringKeyValue);
    }
    private void updateRecordContainingClusteringKey(Table table, int metadataTableInfoIndex) throws DBAppException {
        String clusteringKeyType = MetadataReader.getCorrespondingColumnType(tableName, table.getClusteringKey(), metadataTableInfoIndex);
        Comparable keyObjectValue = DatabaseTypesHandler.getObject(clusteringKeyValue, clusteringKeyType);
        int pageContainingKeyIndex = findPageInfoIndex(table, keyObjectValue);
        checkIfPrimaryKeyMissing(table, clusteringKeyValue, pageContainingKeyIndex);
        PageInfo pageInfo = table.getPagesInfo().get(pageContainingKeyIndex);
        Page page = Page.deserializePage(pageInfo);
        int requiredRecordIndex = findRecordIndex(page, table.getClusteringKey(), keyObjectValue);
        checkIfPrimaryKeyMissing(table, clusteringKeyValue, requiredRecordIndex);
        page.updateRecord(requiredRecordIndex, colNameNewValue);
        Serializer.serialize(pageInfo.getLocation(), page);
    }
    private int updateAllRecords(Table table) {
        int totalRecordsUpdated = 0;
        for(PageInfo pageInfo: table.getPagesInfo()) {
            Page currentPage = Page.deserializePage(pageInfo);
            for(int i = 0; i < currentPage.size(); i++) {
                currentPage.updateRecord(i, colNameNewValue);
            }
            totalRecordsUpdated += currentPage.size();
            Serializer.serialize(pageInfo.getLocation(), currentPage);
        }
        return totalRecordsUpdated;
    }
    public synchronized int updateTable() throws DBAppException {
        int metadataTableInfoIndex = Validator.checkUpdateValidity(params);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(tableName);
        if(clusteringKeyValue == null) {
            return updateAllRecords(table);
        }
        else {
            updateRecordContainingClusteringKey(table, metadataTableInfoIndex);
            return 1;
        }
    }
}
