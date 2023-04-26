package utilities.validation;

import engine.elements.Record;
import engine.exceptions.DBAppException;
import engine.exceptions.TableDoesNotExistException;
import engine.exceptions.insertion_exceptions.NotAllFieldsAreReferencedException;
import engine.operations.insertion.InsertIntoTableParams;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.util.ArrayList;
import java.util.List;

import static utilities.validation.CommonValidator.checkIfValuesInRange;
import static utilities.validation.CommonValidator.checkValuesValidity;

public class InsertionValidator {

    private InsertionValidator(){}
    private static void checkIfAllInsertionParametersExist(String tableName, Record record) throws DBAppException {
        List<String> missedColumnNames = new ArrayList<>();
        for(MetadataRecord metadataRecord: MetadataReader.getTableMetadataRecords(tableName)) {
            if(! record.containsKey(metadataRecord.getColumnName())) {
                missedColumnNames.add(metadataRecord.getColumnName());
            }
        }
        if(! missedColumnNames.isEmpty()) {
            throw new NotAllFieldsAreReferencedException(tableName, missedColumnNames);
        }
    }
    public static void validate(InsertIntoTableParams p) throws DBAppException{
        if(MetadataReader.search(p.getTableName()) == -1) {
            throw new TableDoesNotExistException(p.getTableName());
        }
        checkValuesValidity(p.getTableName(), p.getRecord());
        checkIfValuesInRange(p.getTableName(), p.getRecord());
        checkIfAllInsertionParametersExist(p.getTableName(), p.getRecord());
    }
}
