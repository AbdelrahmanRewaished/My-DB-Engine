package utilities;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;


public class FileHandler {
    private FileHandler(){}

    public static String createFolder(String location) {
        File folder = new File(location);
        folder.mkdirs();
        return folder.getAbsolutePath();
    }
    public static void deleteFolder(String location) {
        File folder = new File(location);
        try {
            FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteFile(String location) {
        File file = new File(location);
        file.delete();
    }
    public static void createFile(String location) {
        File file = new File(location);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isFileExisting(String location) {
        File dir = new File(location);
        return dir.exists();
    }
}
