package utilities.metadata;

import engine.DBApp;
import engine.elements.index.Boundary;
import engine.exceptions.DBAppException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utilities.datatypes.DatabaseTypesHandler;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MetadataReader {
    private MetadataReader() {}
    private static List<CSVRecord> records;
    private static CSVParser parser;
    private static boolean isModified;

    static void setModified() {
        isModified = true;
    }

    static synchronized List<CSVRecord> getCSVRecords() throws DBAppException {
        if(records == null || isModified) {
            try {
                Reader formatReader = Files.newBufferedReader(Paths.get(DBApp.getCSVFileLocation()));
                parser = new CSVParser(formatReader, CSVFormat.DEFAULT);
                records = CSVFormat.DEFAULT.parse(formatReader).getRecords();
                formatReader.close();
                isModified = false;
            }
            catch (IOException e) {
                throw new DBAppException("Database Engine has not been initialized");
            }
        }
        return records;
    }
    static CSVParser getParser() {
        if(parser == null) {
            Reader formatReader;
            try {
                formatReader = Files.newBufferedReader(Paths.get(DBApp.getCSVFileLocation()));
                parser = new CSVParser(formatReader, CSVFormat.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return parser;
    }

    public static int search(String tableName) throws DBAppException {
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
    public static List<MetadataRecord> getTableMetadataRecords(String tableName) throws DBAppException {
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
    public static MetadataRecord getTableColumnMetadataRecord(String tableName, String columnName) throws DBAppException {
        for(MetadataRecord record: getTableMetadataRecords(tableName)) {
            if(record.getColumnName().equals(columnName)) {
                return record;
            }
        }
        return null;
    }
    public static Set<String> getTableColumnNames(String tableName) throws DBAppException {
        List<MetadataRecord> records = MetadataReader.getTableMetadataRecords(tableName);
        Set<String> existingTableColumnNames = new HashSet<>();
        for(MetadataRecord record: records) {
            existingTableColumnNames.add(record.getColumnName());
        }
        return existingTableColumnNames;
    }
    public static Boundary getTableColumnsBoundaries(String tableName, String[] columnNames) throws DBAppException {
        Hashtable<String, Comparable> colNameMin = new Hashtable<>(), colNameMax = new Hashtable<>();
        for(String columnName: columnNames) {
            MetadataRecord record1 = getTableColumnMetadataRecord(tableName, columnName);
            colNameMin.put(columnName, DatabaseTypesHandler.getObject(record1.getMinValue(), record1.getColumnType()));
            colNameMax.put(columnName, DatabaseTypesHandler.getObject(record1.getMaxValue(), record1.getColumnType()));

        }
        return new Boundary(colNameMin, colNameMax);
    }
    public static String getClusteringKey(String tableName) throws DBAppException {
        for(MetadataRecord record: getTableMetadataRecords(tableName)) {
            if(record.isClusteringKey()) {
               return record.getColumnName();
            }
        }
        return null;
    }
    public static Hashtable<String, String> getTableColNameType(String tableName) throws DBAppException {
        List<MetadataRecord> metadataRecords = getTableMetadataRecords(tableName);
        Hashtable<String, String> colNameType = new Hashtable<>();
        for(MetadataRecord record: metadataRecords) {
            colNameType.put(record.getColumnName(), record.getColumnType());
        }
        return colNameType;
    }
}
