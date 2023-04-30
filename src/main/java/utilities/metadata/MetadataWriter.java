package utilities.metadata;

import engine.DBApp;
import engine.exceptions.DBAppException;
import engine.operations.creation.CreateTableParams;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MetadataWriter {
    private MetadataWriter() {}
    private static CSVPrinter getAppender() {
        CSVPrinter appender;
        try {
            FileWriter fileWriter = new FileWriter(DBApp.getCSVFileLocation(), true);
            appender = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return appender;
    }
    private static CSVPrinter getPrinter() {
        CSVPrinter printer;
        try {
            FileWriter fileWriter = new FileWriter(DBApp.getCSVFileLocation());
            printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return printer;
    }
    public static synchronized void addTableInfo(CreateTableParams params) {
        CSVPrinter appender = getAppender();
        for(String columnName: params.getColNameType().keySet()) {
            String type = params.getColNameType().get(columnName);
            String minValue = params.getColNameMin().get(columnName), maxValue = params.getColNameMax().get(columnName);
            String isClusteringKey = columnName.equals(params.getClusteringKey()) + "";
            try {
                appender.printRecord(params.getTableName(), columnName, type, isClusteringKey, null, null, minValue, maxValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            appender.flush();
            appender.close();
            MetadataReader.setModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static synchronized void deleteTableInfo(int tableInfoIndex, String tableName) throws DBAppException {
        List<CSVRecord> records = MetadataReader.getCSVRecords();
        while(! records.isEmpty() && tableInfoIndex < records.size()) {
            MetadataRecord currentRecord = new MetadataRecord(records.get(tableInfoIndex));
            if(! currentRecord.getTableName().equals(tableName)) {
                break;
            }
            records.remove(tableInfoIndex);
        }
        try {
            CSVPrinter printer = getPrinter();
            printer.printRecords(records);
            printer.flush();
            printer.close();
            MetadataReader.setModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
