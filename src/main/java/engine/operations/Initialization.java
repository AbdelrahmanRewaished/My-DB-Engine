package engine.operations;

import engine.DBApp;
import utilities.FileHandler;
import utilities.serialization.Serializer;

import java.util.HashMap;

public class Initialization {

    private Initialization(){}

    private static void createMetadataFile() {
        FileHandler.createFolder(DBApp.getMetadataFolderLocation());
        FileHandler.createFile(DBApp.getCSVFileLocation());
    }
    public static void init() {
        if(FileHandler.isFileExisting(DBApp.getTablesRootFolder())) {
            return;
        }
        createMetadataFile();
        FileHandler.createFolder(DBApp.getTablesRootFolder());
    }
}
