package com.console_project.server.file_util;

import java.util.List;
import java.util.stream.Stream;

/**
 * Интерфейс для классов, которые осуществляют парсинг.
 */
public interface FileReaderWriter<T> {

    List<T> parse();

    void write(Stream<T> collectionStream);
}
