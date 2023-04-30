package utilities.validation;

import engine.exceptions.DBAppException;
import engine.exceptions.InvalidTypeException;
import engine.exceptions.TableDoesNotExistException;
import engine.exceptions.update_exceptions.UpdateListCannotBeEmptyException;
import engine.exceptions.update_exceptions.UpdateListCannotContainPrimaryKeyException;
import engine.operations.update.UpdateTableParams;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import static utilities.datatypes.DatabaseTypesHandler.getType;
import static utilities.datatypes.DatabaseTypesHandler.isCompatibleTypes;
import static utilities.validation.CommonValidator.checkIfValuesInRange;
import static utilities.validation.CommonValidator.checkValuesValidity;

public class UpdateValidator {
    private UpdateValidator(){}
    private static void checkIfCorrectClusteringKeyValueType(String tableName, String clusteringKeyValue) throws DBAppException {
        for(MetadataRecord record: MetadataReader.getTableMetadataRecords(tableName)) {
            if(! record.isClusteringKey()) {
                continue;
            }
            if(! isCompatibleTypes(record.getColumnType(), clusteringKeyValue)) {
                throw new InvalidTypeException(clusteringKeyValue, record.getColumnType(), getType(clusteringKeyValue));
            }
        }
    }

    public static void validate(UpdateTableParams p) throws DBAppException{
        if(MetadataReader.search(p.getTableName())== -1) {
            throw new TableDoesNotExistException(p.getTableName());
        }
        String clusteringKey = MetadataReader.getClusteringKey(p.getTableName());
        if(p.getColNameValue().containsKey(clusteringKey)) {
            throw new UpdateListCannotContainPrimaryKeyException();
        }
        checkValuesValidity(p.getTableName(), p.getColNameValue());
        checkIfValuesInRange(p.getTableName(), p.getColNameValue());
        if(p.getClusteringKeyValue() != null) {
            checkIfCorrectClusteringKeyValueType(p.getTableName(), p.getClusteringKeyValue());
        }
    }
}
