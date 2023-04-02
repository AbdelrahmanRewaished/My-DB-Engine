package utilities;


import engine.DBAppException;
import engine.elements.Record;
import org.apache.commons.csv.CSVRecord;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.util.*;

import static utilities.DatabaseTypesHandler.*;

public class Validator {
    private Validator() {}


    private static int search(String tableName) {
        List<CSVRecord> records = MetadataReader.getCSV();
        for(int i = 0; i < records.size(); i++) {
            MetadataRecord currentCSVRecord = new MetadataRecord(records.get(i));
            String currentTableName = currentCSVRecord.getTableName();
            if(currentTableName.equals(tableName)) {
                return i;
            }
        }
        return -1;
    }



    // Insertion Validation

    private static boolean isDateValueInRange(String minimum, String maximum, Object value) {
        Date currentDate = (Date)value;
        Date minimumDate = getDate(minimum);
        Date maximumDate = getDate(maximum);
        return ! currentDate.before(minimumDate) && ! currentDate.after(maximumDate);
    }
    private static boolean isValueInRange(MetadataRecord currentRecord, Object value) {
        Comparable maximum = DatabaseTypesHandler.getObject(currentRecord.getMaxValue());
        Comparable minimum = DatabaseTypesHandler.getObject(currentRecord.getMinValue());
        return minimum.compareTo(value) <= 0 && maximum.compareTo(value) >= 0;
    }
    private static void checkValue(int tableInfoIndex, String tableName, String columnName, Object value) throws DBAppException{
        List<CSVRecord> records = MetadataReader.getCSV();
        while(tableInfoIndex < records.size()) {
            MetadataRecord currentCSVRecord = new MetadataRecord(records.get(tableInfoIndex++));
            String currentTableName = currentCSVRecord.getTableName();
            if(! tableName.equals(currentTableName)) {
                return;
            }
            String currentColumnName = currentCSVRecord.getColumnName();
            if(! currentColumnName.equals(columnName)) {
                continue;
            }
            if(! isCompatibleTypes(currentCSVRecord.getColumnType(), value)) {
                String requiredType = currentCSVRecord.getColumnType();
                String insertedType = value.getClass().getName();
                throw new DBAppException(String.format("Invalid Type of value: %s . Expected %s , but was %s",  value, requiredType, insertedType));
            }
            if(! isValueInRange(currentCSVRecord, value)) {
                String minimum = currentCSVRecord.getMinValue(), maximum = currentCSVRecord.getMaxValue();
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
    private static void checkIfAllInsertionParametersExist(int tableInfoIndex, String tableName, Record record) throws DBAppException {
        List<CSVRecord> records = MetadataReader.getCSV();
        List<String> missedColumnNames = new ArrayList<>();
        while(tableInfoIndex < records.size()) {
            MetadataRecord currentCSVRecord = new MetadataRecord(records.get(tableInfoIndex++));
            if(! currentCSVRecord.getTableName().equals(tableName)) {
                break;
            }
            if(! record.containsKey(currentCSVRecord.getColumnName())) {
                missedColumnNames.add(currentCSVRecord.getColumnName());
            }
        }
        if(! missedColumnNames.isEmpty()) {
            throw new DBAppException(String.format("Table '%s' fields are not all referenced. Missing: %s", tableName, missedColumnNames));
        }
    }
    public static void checkInsertionInputsValidity(String tableName, Record record) throws DBAppException{
        int tableInfoIndex = search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValues(tableInfoIndex, tableName, record);
        checkIfAllInsertionParametersExist(tableInfoIndex, tableName, record);
    }



    // Update Validation

    private static void checkIfCorrectClusteringKeyValueType(String tableName, int tableInfoIndex, String clusteringKeyValue) throws DBAppException{
        List<CSVRecord> records = MetadataReader.getCSV();
        while(tableInfoIndex < records.size()) {
            MetadataRecord currentCSVRecord = new MetadataRecord(records.get(tableInfoIndex++));
            if(! tableName.equals(currentCSVRecord.getTableName())) {
                return;
            }
            if(! currentCSVRecord.isClusteringKey()) {
                continue;
            }
            if(! isCompatibleTypes(currentCSVRecord.getColumnType(), clusteringKeyValue)) {
                throw new DBAppException(String.format("Invalid Primary Key value of table '%s'. Expected '%s' but was '%s'", tableName, currentCSVRecord.getColumnType(), getType(clusteringKeyValue)));
            }
        }
    }

    public static void checkUpdateValidity(String tableName, String clusteringKey, Record record) throws DBAppException{
        int tableInfoIndex = search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValues(tableInfoIndex, tableName, record);
        checkIfCorrectClusteringKeyValueType(tableName, tableInfoIndex, clusteringKey);
    }


    // Delete Validation
    public static void checkDeleteValidity(String tableName, Hashtable<String, Object> colNameValue) throws DBAppException{
        int tableInfoIndex = search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValues(tableInfoIndex, tableName, colNameValue);
    }


    // Creation Validation
    private static boolean isClusteringKeyExistingInColumns(String clusteringKey, Hashtable<String, String> columns) {
        return columns.containsKey(clusteringKey);
    }
    private static boolean areSetsContainingTheSameColumns(Set<String> set1, Set<String> set2) {
        return set1.equals(set2);
    }
    private static boolean areCompatibleTypes(Hashtable<String, String> colNameType, Hashtable<String, String> colNameBoundValue) {
        for(String columnName: colNameType.keySet()) {
            if(! isCompatibleTypes(colNameType.get(columnName), colNameBoundValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    private static boolean isMaximumLessThanMinimum(Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) {
        for(String columnName: colNameMin.keySet()) {
            if(colNameMin.get(columnName).compareTo(colNameMax.get(columnName)) > 0) {
                return true;
            }
        }
        return false;
    }
    private static void checkIfTypeValid(String columnName, String type) throws DBAppException {
        if(! (type.equals(getIntegerType()) || type.equals(getDoubleType()) ||
                type.equals(getStringType()) || type.equals(getDateType()))) {
            throw new DBAppException(String.format("Type '%s' of column '%s' is invalid", type, columnName));
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
        return areCompatibleTypes(colNameType, colNameMin) && areCompatibleTypes(colNameType, colNameMax) && ! isMaximumLessThanMinimum(colNameMin, colNameMax);
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
}
