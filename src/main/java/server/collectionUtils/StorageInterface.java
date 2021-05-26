package server.collectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Интерфейс для классов, которые хранят коллекцию.
 *
 * @param <T> тип элементов коллекции.
 */
public interface StorageInterface<T> {

    void checkElement(T element) throws NumberFormatException;

    LocalDate getCreationDate();

    Collection<T> getCollection();

}
