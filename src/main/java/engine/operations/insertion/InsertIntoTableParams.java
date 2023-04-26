package engine.operations.insertion;

import engine.elements.Record;

public class InsertIntoTableParams {
    private String tableName;
    private Record record;

    public InsertIntoTableParams(String tableName, Record colNameValue) {
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
