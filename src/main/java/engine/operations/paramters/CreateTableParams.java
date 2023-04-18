package engine.operations.paramters;

import java.util.Hashtable;

public class CreateTableParams extends Params {
    private String tableName;
    private String clusteringKey;
    private Hashtable<String, String> colNameType, colNameMin, colNameMax;

    public CreateTableParams(String tableName, String clusteringKey, Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) {
        this.tableName = tableName;
        this.clusteringKey = clusteringKey;
        this.colNameType = colNameType;
        this.colNameMin = colNameMin;
        this.colNameMax = colNameMax;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClusteringKey() {
        return clusteringKey;
    }

    public Hashtable<String, String> getColNameType() {
        return colNameType;
    }

    public Hashtable<String, String> getColNameMin() {
        return colNameMin;
    }

    public Hashtable<String, String> getColNameMax() {
        return colNameMax;
    }
}
