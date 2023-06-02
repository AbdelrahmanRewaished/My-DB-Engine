package engine.operations.deletion;

import engine.DBApp;
import engine.elements.Page;
import engine.elements.PageMetaInfo;
import engine.elements.Record;
import engine.elements.Table;
import engine.elements.index.Octree;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;
import utilities.validation.DeletionValidator;

public class Deletion{
    private final DeleteFromTableParams params;

    public Deletion(DeleteFromTableParams params) {
        this.params = params;
    }
    public synchronized int deleteFromTable() throws DBAppException {
        DeletionValidator.validate(params);
        Table table = (Table) Deserializer.deserialize(DBApp.getTableInfoFileLocation(params.getTableName()));
        String clusteringKey = table.getClusteringKey();
        DeletionStrategy strategy;
        if(table.isHavingIndices()) {
            if(params.getColNameValue().isEmpty()) {
                strategy = new DeleteAllRecordsWithIndex(params, table);
            }
            else {
                strategy = new DeleteRecordsOnValuesWithIndex(params, table);
            }
        }
        else {
            if (params.getColNameValue().containsKey(clusteringKey)) {
                strategy = new DeleteRecordOnClusteringKey(params, table);
            } else {
                strategy = new DeleteAllMatchingRecords(params, table);
            }
        }
        int recordsDeleted = strategy.deleteFromTable();
        if (recordsDeleted > 0) {
            Serializer.serialize(DBApp.getTableInfoFileLocation(params.getTableName()), table);
        }
        return recordsDeleted;
    }
}
