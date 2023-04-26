package utilities.metadata;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class MetadataRecord {
    private String tableName;
    private String maxValue, minValue;
    private String columnName, columnType;
    private boolean isClusteringKey;
    private String indexName;
    private String indexType;
    public MetadataRecord(CSVRecord record) {
        tableName = record.get(0);
        columnName = record.get(1);
        columnType = record.get(2);
        isClusteringKey = Boolean.parseBoolean(record.get(3));
        indexName = record.get(4);
        indexType = record.get(5);
        minValue = record.get(6);
        maxValue = record.get(7);
    }

    public String getTableName() {
        return tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getIndexType() {
        return indexType;
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

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
    public boolean isClusteringKey() {
        return isClusteringKey;
    }

    public static MetadataRecord getRecord(List<MetadataRecord> metadataRecordList, String columnName) {
        for(MetadataRecord record: metadataRecordList) {
            if(record.getColumnName().equals(columnName)) {
                return record;
            }
        }
        return null;
    }
}
