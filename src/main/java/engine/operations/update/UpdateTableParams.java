package engine.operations.update;

import engine.elements.Record;
import engine.exceptions.DBAppException;
import engine.exceptions.update_exceptions.UpdateListCannotBeEmptyException;

public class UpdateTableParams {
    private String tableName;
    private String clusteringKeyValue;
    private Record colNameValue;

    public UpdateTableParams(String tableName, String clusteringKeyValue, Record colNameValue) throws DBAppException {
        if(tableName == null || colNameValue == null) {
            throw new DBAppException("Invalid update parameters");
        }
        if(colNameValue.isEmpty()) {
            throw new UpdateListCannotBeEmptyException();
        }
        this.tableName = tableName;
        this.clusteringKeyValue = clusteringKeyValue;
        this.colNameValue = colNameValue;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClusteringKeyValue() {
        return clusteringKeyValue;
    }

    public Record getColNameValue() {
        return colNameValue;
    }
}
