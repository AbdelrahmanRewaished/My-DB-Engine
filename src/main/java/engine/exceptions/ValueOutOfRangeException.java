package engine.exceptions;

public class ValueOutOfRangeException extends DBAppException {
    public ValueOutOfRangeException(String value, String minValue, String maxValue) {
        super(String.format("Value %s is out of range. Minimum value allowed: %s, Maximum value allowed: %s", value, minValue, maxValue));
    }
}
