package engine.operations.creation;

public class CreateIndexParams {
    private final String tableName, indexName;
    private final String[] indexedColumns;

    public CreateIndexParams(String tableName, String indexName, String columnName1, String columnName2, String columnName3) {
        this.tableName = tableName;
        this.indexName = indexName;
        this.indexedColumns = new String[3];
    }

    public String getTableName() {
        return tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public String[] getIndexedColumns() {
        return indexedColumns;
    }
}
