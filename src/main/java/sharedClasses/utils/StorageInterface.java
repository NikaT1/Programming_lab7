package sharedClasses.utils;

import sharedClasses.elementsOfCollection.City;

import java.sql.SQLException;
import java.text.ParseException;
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

    void addToCollection(T c, User user) throws SQLException, ParseException, ClassNotFoundException;

    void clear(User user) throws SQLException;

    boolean remove(City city, User user) throws SQLException;

    boolean update(T oldCity, T city, int id, User user) throws SQLException;

    T pollFromQueue();
}
