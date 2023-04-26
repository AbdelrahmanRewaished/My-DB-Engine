package engine.exceptions;

public class ColumnDoesNotExistException extends DBAppException {
    public ColumnDoesNotExistException(String tableName, String columnName) {
        super(String.format("Column '%s' does not exist in table '%s'", columnName, tableName));
    }
}
