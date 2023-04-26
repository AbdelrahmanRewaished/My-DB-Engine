package engine.exceptions.creation_exceptions;

import engine.exceptions.DBAppException;

public class TableAlreadyExistsException extends DBAppException {
    public TableAlreadyExistsException(String tableName) {
        super(String.format("Table '%s' already exists", tableName));
    }
}
