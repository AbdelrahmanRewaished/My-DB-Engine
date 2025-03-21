package utilities.serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Deserializer {
    private Deserializer(){}
    public static Serializable deserialize(String fileLocation) {
        try {
            FileInputStream fis = new FileInputStream(fileLocation);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Serializable result = (Serializable) ois.readObject();
            ois.close();
            fis.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
