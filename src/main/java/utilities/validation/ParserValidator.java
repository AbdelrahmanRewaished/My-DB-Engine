package utilities.validation;


import engine.exceptions.ColumnDoesNotExistException;
import engine.exceptions.DBAppException;
import engine.exceptions.InvalidTypeException;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.metadata.MetadataReader;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;


public class ParserValidator {
    private ParserValidator() {}

    // Parser Validation
    public static void checkIfAllColumnsExist(String tableName, List<String> columnNames) throws DBAppException {
        Set<String> existingTableColumnNames = MetadataReader.getTableColumnNames(tableName);
        for(String columnName: columnNames) {
            if(! existingTableColumnNames.contains(columnName)) {
                throw new ColumnDoesNotExistException(tableName, columnName);
            }
        }
    }
    public static void checkIfAllValuesAreInTheCorrectType(Hashtable<String, String> colNameType, List<String> columnNames, List<String> values) throws DBAppException {
        for(int i = 0; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            String value = values.get(i);
            if(! DatabaseTypesHandler.isCompatibleTypes(colNameType.get(columnName), value)) {
                throw new InvalidTypeException(value, colNameType.get(columnName), DatabaseTypesHandler.getType(value));
            }
        }
    }
}
