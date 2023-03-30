package utilities.metadata;


import engine.DBAppException;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Validator {
    private Validator() {}


    private static int search(String tableName) {
        List<CSVRecord> records = MetadataReader.getCSV();
        for(int i = 0; i < records.size(); i++) {
            MetadataRecord currentRecord = new MetadataRecord(records.get(i));
            String currentTableName = currentRecord.getTableName();
            if(currentTableName.equals(tableName)) {
                return i;
            }
        }
        return -1;
    }



    // Insertion Validation
    private static boolean isValidType(MetadataRecord currentRecord, Object value) {
        String type = currentRecord.getColumnType();
        return value.getClass().getName().equals(type);
    }
    private static Date getDate(String[] date) {
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        return new Date(year, month, day);
    }
    private static boolean isDateValueInRange(String minimum, String maximum, Object value) {
        Date currentDate = (Date)value;
        Date minimumDate = getDate(minimum.split("-"));
        Date maximumDate = getDate(maximum.split("-"));
        return ! currentDate.before(minimumDate) && ! currentDate.after(maximumDate);
    }
    private static boolean isValueInRange(MetadataRecord currentRecord, Object value) {
        String minimum =  currentRecord.getMinValue();
        String maximum = currentRecord.getMaxValue();
        if(value instanceof Date) {
            return isDateValueInRange(minimum, maximum, value);
        }
        if(value instanceof Integer) {
            return Integer.parseInt(minimum) <= (Integer) value && Integer.parseInt(maximum) >= (Integer)value;
        }
        boolean overRange = maximum.compareTo(value.toString()) < 0 ;
        boolean underRange = minimum.compareTo(value.toString()) > 0;
        return !overRange && !underRange;
    }
    private static void checkValue(int tableInfoIndex, String tableName, String columnName, Object value) throws DBAppException{
        List<CSVRecord> records = MetadataReader.getCSV();
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
        throw new DBAppException(String.format("Column '%s' does not exist in Table '%s' ", columnName, tableName));
    }
    private static void checkValues(int tableInfoIndex, String tableName, Hashtable<String, Object> record) throws DBAppException {
        for(String columnName: record.keySet()) {
            checkValue(tableInfoIndex, tableName, columnName, record.get(columnName));
        }
    }

    public static void checkInsertionInputsValidity(String tableName, Hashtable<String, Object> record) throws DBAppException{
        int tableInfoIndex = search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValues(tableInfoIndex, tableName, record);
    }



    // Update Validation
    public static void checkUpdateValidity(String tableName, String clusteringKey, Hashtable<String, Object> record) throws DBAppException{

    }


    // Delete Validation
    public static void checkDeleteValidity(String tableName, String clusteringKey, Hashtable<String, Object> record) throws DBAppException{

    }


    // Creation Validation
    private static boolean isClusteringKeyExistingInColumns(String clusteringKey, Hashtable<String, String> columns) {
        return columns.containsKey(clusteringKey);
    }
    private static boolean areSetsContainingTheSameColumns(Set<String> set1, Set<String> set2) {
        return set1.equals(set2);
    }
    private static boolean isNumber(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(! Character.isDigit(s.charAt(i))) {
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
    private static boolean areCompatibleTypes(Hashtable<String, String> colNameType, Hashtable<String, String> colNameBoundValue) {
        for(String columnName: colNameType.keySet()) {
            if(colNameType.get(columnName).equals("java.util.Date") && ! isDate(colNameBoundValue.get(columnName))) {
                return false;
            }
            if(colNameType.get(columnName).equals("java.lang.Integer") && ! isNumber(colNameBoundValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    private static boolean isMinimumIsLessThanMaximum(Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) {
        for(String columnName: colNameMin.keySet()) {
            if(colNameMin.get(columnName).compareTo(colNameMax.get(columnName)) > 0) {
                return false;
            }
        }
        return true;
    }
    private static void checkIfTypeValid(String columnName, String type) throws DBAppException {
        if(! (type.equals("java.lang.Integer") || type.equals("java.lang.String") || type.equals("java.util.Date"))) {
            throw new DBAppException(String.format("Type '%s' of column '%s'", type, columnName));
        }
    }
    private static void checkIfTypesValid(Hashtable<String, String> colNameType) throws DBAppException {
        for(String columnName: colNameType.keySet()) {
            checkIfTypeValid(columnName, colNameType.get(columnName));
        }
    }
    private static boolean areInputsValid(Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) {
        if(! areSetsContainingTheSameColumns(colNameType.keySet(), colNameMin.keySet()) || ! areSetsContainingTheSameColumns(colNameType.keySet(), colNameMax.keySet())) {
            return false;
        }
        return areCompatibleTypes(colNameType, colNameMin) && areCompatibleTypes(colNameType, colNameMax) && isMinimumIsLessThanMaximum(colNameMin, colNameMax);
    }
    public static void checkCreationValidity(String tableName, String clusteringKey, Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) throws DBAppException {
        if(search(tableName) != -1) {
            throw new DBAppException(String.format("Table '%s' already exists", tableName));
        }
        if(! isClusteringKeyExistingInColumns(clusteringKey, colNameType)) {
            throw new DBAppException(String.format("The clustering key '%s' does not exist in the Types Table", clusteringKey));
        }
        if(! isClusteringKeyExistingInColumns(clusteringKey, colNameMin)) {
            throw new DBAppException(String.format("The clustering key '%s' does not exist in the Minimum values Table", clusteringKey));
        }
        if(! isClusteringKeyExistingInColumns(clusteringKey, colNameMax)) {
            throw new DBAppException(String.format("The clustering key '%s' does not exist in the Maximum values Table", clusteringKey));
        }
        checkIfTypesValid(colNameType);
        if(! areInputsValid(colNameType, colNameMin, colNameMax)) {
            throw new DBAppException("Input Columns are not compatible");
        }
    }

    public static void main(String[] args) {
        String[] minDate = {"1700", "01", "01"};

        Date date = getDate(minDate);
        System.out.println(date.before(new Date(2023, 1,1)) + " " + date.getYear() + " " + new Date(2023, 1, 1).getYear());
    }
}
