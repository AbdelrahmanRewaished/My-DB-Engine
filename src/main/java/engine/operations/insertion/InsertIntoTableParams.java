package engine.operations.insertion;

import engine.elements.Record;
import engine.exceptions.DBAppException;

public class InsertIntoTableParams {
    private String tableName;
    private Record record;

    public InsertIntoTableParams(String tableName, Record colNameValue) throws DBAppException {
        if(tableName == null || colNameValue == null) {
            throw new DBAppException("Invalid parameters");
        }
        if(colNameValue.isEmpty()) {
            throw new DBAppException("Inserted Record cannot be empty");
        }
        this.tableName = tableName;
        this.record = colNameValue;
    }

    public String getTableName() {
        return tableName;
    }

    public Record getRecord() {
        return record;
    }
}
