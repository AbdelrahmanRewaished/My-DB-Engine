package utilities.validation;

import engine.exceptions.ColumnDoesNotExistException;
import engine.exceptions.DBAppException;
import engine.exceptions.InvalidTypeException;
import engine.exceptions.ValueOutOfRangeException;
import utilities.datatypes.DatabaseTypesHandler;
import utilities.datatypes.DBAppNull;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.util.Hashtable;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CommonValidator {
    private CommonValidator(){}
    private static boolean isValueInRange(MetadataRecord currentRecord, Object value) {
        if(value instanceof DBAppNull) {
            return true;
        }
        Comparable maximum = DatabaseTypesHandler.getObject(currentRecord.getMaxValue(), currentRecord.getColumnType());
        if(value instanceof String) {
            String strValue = (String) value;
            String strMaximum = (String) maximum;
            if(strValue.length() > strMaximum.length()) {
                return false;
            }
        }
        Comparable minimum = DatabaseTypesHandler.getObject(currentRecord.getMinValue(), currentRecord.getColumnType());
        return minimum.compareTo(value) <= 0 && maximum.compareTo(value) >= 0;
    }
    static void checkValueValidity(List<MetadataRecord> metadataRecords, String columnName, Object value) throws DBAppException {
        String tableName = metadataRecords.get(0).getTableName();
        if(columnName == null) {
            return;
        }
        MetadataRecord columnMetadataRecord = MetadataRecord.getRecord(metadataRecords, columnName);
        if(columnMetadataRecord == null) {
            throw new ColumnDoesNotExistException(tableName, columnName);
        }
        if(! DatabaseTypesHandler.isCompatibleTypes(columnMetadataRecord.getColumnType(), value)) {
            String requiredType = columnMetadataRecord.getColumnType();
            String insertedType = value.getClass().getName();
            throw new InvalidTypeException(value.toString(), requiredType, insertedType);
        }
    }
    static void checkIfValuesInRange(String tableName, Hashtable<String, Object> record) throws DBAppException {
        List<MetadataRecord> metadataRecords = MetadataReader.getTableMetadataRecords(tableName);
        for(String columnName: record.keySet()) {
            MetadataRecord columnMetadataRecord = MetadataRecord.getRecord(metadataRecords, columnName);
            Object value = record.get(columnName);
            assert columnMetadataRecord != null;
            if(! isValueInRange(columnMetadataRecord, value)) {
                String minimum = columnMetadataRecord.getMinValue(), maximum = columnMetadataRecord.getMaxValue();
                throw new ValueOutOfRangeException(DatabaseTypesHandler.getString(value), minimum, maximum);
            }
        }
    }
    static void checkValuesValidity(String tableName, Hashtable<String, Object> record) throws DBAppException {
        List<MetadataRecord> metadataRecords = MetadataReader.getTableMetadataRecords(tableName);
        for(String columnName: record.keySet()) {
            if(record.get(columnName) instanceof DBAppNull) {
                continue;
            }
            checkValueValidity(metadataRecords, columnName, record.get(columnName));
        }
    }
}
