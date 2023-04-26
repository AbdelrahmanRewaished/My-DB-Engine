package utilities.metadata;

import engine.operations.creation.CreateTableParams;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MetaDataWriter {
    private static CSVPrinter appender;
    private static CSVPrinter printer;

    private MetaDataWriter() {}
    private static CSVPrinter getAppender() {
        if(appender == null) {
            try {
                appender = new CSVPrinter(new FileWriter(Metadata.getCSVFileLocation(), true), CSVFormat.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return appender;
    }
    private static CSVPrinter getPrinter() {
       if(printer == null) {
           List<CSVRecord> csvRecords = MetadataReader.getCSVRecords();
           try {
               printer = new CSVPrinter(new FileWriter(Metadata.getCSVFileLocation()), CSVFormat.DEFAULT);
               printer.printRecords(csvRecords);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
       return printer;
    }
    public static synchronized void addTableInfo(CreateTableParams params) {
        for(String columnName: params.getColNameType().keySet()) {
            String type = params.getColNameType().get(columnName);
            String minValue = params.getColNameMin().get(columnName), maxValue = params.getColNameMax().get(columnName);
            String isClusteringKey = columnName.equals(params.getClusteringKey()) + "";
            try {
                getAppender().printRecord(params.getTableName(), columnName, type, isClusteringKey, null, null, minValue, maxValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            getAppender().flush();
            getAppender().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteTableInfo(int tableInfoIndex, String tableName) {
        List<CSVRecord> records = MetadataReader.getCSVRecords();
        while(! records.isEmpty()) {
            MetadataRecord currentRecord = new MetadataRecord(records.get(tableInfoIndex));
            if(! currentRecord.getTableName().equals(tableName)) {
                break;
            }
            records.remove(tableInfoIndex);
        }
        try {
            getPrinter().printRecords(records);
            getPrinter().flush();
            getPrinter().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
