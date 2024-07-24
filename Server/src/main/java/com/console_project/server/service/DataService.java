package com.console_project.server.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Интерфейс для классов, которые управляют доступом к коллекции.
 *
 * @param <T> тип элементов коллекции.
 */
public interface DataService<T> {

    LocalDate getCreationDate();

    Stream<T> getCollectionStream();

    boolean addElement(T t);

    boolean deleteById(int id);

    int getSize();

    T removeHead();

    boolean updateById(int id, T t);

    boolean addElementIfMax(T t);

    boolean addElementIfMin(T t);

    double averageOfMetersAboveSeaLevel();

    void clear(String key);

    Map<Long, Long> groupCountingByMetersAboveSeaLevel();

    Stream<T> getCollectionSortedStream();
}
