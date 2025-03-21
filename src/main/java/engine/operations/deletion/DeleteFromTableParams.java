package engine.operations.deletion;

import engine.exceptions.DBAppException;

import java.util.Hashtable;

public class DeleteFromTableParams {
    private String tableName;
    private Hashtable<String, Object> colNameValue;

    public DeleteFromTableParams(String tableName, Hashtable<String, Object> colNameValue) throws DBAppException {
        if(tableName == null) {
            throw new DBAppException("invalid Delete from Table parameters");
        }
        this.tableName = tableName;
        this.colNameValue = colNameValue;
    }
    public String getTableName() {
        return tableName;
    }
    public Hashtable<String, Object> getColNameValue() {
        return colNameValue;
    }
}
