package sharedClasses;

import java.io.*;

public class Serialization {
    public Object deserializeData(byte[] bytes) {
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(input);
            return objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] serializeData(Object object) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
            objectOutputStream.writeObject(object);
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
