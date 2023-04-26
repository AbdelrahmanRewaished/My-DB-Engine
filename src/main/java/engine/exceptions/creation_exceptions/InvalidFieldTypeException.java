package engine.exceptions.creation_exceptions;

import engine.exceptions.DBAppException;
import utilities.datatypes.DatabaseTypesHandler;

import java.util.Arrays;
import java.util.List;

public class InvalidFieldTypeException extends DBAppException {
    public InvalidFieldTypeException(String type, String columnName) {
        super(String.format("Type '%s' of column '%s' is invalid. Allowed types are %s", type, columnName, Arrays.toString(DatabaseTypesHandler.supportedTypes)));
    }
}
