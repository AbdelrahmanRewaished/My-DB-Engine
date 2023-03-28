package utilities.serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Deserializer {
    public static Serializable deserialize(String fileLocation) {
        try (FileInputStream fis = new FileInputStream(fileLocation);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (Serializable) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
