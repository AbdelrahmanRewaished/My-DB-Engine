package engine.operations.dropping;

import engine.DBApp;
import engine.exceptions.DBAppException;
import utilities.FileHandler;
import utilities.metadata.MetadataReader;
import utilities.metadata.MetadataWriter;

public class Dropping {
    private final String tableName;
    public Dropping(String strTableName) {
        tableName = strTableName;
    }
    public void dropTable() throws DBAppException {
        int tableInfoIndex = MetadataReader.search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Could not drop table %s because it does not exist !", tableName));
        }
        FileHandler.deleteFolder(DBApp.getTablesRootFolder() + tableName);
        MetadataWriter.deleteTableInfo(tableInfoIndex, tableName);
    }
}
