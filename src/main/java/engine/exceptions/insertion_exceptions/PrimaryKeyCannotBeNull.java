package engine.exceptions.insertion_exceptions;

import engine.exceptions.DBAppException;

public class PrimaryKeyCannotBeNull extends DBAppException {
    public PrimaryKeyCannotBeNull(String tableName, String clusteringKey) {
        super(String.format("Primary Key '%s' of table '%s' cannot be null", clusteringKey, tableName));
    }
}
