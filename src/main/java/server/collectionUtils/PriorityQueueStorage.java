package server.collectionUtils;

import server.DataBaseControl;
import sharedClasses.City;
import sharedClasses.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс для хранения коллекции.
 */
public class PriorityQueueStorage implements StorageInterface<City> {
    /**
     * Путь к исходному файлу.
     */
    private final DataBaseControl dataBaseControl;
    /**
     * Дата создания.
     */
    private final LocalDate creationDate;
    /**
     * Список id.
     */
    private final HashSet<Integer> idSet;
    /**
     * Коллекция.
     */
    private PriorityQueue<City> priorityQueue = new PriorityQueue<>(10, (c1, c2) -> c2.getArea() - c1.getArea());

    public PriorityQueueStorage(DataBaseControl dataBaseControl) {
        this.dataBaseControl = dataBaseControl;
        idSet = new HashSet<>();
        creationDate = LocalDate.now();
    }

    /**
     * Метод, проверяющий элемент коллекции на допустимые знаечения полей.
     *
     * @param city проверяемый объект.
     */
    public void checkElement(City city) throws NumberFormatException {
        if (city.getName().equals("") || city.getCoordinates() == null || city.getCoordinates().getX() == null ||
                city.getCoordinates().getY() == null || city.getClimate() == null || city.getGovernor() == null)
            throw new NullPointerException();
        if (city.getCoordinates().getX() <= -724 || city.getCoordinates().getY() <= -989 || city.getPopulation() <= 0 ||
                city.getArea() <= 0 || city.getGovernor().getAge() != null && city.getGovernor().getAge() <= 0)
            throw new NumberFormatException();
    }

    /**
     * Метод, возвращающий список занятых id.
     *
     * @return список занятых id.
     */
    public HashSet<Integer> getIdSet() {
        return idSet;
    }

    /**
     * Метод, генерирующий id.
     *
     * @return сгенерированное id.
     */
    public Integer generateId() throws IllegalStateException {
        int id;
        int count = 0;
        IllegalStateException e = new IllegalStateException();
        if (Collections.max(idSet) == Integer.MAX_VALUE) {
            id = 1;
            count += 1;
        } else id = Collections.max(idSet) + 1;
        while (!idSet.add(id)) {
            if (id == Integer.MAX_VALUE) {
                id = 1;
                count += 1;
            } else id += 1;
            if (count == 2) throw e;
        }
        return id;
    }

    /**
     * Метод, возвращающий дату создания коллекции.
     *
     * @return дата создания коллекции.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, возвращающий коллекцию.
     *
     * @return коллекция.
     */
    public PriorityQueue<City> getCollection() {
        return priorityQueue;
    }

    public void addToCollection(City c, User user) throws SQLException, ParseException, ClassNotFoundException {
        c = dataBaseControl.addToDataBase(c, user);
        priorityQueue.add(c);
    }

    public void clear(User user) throws SQLException {
        dataBaseControl.clear(user);
        final String s = user.getLogin();
        priorityQueue = priorityQueue.stream().
                filter(c -> !c.getOwner().equals(s)).
                collect(Collectors.toCollection(PriorityQueue::new));
    }

    public boolean remove(City city, User user) throws SQLException {
        boolean flag = dataBaseControl.remove(city, user);
        if (flag) priorityQueue.remove(city);
        return flag;
    }

    public boolean update(City oldCity, City city, int id, User user) throws SQLException {
        boolean flag = dataBaseControl.update(city, id, user);
        if (flag) {
            priorityQueue.remove(oldCity);
            city.setId(id);
            priorityQueue.add(city);
        }
        return flag;
    }

    public City pollFromQueue() {
        return priorityQueue.poll();
    }

}
