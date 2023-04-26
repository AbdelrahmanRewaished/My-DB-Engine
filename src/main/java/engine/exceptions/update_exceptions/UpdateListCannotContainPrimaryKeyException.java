package engine.exceptions.update_exceptions;

import engine.exceptions.DBAppException;

public class UpdateListCannotContainPrimaryKeyException extends DBAppException {
    public UpdateListCannotContainPrimaryKeyException() {
        super("Updating primary key of an existing record is forbidden");
    }
}
