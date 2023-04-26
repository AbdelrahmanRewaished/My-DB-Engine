package engine.exceptions.creation_exceptions;

import engine.exceptions.DBAppException;

public class ColumnsAreNotCompatibleException extends DBAppException {
    public ColumnsAreNotCompatibleException() {
        super("Input Columns are not compatible");
    }
}
