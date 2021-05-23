package server.collectionUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Интерфейс для классов, которые хранят коллекцию.
 *
 * @param <T> тип элементов коллекции.
 */
public interface StorageInterface<T> {

    void checkElement(T element) throws NumberFormatException;

    HashSet<Integer> getIdSet();

    Integer generateId() throws IllegalStateException;

    LocalDate getCreationDate();

    PriorityQueue<T> getCollection();

}
