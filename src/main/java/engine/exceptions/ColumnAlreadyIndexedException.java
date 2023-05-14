package engine.exceptions;

import engine.DBApp;

public class ColumnAlreadyIndexedException extends DBAppException {
    public ColumnAlreadyIndexedException(String tableName, String indexName, String columnName) {
        super(String.format("Column '%s' in table '%s' has already an index '%s' applied on it", columnName, tableName, indexName));
    }
}
