package engine.operations.deletion;

import engine.DBApp;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;
import utilities.validation.DeletionValidator;

import java.util.HashMap;

public class Deletion{
    private final DeleteFromTableParams params;

    public Deletion(DeleteFromTableParams params) {
        this.params = params;
    }
    public synchronized int deleteFromTable() throws DBAppException {
        DeletionValidator.validate(params);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(params.getTableName());
        String clusteringKey = table.getClusteringKey();
        DeletionStrategy strategy;
        if (params.getColNameValue().containsKey(clusteringKey)) {
            strategy = new DeleteRecordOnClusteringKey(params, table);
        } else {
            strategy = new DeleteAllMatchingRecords(params, table);
        }
        int recordsDeleted = strategy.deleteFromTable();
        if (recordsDeleted > 0) {
            Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        }
        return recordsDeleted;
    }
}
