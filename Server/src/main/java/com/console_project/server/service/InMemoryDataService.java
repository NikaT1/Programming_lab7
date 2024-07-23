package com.console_project.server.service;


import com.console_project.server.exception.TooMuchElementsException;
import com.console_project.shared.model.City;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс для хранения коллекции в памяти.
 */
public class InMemoryDataService implements DataService<City> {

    @Getter
    private final LocalDate creationDate;

    private final HashSet<Integer> idSet;

    private final PriorityQueue<City> priorityQueue = new PriorityQueue<>(10, (c1, c2) -> c2.getArea() - c1.getArea());

    /**
     * @param initCollection изначальное наполнение коллекции
     */
    public InMemoryDataService(List<City> initCollection) {
        idSet = new HashSet<>();
        creationDate = LocalDate.now();
        init(initCollection);
    }

    private void init(List<City> initCollection) {
        for (City city : initCollection) {
            if (checkElement(city)) {
                priorityQueue.add(city);
            }
        }
    }

    private boolean checkElement(City city) throws NumberFormatException {
        if (city.getName().isEmpty() || city.getCoordinates() == null || city.getCoordinates().getX() == null ||
                city.getCoordinates().getY() == null || city.getClimate() == null || city.getGovernor() == null)
            return false;
        if (city.getCoordinates().getX() <= -724 && city.getCoordinates().getY() <= -989 && city.getPopulation() < 0 &&
                city.getArea() <= 0 && (city.getGovernor().getAge() != null && city.getGovernor().getAge() <= 0)) {
            return false;
        }
        return idSet.add(city.getId());
    }

    private Integer generateId() throws TooMuchElementsException {
        int id;
        int count = 0;
        if (!idSet.isEmpty() && Collections.max(idSet) == Integer.MAX_VALUE) {
            id = 1;
            count++;
        } else id = (idSet.isEmpty()) ? 0 : Collections.max(idSet) + 1;
        while (!idSet.add(id)) {
            if (id == Integer.MAX_VALUE) {
                id = 1;
                count++;
            } else id++;
            if (count == 2) throw new TooMuchElementsException();
        }
        return id;
    }

    @Override
    public Stream<City> getCollectionStream() {
        return priorityQueue.stream();
    }

    @Override
    public boolean addElement(City city) throws TooMuchElementsException {
        city.setId(generateId());
        priorityQueue.add(city);
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        Optional<City> curCity = Optional.empty();
        for (City city : priorityQueue) {
            if (city.getId() == id) {
                curCity = Optional.of(city);
                break;
            }
        }
        if (curCity.isPresent()) {
            priorityQueue.remove(curCity.get());
            idSet.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public int getSize() {
        return priorityQueue.size();
    }

    @Override
    public City removeHead() {
        return priorityQueue.poll();
    }

    @Override
    public boolean updateById(int id, City city) {
        boolean isDeleted = deleteById(id);
        if (isDeleted) {
            city.setId(id);
            idSet.add(id);
            priorityQueue.add(city);
            return true;
        }
        return false;
    }

    @Override
    public boolean addElementIfMin(City city) throws TooMuchElementsException {
        OptionalInt min = priorityQueue.stream().mapToInt(City::getArea).min();
        if (min.isEmpty() || min.getAsInt() > city.getArea()) {
            addElement(city);
            return true;
        }
        return false;
    }

    @Override
    public boolean addElementIfMax(City city) throws TooMuchElementsException {
        if (priorityQueue.peek() == null || priorityQueue.peek().getArea() < city.getArea()) {
            addElement(city);
            return true;
        }
        return false;
    }

    @Override
    public double averageOfMetersAboveSeaLevel() {
        int sum = 0;
        for (City city : priorityQueue) {
            sum += city.getMetersAboveSeaLevel();
        }
        return (double) sum / priorityQueue.size();
    }

    @Override
    public void clear(String ignored) {
        priorityQueue.clear();
    }

    @Override
    public Map<Long, Long> groupCountingByMetersAboveSeaLevel() {
        return priorityQueue.stream().collect(Collectors.groupingBy(City::getMetersAboveSeaLevel, Collectors.counting()));
    }

    @Override
    public Stream<City> getCollectionSortedStream() {
        return getCollectionStream().sorted(Comparator.comparingInt(City::getArea));
    }
}
