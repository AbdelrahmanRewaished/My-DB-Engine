package engine.operations.creation;

import engine.DBApp;
import engine.elements.Table;
import engine.exceptions.DBAppException;
import utilities.FileHandler;
import utilities.metadata.MetadataWriter;
import utilities.serialization.Serializer;
import utilities.validation.CreationValidator;

public class Creation{
    private final CreateTableParams createTableParams;
    public Creation(CreateTableParams createTableParams) {
        this.createTableParams = createTableParams;
    }

    public synchronized void createTable() throws DBAppException {
        CreationValidator.validate(createTableParams);
        String tableLocation = FileHandler.createFolder(DBApp.getTablesRootFolder() + createTableParams.getTableName());
        Table table = new Table(createTableParams.getTableName(), tableLocation, createTableParams.getClusteringKey());
        Serializer.serialize(DBApp.getTableInfoFileLocation(createTableParams.getTableName()), table);
        MetadataWriter.addTableInfo(createTableParams);
    }
}
