package engine.operations.creation;

import engine.exceptions.DBAppException;

import java.util.Arrays;
import java.util.HashSet;

public class CreateIndexParams {
    private final String tableName, indexName;
    private final String[] indexedColumns;

    public CreateIndexParams(String tableName, String indexName, String[] indexedColumns) throws DBAppException {
        this.tableName = tableName;
        this.indexName = indexName;
        this.indexedColumns = indexedColumns;
        if(indexedColumns.length != 3 || new HashSet<>(Arrays.asList(indexedColumns)).size() != indexedColumns.length) {
            throw new DBAppException("Octree index must be created on exactly 3 unique columns");
        }
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
