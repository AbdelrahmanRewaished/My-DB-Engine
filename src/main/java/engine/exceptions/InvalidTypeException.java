package engine.exceptions;

public class InvalidTypeException extends DBAppException {
    public InvalidTypeException(String value, String requiredType, String insertedType) {
        super(String.format("Invalid Type of value: %s. Expected %s, but was %s",  value, requiredType, insertedType));
    }
}
