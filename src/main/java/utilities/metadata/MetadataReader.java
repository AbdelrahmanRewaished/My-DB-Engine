package utilities.metadata;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class MetadataReader{
    private static MetadataReader instance;
    private List<CSVRecord> records;
    private MetadataReader() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(Metadata.getCSVFileLocation()));
            records = CSVFormat.DEFAULT.parse(reader).getRecords();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<CSVRecord> getCSV() {
        if (instance == null) {
            instance = new MetadataReader();
        }
        return instance.records;
    }
}
