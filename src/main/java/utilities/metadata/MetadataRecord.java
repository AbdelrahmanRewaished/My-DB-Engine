package utilities.metadata;

import org.apache.commons.csv.CSVRecord;

class MetadataRecord {
    private String tableName;
    private String maxValue, minValue;
    private String columnName, columnType;
    private boolean isClusteringKey;
    public MetadataRecord(CSVRecord record) {
        tableName = record.get(0);
        columnName = record.get(1);
        columnType = record.get(2);
        isClusteringKey = record.get(3).equals("True");
        minValue = record.get(4);
        maxValue = record.get(5);
    }

    public String getTableName() {
        return tableName;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public boolean isClusteringKey() {
        return isClusteringKey;
    }
}
