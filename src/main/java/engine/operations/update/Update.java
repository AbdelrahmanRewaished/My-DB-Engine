package engine.operations.update;

import engine.DBApp;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.serialization.Deserializer;
import utilities.validation.UpdateValidator;

import java.util.HashMap;

public class Update {
    private final UpdateTableParams params;

    public Update(UpdateTableParams params) {
        this.params = params;
    }
    public synchronized int updateTable() throws DBAppException {
        UpdateValidator.validate(params);
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        Table table = serializedTablesInfo.get(params.getTableName());
        UpdateStrategy strategy;
        if(params.getClusteringKeyValue() == null) {
            strategy = new UpdateAllRecords(params, table);
        }
        else {
           strategy = new UpdateRecordOnClusteringKey(params, table);
        }
        return strategy.updateTable();
    }
}
