package utilities;

import org.apache.commons.csv.CSVRecord;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
public class DatabaseTypesHandler {
    private DatabaseTypesHandler(){}

    private static final String integerType = "java.lang.Integer";
    private static final String doubleType = "java.lang.Double";
    private static final String dateType = "java.util.Date";
    private static final String stringType = "java.lang.String";

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
    static Date getDate(String date) {
        String[] splitter = date.split("-");
        int year = Integer.parseInt(splitter[0]);
        int month = Integer.parseInt(splitter[1]);
        int day = Integer.parseInt(splitter[2]);
        return new Date(year, month, day);
    }
    private static boolean isInteger(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(! Character.isDigit(s.charAt(i))) {
                return false;
            }
            if(s.charAt(i) == '.') {
                return false;
            }
        }
        return true;
    }
    private static boolean isDouble(String s) {
        boolean oneDecimalPointFound = false;
        char c = s.charAt(0);
        if(! Character.isDigit(c)) {
            return false;
        }
        for(int i = 1; i < s.length(); i++) {
            c = s.charAt(i);
            if(! Character.isDigit(c) && c != '.') {
                return false;
            }
            if(! Character.isDigit(c) && ! oneDecimalPointFound) {
                oneDecimalPointFound = true;
            }
            else if(! Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    private static boolean isDate(String s) {
        try {
            LocalDate.parse(s);
        }
        catch(Exception e) {
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
    public static String getTypeOfColumn(int tableInfoIndex, String tableName, String columnName) {
        List<CSVRecord> records = MetadataReader.getCSV();
        while(tableInfoIndex < records.size()) {
            MetadataRecord currentRecord = new MetadataRecord(records.get(tableInfoIndex++));
            if(! tableName.equals(currentRecord.getTableName())) {
                return null;
            }
            if(currentRecord.getColumnName().equals(columnName)) {
                return currentRecord.getColumnType();
            }
        }
        return null;
    }
    public static Comparable getObject(String object) {
        String type = getType(object);
        if(type.equals(integerType)) {
            return Integer.parseInt(object);
        }
        if(type.equals(doubleType)) {
            return Double.parseDouble(object);
        }
        if(type.equals(dateType)) {
            return getDate(object);
        }
        return object;
    }
    public static boolean isCompatibleTypes(String type, String value) {
        return type.equals(stringType) || type.equals(getType(value));
    }
    public static boolean isCompatibleTypes(String type, Object value) {
        return type.equals(value.getClass().getName());
    }
}
