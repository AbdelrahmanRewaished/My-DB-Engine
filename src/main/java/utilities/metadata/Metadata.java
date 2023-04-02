package utilities.metadata;

import engine.DBApp;

public class Metadata {
    private static final String csvFileLocation = DBApp.getRootDatabaseFolder() + "/metadata.csv";
    public static String getCSVFileLocation() {
        return csvFileLocation;
    }
}
