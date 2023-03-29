package utilities.metadata;


import engine.DBAppException;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class Validator {
    private Validator() {}

    private static int search(String tableName) {
        int i;
        List<CSVRecord> records = null;
        try {
            records = MetadataReader.getCSV().getRecords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(i = 0; i < records.size(); i++) {
            MetadataRecord currentRecord = new MetadataRecord(records.get(i));
            String currentTableName = currentRecord.getTableName();
            if(currentTableName.equals(tableName)) {
                return i;
            }
        }
        return -1;
    }
    private static boolean isValidType(MetadataRecord currentRecord, Object value) {
        String type = currentRecord.getColumnType();
        return value.getClass().getName().equals(type);
    }
    private static boolean isValueInRange(MetadataRecord currentRecord, Object value) {
        String minimum =  currentRecord.getMinValue();
        String maximum = currentRecord.getMaxValue();
        boolean overRange = maximum.compareTo(value.toString()) < 0 ;
        boolean underRange = minimum.compareTo(value.toString()) > 0;
        return !overRange && !underRange;
    }

    private static void checkValue(int tableInfoIndex, String tableName, String columnName, Object value) throws DBAppException{
        List<CSVRecord> records = null;
        try {
            records = MetadataReader.getCSV().getRecords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(tableInfoIndex < records.size()) {
            MetadataRecord currentRecord = new MetadataRecord(records.get(tableInfoIndex++));
            String currentTableName = currentRecord.getTableName();
            if(! tableName.equals(currentTableName)) {
                return;
            }
            String currentColumnName = currentRecord.getColumnName();
            if(! currentColumnName.equals(columnName)) {
                continue;
            }
            if(! isValidType(currentRecord, value)) {
                String requiredType = currentRecord.getColumnType();
                String insertedType = value.getClass().getName();
                throw new DBAppException(String.format("Invalid Type of value: %s . Expected %s , but was %s",  value, requiredType, insertedType));
            }
            if(! isValueInRange(currentRecord, value)) {
                String minimum = currentRecord.getMinValue(), maximum = currentRecord.getMaxValue();
                throw new DBAppException(String.format("Value %s is out of range. Minimum value allowed: %s, Maximum value allowed: %s", value, minimum, maximum));
            }
            return;
        }
        throw new DBAppException(String.format("Column '%s' does not exist in Table '$s' ", columnName, tableName));
    }
    private static void checkValues(int tableInfoIndex, String tableName, Hashtable<String, Object> record) throws DBAppException {
        for(String columnName: record.keySet()) {
            checkValue(tableInfoIndex, tableName, columnName, record.get(columnName));
        }
    }
    public static void checkInputsValidity(String tableName, Hashtable<String, Object> record) throws DBAppException{
        int tableInfoIndex = search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValues(tableInfoIndex, tableName, record);
    }
    public static void checkCreationValidity(String tableName) throws DBAppException {
        if(search(tableName) != -1) {
            throw new DBAppException(String.format("Table '%s' already exists", tableName));
        }
    }
}
