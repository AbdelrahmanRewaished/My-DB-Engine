package utilities.metadata;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class MetaDataWriter {
    private static MetaDataWriter instance;
    private CSVWriter writer;

    private MetaDataWriter() {
        try {
            writer = new CSVWriter(new FileWriter( Metadata.getCSVFileLocation(), true));
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
    public static void addTableInfo(String tableName, String clusteringKey, Hashtable<String, String> colNameType, Map<String, String> colNameMin, Map<String, String> colNameMax) {
        for(String columnName: colNameType.keySet()) {
            String type = colNameType.get(columnName);
            String minValue = colNameMin.get(columnName), maxValue = colNameMax.get(columnName);
            String isClusteringKey = columnName.equals(clusteringKey) + "";
            String[] columnData = {tableName, columnName, type, isClusteringKey, null, null, minValue, maxValue};
            getInstance().writer.writeNext(columnData);
        }
        try {
            getInstance().writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
