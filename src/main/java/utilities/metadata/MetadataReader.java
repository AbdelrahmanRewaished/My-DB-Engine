package utilities.metadata;

import com.opencsv.CSVReader;
import engine.DBApp;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MetadataReader{
    private static MetadataReader instance;
    private List<CSVRecord> records;
    private MetadataReader() {
        try {
            Reader formatReader = Files.newBufferedReader(Paths.get(Metadata.getCSVFileLocation()));
            records = CSVFormat.DEFAULT.parse(formatReader).getRecords();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static synchronized MetadataReader getInstance() {
        if(instance == null) {
            instance = new MetadataReader();
        }
        return instance;
    }
    public static synchronized List<CSVRecord> getCSVRecords() {
        return getInstance().records;
    }
    public static int search(String tableName) {
        List<CSVRecord> records = getCSVRecords();
        for(int i = 0; i < records.size(); i++) {
            MetadataRecord currentCSVRecord = new MetadataRecord(records.get(i));
            String currentTableName = currentCSVRecord.getTableName();
            if(currentTableName.equals(tableName)) {
                return i;
            }
        }
        return -1;
    }
    public static List<String> getTableColumns(int tableInfoIndex, String tableName) {
        List<CSVRecord> csvRecords = getCSVRecords();
        List<String> columnNames = new ArrayList<>();
        for(int i = tableInfoIndex; i < csvRecords.size(); i++) {
            MetadataRecord currentRecord = new MetadataRecord(csvRecords.get(i));
            if(! currentRecord.getTableName().equals(tableName)) {
                break;
            }
            columnNames.add(currentRecord.getColumnName());
        }
        return columnNames;
    }
    public static Hashtable<String, String> getTableColNameType(int tableInfoIndex, String tableName) {
        List<CSVRecord> csvRecords = getCSVRecords();
        Hashtable<String, String> colNameType = new Hashtable<>();
        for(int i = tableInfoIndex; i < csvRecords.size(); i++) {
            MetadataRecord currentRecord = new MetadataRecord(csvRecords.get(i));
            if(! currentRecord.getTableName().equals(tableName)) {
                break;
            }
            colNameType.put(currentRecord.getColumnName(), currentRecord.getColumnType());
        }
        return colNameType;
    }
    public static String getCorrespondingColumnType(String tableName, String columnName, int tableInfoIndex) {
        List<CSVRecord> csvRecords = MetadataReader.getCSVRecords();
        String requiredType = null;
        for(int i = tableInfoIndex; i < csvRecords.size(); i++) {
            MetadataRecord currentRecord = new MetadataRecord(csvRecords.get(i));
            if(! currentRecord.getTableName().equals(tableName)) {
                break;
            }
            if(currentRecord.getColumnName().equals(columnName)) {
                requiredType = currentRecord.getColumnType();
                break;
            }
        }
        return requiredType;
    }
}
