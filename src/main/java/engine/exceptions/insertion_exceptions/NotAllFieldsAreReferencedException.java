package engine.exceptions.insertion_exceptions;

import engine.exceptions.DBAppException;

import java.util.List;

public class NotAllFieldsAreReferencedException extends DBAppException {
    public NotAllFieldsAreReferencedException(String tableName, List<String> missedColumnNames) {
        super(String.format("Table '%s' fields are not all referenced. Missing: %s", tableName, missedColumnNames));
    }
}
