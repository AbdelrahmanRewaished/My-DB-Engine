package engine.operations.creation;

import engine.DBApp;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.FileHandler;
import utilities.metadata.MetaDataWriter;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;
import utilities.validation.CreationValidator;

import java.util.HashMap;

public class Creation{
    private final CreateTableParams createTableParams;
    public Creation(CreateTableParams createTableParams) {
        this.createTableParams = createTableParams;
    }

    public synchronized void createTable() throws DBAppException {
        CreationValidator.validate(createTableParams);
        String tableLocation = FileHandler.createFolder(DBApp.getTablesRootFolder() + createTableParams.getTableName());
        Table table = new Table(createTableParams.getTableName(), tableLocation, createTableParams.getClusteringKey());
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        serializedTablesInfo.put(createTableParams.getTableName(), table);
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        MetaDataWriter.addTableInfo(createTableParams);
    }
}
