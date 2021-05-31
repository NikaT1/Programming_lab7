package server.collectionUtils;

import java.time.LocalDate;
import java.util.Collection;

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
