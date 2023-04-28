package utilities.validation;

import engine.exceptions.DBAppException;
import engine.exceptions.creation_exceptions.ClusteringKeyIsNotReferencedInTable;
import engine.exceptions.creation_exceptions.ColumnsAreNotCompatibleException;
import engine.exceptions.creation_exceptions.InvalidFieldTypeException;
import engine.exceptions.creation_exceptions.TableAlreadyExistsException;
import engine.operations.creation.CreateTableParams;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;

import java.util.Hashtable;
import java.util.Set;

public class CreationValidator {
    private CreationValidator(){}

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
    private static void checkIfValidType(String columnName, String type) throws DBAppException {
        if(! DatabaseTypesHandler.getSupportedTypes().contains(type)) {
            throw new InvalidFieldTypeException(type, columnName);
        }
    }
    private static void checkIfValidTypes(Hashtable<String, String> colNameType) throws DBAppException {
        for(String columnName: colNameType.keySet()) {
            checkIfValidType(columnName, colNameType.get(columnName));
        }
    }
    private static boolean areInputsValid(Hashtable<String, String> colNameType, Hashtable<String, String> colNameMin, Hashtable<String, String> colNameMax) {
        if(! areSetsContainingTheSameColumns(colNameType.keySet(), colNameMin.keySet()) || ! areSetsContainingTheSameColumns(colNameType.keySet(), colNameMax.keySet())) {
            return false;
        }
        return areCompatibleTypes(colNameType, colNameMin) && areCompatibleTypes(colNameType, colNameMax) && ! isMaximumLessThanMinimum(colNameType, colNameMin, colNameMax);
    }
    public static void validate(CreateTableParams p) throws DBAppException {
        if(MetadataReader.search(p.getTableName()) != -1) {
            throw new TableAlreadyExistsException(p.getTableName());
        }
        if(! isClusteringKeyExistingInColumns(p.getClusteringKey(), p.getColNameType())) {
            throw new ClusteringKeyIsNotReferencedInTable(p.getClusteringKey(), "colNameType");
        }
        if(! isClusteringKeyExistingInColumns(p.getClusteringKey(), p.getColNameMin())) {
            throw new ClusteringKeyIsNotReferencedInTable(p.getClusteringKey(), "colNameMin");
        }
        if(! isClusteringKeyExistingInColumns(p.getClusteringKey(), p.getColNameMax())) {
            throw new ClusteringKeyIsNotReferencedInTable(p.getClusteringKey(), "colNameMax");
        }
        checkIfValidTypes(p.getColNameType());
        if(! areInputsValid(p.getColNameType(), p.getColNameMin(), p.getColNameMax())) {
            throw new ColumnsAreNotCompatibleException();
        }
    }
}
