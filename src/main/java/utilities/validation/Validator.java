package utilities.validation;


import engine.DBAppException;
import engine.elements.Record;
import engine.operations.paramters.CreateTableParams;
import org.apache.commons.csv.CSVRecord;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.util.*;

import static utilities.datatypes.DatabaseTypesHandler.*;


public class Validator {
    private Validator() {}






    // Insertion Validation

    private static boolean isValueInRange(MetadataRecord currentRecord, Object value) {
        Comparable maximum = DatabaseTypesHandler.getObject(currentRecord.getMaxValue(), currentRecord.getColumnType());
        Comparable minimum = DatabaseTypesHandler.getObject(currentRecord.getMinValue(), currentRecord.getColumnType());
        return minimum.compareTo(value) <= 0 && maximum.compareTo(value) >= 0;
    }
    public static void checkIfAllColumnsExist(int tableInfoIndex, String tableName, List<String> columnNames) throws DBAppException{
        List<CSVRecord> records = MetadataReader.getCSVRecords();
        Set<String> existingTableColumnNames = new HashSet<>();
        while(tableInfoIndex < records.size()) {
            MetadataRecord currentCSVRecord = new MetadataRecord(records.get(tableInfoIndex++));
            String currentTableName = currentCSVRecord.getTableName();
            if(! tableName.equals(currentTableName)) {
                break;
            }
            existingTableColumnNames.add(currentCSVRecord.getColumnName());
        }
        for(String columnName: columnNames) {
            if(! existingTableColumnNames.contains(columnName)) {
                throw new DBAppException(String.format("Column '%s' does not exist in Table '%s' ", columnName, tableName));
            }
        }
    }
    private static void checkValueValidity(int tableInfoIndex, String tableName, String columnName, Object value) throws DBAppException{
        List<CSVRecord> records = MetadataReader.getCSVRecords();
        MetadataRecord currentCSVRecord = null;
        while(tableInfoIndex < records.size()) {
            currentCSVRecord = new MetadataRecord(records.get(tableInfoIndex++));
            String currentTableName = currentCSVRecord.getTableName();
            if(! tableName.equals(currentTableName)) {
                throw new DBAppException(String.format("Column '%s' does not exist in Table '%s' ", columnName, tableName));
            }
            String currentColumnName = currentCSVRecord.getColumnName();
            if(currentColumnName.equals(columnName)) {
                break;
            }
        }
        if(! DatabaseTypesHandler.isCompatibleTypes(currentCSVRecord.getColumnType(), value)) {
            String requiredType = currentCSVRecord.getColumnType();
            String insertedType = value.getClass().getName();
            throw new DBAppException(String.format("Invalid Type of value: %s . Expected %s , but was %s",  value, requiredType, insertedType));
        }
        if(! isValueInRange(currentCSVRecord, value)) {
            String minimum = currentCSVRecord.getMinValue(), maximum = currentCSVRecord.getMaxValue();
            throw new DBAppException(String.format("Value %s is out of range. Minimum value allowed: %s, Maximum value allowed: %s", value, minimum, maximum));
        }
    }
    private static void checkValuesValidity(int tableInfoIndex, String tableName, Hashtable<String, Object> record) throws DBAppException {
        for(String columnName: record.keySet()) {
            checkValueValidity(tableInfoIndex, tableName, columnName, record.get(columnName));
        }
    }
    private static void checkIfAllInsertionParametersExist(int tableInfoIndex, String tableName, Record record) throws DBAppException {
        List<CSVRecord> records = MetadataReader.getCSVRecords();
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
        int tableInfoIndex = MetadataReader.search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValuesValidity(tableInfoIndex, tableName, record);
        checkIfAllInsertionParametersExist(tableInfoIndex, tableName, record);
    }

    // Update Validation

    private static void checkIfCorrectClusteringKeyValueType(String tableName, int tableInfoIndex, String clusteringKeyValue) throws DBAppException{
        List<CSVRecord> records = MetadataReader.getCSVRecords();
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

    public static int checkUpdateValidity(String tableName, String clusteringKey, Record record) throws DBAppException{
        int tableInfoIndex = MetadataReader.search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValuesValidity(tableInfoIndex, tableName, record);
        checkIfCorrectClusteringKeyValueType(tableName, tableInfoIndex, clusteringKey);
        return tableInfoIndex;
    }


    // Delete Validation
    public static int checkDeleteValidity(String tableName, Hashtable<String, Object> colNameValue) throws DBAppException{
        int tableInfoIndex = MetadataReader.search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Table '%s' does not exist", tableName));
        }
        checkValuesValidity(tableInfoIndex, tableName, colNameValue);
        return tableInfoIndex;
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
            if(! DatabaseTypesHandler.isCompatibleTypes(colNameType.get(columnName), colNameBoundValue.get(columnName))) {
                return false;
            }
        }
        return true;
    }
    private static boolean isMaximumLessThanMinimum(Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) {
        for(String columnName: colNameMin.keySet()) {
            Comparable min = DatabaseTypesHandler.getObject(colNameMin.get(columnName), colNameType.get(columnName));
            Comparable max = DatabaseTypesHandler.getObject(colNameMax.get(columnName), colNameType.get(columnName));
            if(min.compareTo(max) > 0) {
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
        return areCompatibleTypes(colNameType, colNameMin) && areCompatibleTypes(colNameType, colNameMax) && ! isMaximumLessThanMinimum(colNameType, colNameMin, colNameMax);
    }
    public static void checkCreationValidity(CreateTableParams params) throws DBAppException {
        if(MetadataReader.search(params.getTableName()) != -1) {
            throw new DBAppException(String.format("Table '%s' already exists", params.getTableName()));
        }
        if(! isClusteringKeyExistingInColumns(params.getClusteringKey(), params.getColNameType())) {
            throw new DBAppException(String.format("The clustering key '%s' does not exist in the Types Table", params.getClusteringKey()));
        }
        if(! isClusteringKeyExistingInColumns(params.getClusteringKey(), params.getColNameMin())) {
            throw new DBAppException(String.format("The clustering key '%s' does not exist in the Minimum values Table", params.getClusteringKey()));
        }
        if(! isClusteringKeyExistingInColumns(params.getClusteringKey(), params.getColNameMax())) {
            throw new DBAppException(String.format("The clustering key '%s' does not exist in the Maximum values Table", params.getClusteringKey()));
        }
        checkIfTypesValid(params.getColNameType());
        if(! areInputsValid(params.getColNameType(), params.getColNameMin(), params.getColNameMax())) {
            throw new DBAppException("Input Columns are not compatible");
        }
    }

}
