package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static final String propertiesLocation = "src/main/java/DBApp.config";
    private final Properties properties;
    private static PropertiesReader instance;
    private PropertiesReader() {
        properties = new Properties();
        try {
            FileReader reader = new FileReader(propertiesLocation);
            properties.load(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Properties getProperties() {
        if(instance == null) {
            instance = new PropertiesReader();
        }
        return instance.properties;
    }

    public static int getProperty(String key) {
        return Integer.parseInt(getProperties().getProperty(key));
    }
}
