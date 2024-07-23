package com.console_project.shared.serialization;

import java.io.*;

public class ObjectSerializerImpl<T> implements ObjectSerializer<T> {
    public T deserializeObject(byte[] bytes) {
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(input);
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            //todo exception handling
            return null;
        }
    }

    public byte[] serializeObject(T t) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
            objectOutputStream.writeObject(t);
            return output.toByteArray();
        } catch (IOException e) {
            //todo exception handling
            return null;
        }
    }
}
