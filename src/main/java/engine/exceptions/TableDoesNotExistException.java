package engine.exceptions;

public class TableDoesNotExistException extends DBAppException {
    public TableDoesNotExistException(String tableName) {
        super(String.format("Table '%s' does not exist", tableName));
    }
}
