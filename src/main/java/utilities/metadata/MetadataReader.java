package utilities.metadata;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
class MetadataReader{
    private static MetadataReader instance;
    CSVParser parser;
    private MetadataReader() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(Metadata.getCSVFileLocation()));
            parser = new CSVParser(reader, CSVFormat.DEFAULT);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static CSVParser getCSV() {
        if(instance == null) {
            instance = new MetadataReader();
        }
        return instance.parser;
    }

}
