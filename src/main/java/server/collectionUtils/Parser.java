package server.collectionUtils;

import sharedClasses.City;
import sharedClasses.Climate;
import sharedClasses.Coordinates;
import sharedClasses.Human;

import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс для чтения данных из файла и создания элементов коллекции.
 */

public class Parser implements ParserInterface {
    private final PriorityQueueStorage priorityQueue;

    /**
     * Конструктор.
     *
     * @param priorityQueue объект класса, в котором будет хранится коллекция.
     */
    public Parser(PriorityQueueStorage priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    /**
     * Метод, осуществляющий парсинг файла.
     *
     * @param lines входящий поток данных из файла.
     */
    public void parseFile(InputStreamReader lines) throws ParseException, NumberFormatException {
        /*Scanner scanner = new Scanner(lines);
        String[] nextLine;
        HashMap<String, Integer> fields = new HashMap<>();
        nextLine = scanner.nextLine().split(",");
        int i = 0;
        for (String str : nextLine) {
            fields.put(str, i);
            i++;
        }
        while (scanner.hasNext()) {
            nextLine = scanner.nextLine().split(",", -1);
            City city = new City();
            city.setId(Integer.parseInt(nextLine[fields.get("id")]));
            if (!priorityQueue.getIdSet().add(city.getId())) throw new NumberFormatException();
            city.setName(nextLine[fields.get("name")]);
            city.setCoordinates(new Coordinates(Float.parseFloat(nextLine[fields.get("x")]), Integer.parseInt(nextLine[fields.get("y")])));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            city.setCreationDate(Instant.ofEpochMilli(formatter.parse(nextLine[fields.get("creationDate")]).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            city.setArea(Integer.parseInt(nextLine[fields.get("area")]));
            city.setPopulation(Long.parseLong(nextLine[fields.get("population")]));
            if (!nextLine[fields.get("metersAboveSeaLevel")].equals(""))
                city.setMetersAboveSeaLevel(Long.parseLong(nextLine[fields.get("metersAboveSeaLevel")]));
            else city.setMetersAboveSeaLevel(null);
            if (!nextLine[fields.get("establishmentDate")].equals("")) {
                city.setEstablishmentDate(formatter.parse(nextLine[fields.get("establishmentDate")]));
            } else city.setEstablishmentDate(null);
            if (!nextLine[fields.get("agglomeration")].equals(""))
                city.setAgglomeration(Integer.parseInt(nextLine[fields.get("agglomeration")]));
            else city.setAgglomeration(null);
            city.setClimate(Climate.valueOf(nextLine[fields.get("climate")]));
            if (!nextLine[fields.get("age")].equals("")) {
                city.setGovernor(new Human(Integer.parseInt(nextLine[fields.get("age")])));
            } else city.setGovernor(new Human(null));
            priorityQueue.checkElement(city);
            priorityQueue.addToCollection(city);
        }*/
    }
}


