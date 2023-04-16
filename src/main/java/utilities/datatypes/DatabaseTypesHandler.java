package utilities.datatypes;

import org.apache.commons.csv.CSVRecord;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class DatabaseTypesHandler {
    private DatabaseTypesHandler(){}

    private static final String integerType = "java.lang.Integer";
    private static final String doubleType = "java.lang.Double";
    private static final String dateType = "java.util.Date";
    private static final String stringType = "java.lang.String";

    private static final String sqlINTType = "INT";
    private static final String sqlVARCHARType = "VARCHAR";
    private static final String sqlFLOATType = "FLOAT";
    private static final String sqlDATEType = "DATE";
    public static String getIntegerType() {
        return integerType;
    }
    public static String getDoubleType() {
        return doubleType;
    }
    public static String getStringType() {
        return stringType;
    }
    public static String getDateType() {
        return dateType;
    }

    public static String getSqlVarcharType() {return sqlVARCHARType;}

    public static Date getDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isInteger(String s) {
        if(s.isEmpty()) {
            return false;
        }
        int i = 0;
        boolean isNegative = false;
        if(s.charAt(0) == '-') {
            isNegative = true;
            i++;
        }
        for(; i < s.length(); i++) {
            char c = s.charAt(i);
            if(! Character.isDigit(c)) {
                return false;
            }
            if(c == '.') {
                return false;
            }
        }
        return ! isNegative || i > 1;
    }
    private static boolean isDouble(String s) {
       try{
           Double.parseDouble(s);
       }catch (Exception e) {
           return false;
       }
       return true;
    }
    private static boolean isDate(String s) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }
        catch(ParseException e) {
            return false;
        }
        return true;
    }
    public static String getType(String value) {
        if(isDate(value)) {
            return dateType;
        }
        if(isInteger(value)) {
            return integerType;
        }
        if(isDouble(value)) {
            return doubleType;
        }
        return stringType;
    }
    public static Comparable getObject(String object, String objectType) {
        if(objectType.equals(stringType)) {
            return object;
        }
        if(objectType.equals(integerType)) {
            return Integer.parseInt(object);
        }
        if(objectType.equals(doubleType)) {
            return Double.parseDouble(object);
        }
        if(objectType.equals(dateType)) {
            return getDate(object);
        }
        return object;
    }
    public static boolean isCompatibleTypes(String type, String value) {
        return type.equals(stringType) || type.equals(doubleType) && getType(value).equals(integerType) || type.equals(getType(value));
    }
    public static boolean isCompatibleTypes(String type, Object value) {
        return type.equals(doubleType) && value instanceof Integer || type.equals(value.getClass().getName());
    }
    private static String getIdIntegerMinValue() {
        return "0";
    }
    private static String getMinimumIntegerValue() {
        return Integer.MIN_VALUE + "";
    }
    private static String getMinimumDoubleValue() {
        return Double.MIN_VALUE + "";
    }
    private static String getMinimumStringValue() {
        return ' ' + " ";
    }
    private static String getMinimumDateValue() {
        Date min = new Date(Integer.MIN_VALUE);
        return String.format("%d-%d-%d", min.getYear(), min.getMonth(), min.getDay());
    }
    private static String getMaximumIntegerValue() {
        return Integer.MAX_VALUE + "";
    }
    private static String getMaximumDoubleValue() {
        return Double.MAX_VALUE + "";
    }
    private static String getMaximumStringValue(int maxLength) {
        StringBuilder sb = new StringBuilder();
        char maximumCharEver = '~';
        while(maxLength-- > 0) {
            sb.append(maximumCharEver);
        }
        return sb.toString();
    }
    private static String getMaximumDateValue() {
        Date max = new Date(Long.MAX_VALUE);
        return String.format("%d-%d-%d", max.getYear(), max.getMonth(), max.getDay());
    }
    public static Bound getBoundDataValues(String type, boolean isPrimaryKey, Integer maxStringLength) {
        switch (type) {
            case "INT" -> {
                String max = getMaximumIntegerValue();
                if (isPrimaryKey) {
                    return new Bound(getIdIntegerMinValue(), max);
                }
                return new Bound(getMinimumIntegerValue(), max);
            }
            case "FLOAT" -> {
                return new Bound(getMinimumDoubleValue(), getMaximumDoubleValue());
            }
            case "DATE" -> {
                return new Bound(getMinimumDateValue(), getMaximumDateValue());
            }
            default -> {
                return new Bound(getMinimumStringValue(), getMaximumStringValue(maxStringLength));
            }
        }
    }
    public static String getCorrespondingJavaType(String sqlType) {
        // VARCHAR(xx)
        // characters indices
        // 0123456
        if (sqlType.contains(sqlVARCHARType)) {
            return stringType;
        }
        return switch (sqlType) {
            case sqlINTType -> integerType;
            case sqlFLOATType -> doubleType;
            case sqlDATEType -> dateType;
            default -> null;
        };
    }

    public static void main(String[] args) {
        System.out.println(getMaximumDateValue() + "  " + getMinimumDateValue());
    }
}
