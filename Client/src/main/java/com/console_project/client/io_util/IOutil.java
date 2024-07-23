package com.console_project.client.io_util;

public interface IOutil<T> {

    T readObject();

    String readField(String message);

    boolean readAnswer(String message);

    default void write(String s) {
        System.out.println(s);
    }
}
