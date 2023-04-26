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
    public static List<MetadataRecord> getTableMetadataRecords(String tableName) {
        List<MetadataRecord> recordList = new ArrayList<>();
        List<CSVRecord> csvRecords = getCSVRecords();
        for(int i = search(tableName); i < csvRecords.size(); i++) {
            MetadataRecord currentRecord = new MetadataRecord(csvRecords.get(i));
            if(! currentRecord.getTableName().equals(tableName)) {
                return recordList;
            }
            recordList.add(currentRecord);
        }
        return recordList;
    }
    public static MetadataRecord getTableColumnMetadataRecord(String tableName, String columnName) {
        for(MetadataRecord record: getTableMetadataRecords(tableName)) {
            if(record.getColumnName().equals(columnName)) {
                return record;
            }
        }
        return null;
    }
    public static Set<String> getTableColumnNames(String tableName) {
        List<MetadataRecord> records = MetadataReader.getTableMetadataRecords(tableName);
        Set<String> existingTableColumnNames = new HashSet<>();
        for(MetadataRecord record: records) {
            existingTableColumnNames.add(record.getColumnName());
        }
        return existingTableColumnNames;
    }
    public static String getClusteringKey(String tableName) {
        for(MetadataRecord record: getTableMetadataRecords(tableName)) {
            if(record.isClusteringKey()) {
               return record.getColumnName();
            }
        }
        return null;
    }
    public static Hashtable<String, String> getTableColNameType(String tableName) {
        List<MetadataRecord> metadataRecords = getTableMetadataRecords(tableName);
        Hashtable<String, String> colNameType = new Hashtable<>();
        for(MetadataRecord record: metadataRecords) {
            colNameType.put(record.getColumnName(), record.getColumnType());
        }
        return colNameType;
    }
}
