package engine.operations;

import engine.DBApp;
import engine.DBAppException;
import engine.elements.Table;
import engine.operations.paramters.CreateTableParams;
import utilities.FileHandler;
import utilities.metadata.MetaDataWriter;
import utilities.validation.Validator;
import utilities.serialization.Deserializer;
import utilities.serialization.Serializer;

import java.util.HashMap;

public class Creation{
    private final CreateTableParams createTableParams;
    public Creation(CreateTableParams createTableParams) {
        this.createTableParams = createTableParams;
    }

    public synchronized void createTable() throws DBAppException {
        Validator.checkCreationValidity(createTableParams);
        String tableLocation = FileHandler.createFolder(DBApp.getTablesRootFolder() + createTableParams.getTableName());
        Table table = new Table(createTableParams.getTableName(), tableLocation, createTableParams.getClusteringKey());
        HashMap<String, Table> serializedTablesInfo = (HashMap<String, Table>) Deserializer.deserialize(DBApp.getSerializedTablesInfoLocation());
        serializedTablesInfo.put(createTableParams.getTableName(), table);
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), serializedTablesInfo);
        MetaDataWriter.addTableInfo(createTableParams);
    }
}
