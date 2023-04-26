package engine.exceptions.update_exceptions;

import engine.exceptions.DBAppException;

public class UpdateListCannotBeEmptyException extends DBAppException {
    public UpdateListCannotBeEmptyException() {
        super("Update List 'colNameValue' cannot be empty");
    }
}
