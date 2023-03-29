package engine.operations;

import engine.DBApp;
import utilities.metadata.Metadata;
import utilities.serialization.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Initialization {

    private Initialization(){}

    private static String createFolder(String folderName) {
        File folder = new File(folderName);
        folder.mkdirs();
        return folder.getAbsolutePath() + "/";
    }
    private static void createMetadataFile() {
        File file = new File(Metadata.getCSVFileLocation());
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isInitialized() {
        File dir = new File(DBApp.getRootDatabaseFolder() + "/DBEngine");
        return dir.exists();
    }
    public static void init() {
        if(isInitialized()) {
            return;
        }
        String root1 = createFolder(DBApp.getRootDatabaseFolder() + "DBEngine");
        createMetadataFile();
        String root2 = createFolder(root1 + "tables");
        Serializer.serialize(root2 + "/serializedTablesInfo.txt", new HashMap<>());
    }

    public static void main(String[] args) {
        init();
    }
}
