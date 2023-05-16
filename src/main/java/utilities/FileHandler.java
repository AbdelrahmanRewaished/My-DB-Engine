package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    static List<String> combinations = new ArrayList<>();
    static void getCombinations(int start, String word) {
        if(start == word.length()) {
            combinations.add(word);
            return;
        }
        String small = word.substring(0, start) + word.substring(start, start + 1).toLowerCase() + word.substring(start + 1);
        getCombinations(start + 1, small);
        String capital = word.substring(0, start) + word.substring(start, start + 1).toUpperCase() + word.substring(start + 1);
        getCombinations(start + 1, capital);
    }
    static void print() {
        for(int i = 0; i < combinations.size(); i++) {
            System.out.print(String.format("'%s'", combinations.get(i)));
            if(i < combinations.size() - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
        getCombinations(0, "index");
        System.out.println(combinations);
        print();
    }
}
