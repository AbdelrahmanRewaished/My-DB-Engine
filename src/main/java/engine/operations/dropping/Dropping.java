package engine.operations.dropping;

import engine.DBApp;
import engine.exceptions.DBAppException;
import engine.elements.Table;
import utilities.FileHandler;
import utilities.metadata.MetadataWriter;
import utilities.metadata.MetadataReader;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;

@SuppressWarnings("unchecked")
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
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        serializedTablesInfo.remove(tableName);
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        FileHandler.deleteFolder(DBApp.getTablesRootFolder() + tableName);
        MetadataWriter.deleteTableInfo(tableInfoIndex, tableName);
    }
}
