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
        if(FileHandler.isFileExisting(DBApp.getRootDatabaseFolder() + "/DBEngine")) {
            return;
        }
        String root1 = FileHandler.createFolder(DBApp.getRootDatabaseFolder() + "/DBEngine");
        createMetadataFile();
        String root2 = FileHandler.createFolder(root1 + "/tables");
        Serializer.serialize(root2 + "/serializedTablesInfo.txt", new HashMap<>());
    }
}
