package engine.operations.creation;

import engine.exceptions.DBAppException;

import java.util.Hashtable;

public class CreateTableParams {
    private String tableName;
    private String clusteringKey;
    private Hashtable<String, String> colNameType, colNameMin, colNameMax;

    private boolean isValidParameters() {
        return tableName != null && clusteringKey != null && colNameType != null && colNameMin != null && colNameMax != null;
    }
    private String getEmptyHashTable() {
        if(colNameType.isEmpty()) {
            return "colNameType";
        }
        if(colNameMin.isEmpty()) {
            return "colNameMin";
        }
        if(colNameMax.isEmpty()) {
            return "colNameMax";
        }
        return null;
    }
    public CreateTableParams(String tableName, String clusteringKey, Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) throws DBAppException {
        this.tableName = tableName;
        this.clusteringKey = clusteringKey;
        this.colNameType = colNameType;
        this.colNameMin = colNameMin;
        this.colNameMax = colNameMax;
        if(! isValidParameters()) {
            throw new DBAppException("Invalid Create Table parameters");
        }
        if(getEmptyHashTable() != null) {
            throw new DBAppException(String.format("Hashtable '%s' cannot be empty", getEmptyHashTable()));
        }
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
