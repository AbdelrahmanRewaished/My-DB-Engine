package engine.operations;

import engine.DBApp;
import utilities.FileHandler;
import utilities.metadata.Metadata;
import utilities.serialization.Serializer;

import java.util.HashMap;

public class Initialization {

    private Initialization(){}

    private static void createMetadataFile() {
        FileHandler.createFile(Metadata.getCSVFileLocation());
    }
    public static void init() {
        if(FileHandler.isFileExisting(DBApp.getRootDatabaseFolder())) {
            return;
        }
        FileHandler.createFolder(DBApp.getRootDatabaseFolder());
        createMetadataFile();
        FileHandler.createFolder(DBApp.getTablesRootFolder());
        Serializer.serialize(DBApp.getSerializedTablesInfoLocation(), new HashMap<>());
    }
}
