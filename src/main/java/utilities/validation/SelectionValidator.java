package utilities.validation;

import engine.operations.selection.SQLTerm;
import engine.exceptions.DBAppException;
import engine.exceptions.TableDoesNotExistException;
import engine.operations.selection.SelectFromTableParams;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataRecord;

import java.util.Hashtable;
import java.util.List;

import static utilities.validation.CommonValidator.checkValueValidity;

public class SelectionValidator {

    private SelectionValidator(){}

    public static void validate(SelectFromTableParams sp) throws DBAppException {
        Hashtable<String, List<MetadataRecord>> tableMetadataInfo = new Hashtable<>();
        for(SQLTerm term: sp.getSqlTerms()) {
            String tableName = term._strTableName;
            if(! tableMetadataInfo.containsKey(tableName)) {
                if(MetadataReader.search(tableName) == -1) {
                    throw new TableDoesNotExistException(tableName);
                }
                tableMetadataInfo.put(tableName, MetadataReader.getTableMetadataRecords(tableName));
            }
            checkValueValidity(tableMetadataInfo.get(tableName), term._strColumnName, term._objValue);
        }
    }
}
