package engine.operations.dropping;

import engine.DBApp;
import engine.exceptions.DBAppException;
import engine.elements.Table;
import utilities.FileHandler;
import utilities.metadata.MetaDataWriter;
import utilities.metadata.MetadataReader;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;

public class Dropping {
    private String tableName;
    public Dropping(String strTableName) {
        tableName = strTableName;
    }
    public void drop() throws DBAppException {
        int tableInfoIndex = MetadataReader.search(tableName);
        if(tableInfoIndex == -1) {
            throw new DBAppException(String.format("Could not drop table %s because it does not exist !", tableName));
        }
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        serializedTablesInfo.remove(tableName);
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        FileHandler.deleteFolder(DBApp.getTablesRootFolder() + tableName);
        MetaDataWriter.deleteTableInfo(tableInfoIndex, tableName);
    }
}
