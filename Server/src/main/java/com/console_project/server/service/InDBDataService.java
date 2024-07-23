package com.console_project.server.service;

import com.console_project.server.exception.TooMuchElementsException;
import com.console_project.server.repository.DataRepository;
import com.console_project.shared.model.City;
import lombok.Getter;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InDBDataService implements DataService<City> {

    @Getter
    private final LocalDate creationDate;

    private final DataRepository<City> dataRepository;

    private final Collection<City> collection = Collections.synchronizedCollection(
            new PriorityQueue<>(10, (c1, c2) -> c2.getArea() - c1.getArea()));

    public InDBDataService(DataRepository<City> dataRepository) {
        creationDate = LocalDate.now();
        this.dataRepository = dataRepository;
        init();
    }

    @SneakyThrows
    private void init() {
        collection.clear();
        List<City> initCollection = dataRepository.getAll();
        for (City city : initCollection) {
            if (checkElement(city)) {
                collection.add(city);
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
        return true;
    }

    @Override
    public Stream<City> getCollectionStream() {
        return collection.stream();
    }

    @Override
    public boolean addElement(City city) {
        if (dataRepository.add(city)) {
            init(); //todo поменять на поиск id??
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        Optional<City> curCity;
        synchronized (collection) {
            curCity = collection.stream().filter(e -> e.getId() == id).findFirst();
        }
        if (curCity.isPresent() && dataRepository.remove(curCity.get())) {
            return collection.remove(curCity.get());
        }
        return false;
    }

    @Override
    public int getSize() {
        return collection.size();
    }

    @Override
    public City removeHead() {
        if (!collection.isEmpty()) {
            Optional<City> city;
            synchronized (collection) {
                city = collection.stream().max(Comparator.comparingInt(City::getArea));
            }
            if (city.isPresent() && dataRepository.remove(city.get())) {
                collection.remove(city.get());
                return city.get();
            }
        }
        return null;
    }

    @Override
    public boolean updateById(int id, City city) {
        if (dataRepository.update(id, city)) {
            city.setId(id);
            collection.removeIf(e -> e.getId() == id);
            collection.add(city);
            return true;
        }
        return false;
    }

    @Override
    public boolean addElementIfMin(City city) throws TooMuchElementsException {
        OptionalInt minArea;
        synchronized (collection) {
            minArea = collection.stream().mapToInt(City::getArea).min();
        }
        if (minArea.isEmpty() || minArea.getAsInt() > city.getArea()) {
            return addElement(city);
        }
        return false;
    }

    @Override
    public boolean addElementIfMax(City city) throws TooMuchElementsException {
        OptionalInt maxArea;
        synchronized (collection) {
            maxArea = collection.stream().mapToInt(City::getArea).max();
        }
        if (maxArea.isEmpty() || maxArea.getAsInt() < city.getArea()) {
            return addElement(city);
        }
        return false;
    }

    @Override
    public double averageOfMetersAboveSeaLevel() {
        int sum = 0;
        synchronized (collection) {
            for (City city : collection) {
                sum += city.getMetersAboveSeaLevel();
            }
        }
        return (double) sum / collection.size();
    }

    @Override
    public void clear(String key) {
        if (dataRepository.clear(key)) {
            collection.clear();
        }
    }

    @Override
    public synchronized Map<Long, Long> groupCountingByMetersAboveSeaLevel() {
        return collection.stream().collect(Collectors.groupingBy(City::getMetersAboveSeaLevel, Collectors.counting()));
    }

    @Override
    public synchronized Stream<City> getCollectionSortedStream() {
        return getCollectionStream().sorted(Comparator.comparingInt(City::getArea));
    }
}
