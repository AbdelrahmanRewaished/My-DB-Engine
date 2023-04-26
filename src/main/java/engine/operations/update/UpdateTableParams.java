package engine.operations.update;

import engine.elements.Record;

public class UpdateTableParams {
    private String tableName;
    private String clusteringKeyValue;
    private Record colNameValue;

    public UpdateTableParams(String tableName, String clusteringKeyValue, Record colNameValue) {
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
