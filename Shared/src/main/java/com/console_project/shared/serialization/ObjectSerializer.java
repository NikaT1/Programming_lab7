package com.console_project.shared.serialization;

public interface ObjectSerializer<T> {
    T deserializeObject(byte[] bytes);

    byte[] serializeObject(T t);
}
