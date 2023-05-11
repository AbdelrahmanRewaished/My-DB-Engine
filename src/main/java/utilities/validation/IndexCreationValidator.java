package utilities.validation;

import engine.elements.IndexMetaInfo;
import engine.elements.Table;
import engine.exceptions.ColumnAlreadyIndexedException;
import engine.exceptions.ColumnDoesNotExistException;
import engine.exceptions.DBAppException;
import engine.exceptions.TableDoesNotExistException;
import engine.operations.creation.CreateIndexParams;
import utilities.metadata.MetadataReader;

public class IndexCreationValidator {
    private IndexCreationValidator(){}

    public static void validateElementsExistence(CreateIndexParams params) throws DBAppException {
        if(MetadataReader.search(params.getTableName()) == -1) {
            throw new TableDoesNotExistException(params.getTableName());
        }
        for(String columnName: params.getIndexedColumns()) {
            if(MetadataReader.getTableColumnMetadataRecord(params.getTableName(), columnName) == null) {
                throw new ColumnDoesNotExistException(params.getTableName(), columnName);
            }
        }
    }
    public static void validateIndexedColumnsExistence(Table table, String[] columnNames) throws DBAppException {
        for(IndexMetaInfo metaInfo: table.getIndicesInfo()) {
            for(String columnName: columnNames) {
                if(metaInfo.isContainingIndexColumn(columnName)) {
                    throw new ColumnAlreadyIndexedException(table.getName(), metaInfo.getName(), columnName);
                }
            }
        }
    }
}
