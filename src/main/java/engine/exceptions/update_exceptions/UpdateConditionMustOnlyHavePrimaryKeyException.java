package engine.exceptions.update_exceptions;

import engine.exceptions.DBAppException;

public class UpdateConditionMustOnlyHavePrimaryKeyException extends DBAppException {
    public UpdateConditionMustOnlyHavePrimaryKeyException(String tableName, String insertedClusteringKey, String actualClusteringKey) {
        super(String.format("Update condition must only have Primary key of table '%s'. Expected Primary key is '%s' but was '%s'", tableName, actualClusteringKey, insertedClusteringKey));
    }
}
