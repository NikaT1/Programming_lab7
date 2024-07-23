package com.console_project.server.service;

import com.console_project.server.exception.TooMuchElementsException;

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

    boolean addElement(T t) throws TooMuchElementsException;

    boolean deleteById(int id);

    int getSize();

    T removeHead();

    boolean updateById(int id, T t);

    boolean addElementIfMax(T t) throws TooMuchElementsException;

    boolean addElementIfMin(T t) throws TooMuchElementsException;

    double averageOfMetersAboveSeaLevel();

    void clear(String key);

    Map<Long, Long> groupCountingByMetersAboveSeaLevel();

    Stream<T> getCollectionSortedStream();
}
