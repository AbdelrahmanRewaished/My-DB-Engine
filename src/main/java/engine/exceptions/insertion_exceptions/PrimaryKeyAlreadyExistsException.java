package engine.exceptions.insertion_exceptions;

import engine.exceptions.DBAppException;

public class PrimaryKeyAlreadyExistsException extends DBAppException {
    public PrimaryKeyAlreadyExistsException(String clusteringKeyValue, String tableName) {
        super(String.format("Primary Key of value '%s' already exists in Table '%s'", clusteringKeyValue, tableName));
    }
}
