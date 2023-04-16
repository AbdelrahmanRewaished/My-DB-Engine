package utilities.metadata;

import com.opencsv.CSVWriter;
import engine.operations.paramters.CreateTableParams;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MetaDataWriter {
    private static MetaDataWriter instance;
    private CSVPrinter appender;
    private CSVPrinter printer;

    private MetaDataWriter() {
        try {
            appender = new CSVPrinter(new FileWriter( Metadata.getCSVFileLocation(), true), CSVFormat.DEFAULT);
            printer = new CSVPrinter(new FileWriter(Metadata.getCSVFileLocation()), CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static MetaDataWriter getInstance() {
        if(instance == null) {
            instance = new MetaDataWriter();
        }
        return instance;
    }
    private static CSVPrinter getAppender() {
        return getInstance().appender;
    }
    private static CSVPrinter getPrinter() {
        return getInstance().printer;
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
