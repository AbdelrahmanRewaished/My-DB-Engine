package utilities.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {
    private Serializer(){}
    public static void serialize(String fileLocation, Serializable serializable) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            ObjectOutputStream oos = new ObjectOutputStream(fos) ;
            oos.writeObject(serializable);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
