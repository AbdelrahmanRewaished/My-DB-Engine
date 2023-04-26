package utilities.validation;

import engine.exceptions.DBAppException;
import engine.exceptions.TableDoesNotExistException;
import engine.operations.deletion.DeleteFromTableParams;
import utilities.metadata.MetadataReader;

import static utilities.validation.CommonValidator.checkValuesValidity;

public class DeletionValidator {
    private DeletionValidator(){}
    public static void validate(DeleteFromTableParams p) throws DBAppException {
        if(MetadataReader.search(p.getTableName()) == -1) {
            throw new TableDoesNotExistException(p.getTableName());
        }
        checkValuesValidity(p.getTableName(), p.getColNameValue());
    }
}
