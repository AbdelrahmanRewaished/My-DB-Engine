package utilities.validation;

import engine.elements.Record;
import engine.exceptions.DBAppException;
import engine.exceptions.TableDoesNotExistException;
import engine.exceptions.insertion_exceptions.PrimaryKeyCannotBeNull;
import engine.operations.insertion.InsertIntoTableParams;
import utilities.metadata.MetadataReader;

import static utilities.validation.CommonValidator.checkIfValuesInRange;
import static utilities.validation.CommonValidator.checkValuesValidity;

public class InsertionValidator {

    private InsertionValidator(){}
    private static void checkIfPrimaryKeyIsInserted(String tableName, Record record) throws DBAppException {
        String tableClusteringKey = MetadataReader.getClusteringKey(tableName);
        if(! record.containsKey(tableClusteringKey)) {
            throw new PrimaryKeyCannotBeNull(tableName, tableClusteringKey);
        }
    }
    public static void validate(InsertIntoTableParams p) throws DBAppException{
        if(MetadataReader.search(p.getTableName()) == -1) {
            throw new TableDoesNotExistException(p.getTableName());
        }
        checkValuesValidity(p.getTableName(), p.getRecord());
        checkIfValuesInRange(p.getTableName(), p.getRecord());
        checkIfPrimaryKeyIsInserted(p.getTableName(), p.getRecord());
    }
}
