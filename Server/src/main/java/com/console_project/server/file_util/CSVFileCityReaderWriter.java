package com.console_project.server.file_util;


import com.console_project.shared.model.City;
import com.console_project.shared.model.Climate;
import com.console_project.shared.model.Coordinates;
import com.console_project.shared.model.Human;
import lombok.AllArgsConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Класс для чтения данных из файла и создания элементов коллекции.
 */

@AllArgsConstructor
public class CSVFileCityReaderWriter implements FileReaderWriter<City> {
    private final String fileName;
    private final String FIELDS = "id,name,x,y,creationDate,area,population,metersAboveSeaLevel," +
            "establishmentDate,agglomeration,climate,age";

    @Override
    public List<City> parse() {
        List<City> initCollection = new ArrayList<>();
        /* По заданию требуется BufferedInputStream:
        InputStreamReader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(fileName)));
        Scanner scanner = new Scanner(reader); */
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            String[] nextLine = scanner.nextLine().split(",");
            HashMap<String, Integer> fields = new HashMap<>();
            for (int i = 0; i < nextLine.length; i++) {
                fields.put(nextLine[i], i);
            }

            while (scanner.hasNext()) {
                nextLine = scanner.nextLine().split(",", -1);
                City city = new City();
                city.setId(Integer.parseInt(nextLine[fields.get("id")]));
                city.setName(nextLine[fields.get("name")]);
                city.setCoordinates(new Coordinates(Float.parseFloat(nextLine[fields.get("x")]), Integer.parseInt(nextLine[fields.get("y")])));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                city.setCreationDate(Instant.ofEpochMilli(formatter.parse(nextLine[fields.get("creationDate")]).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
                city.setArea(Integer.parseInt(nextLine[fields.get("area")]));
                city.setPopulation(Long.parseLong(nextLine[fields.get("population")]));
                if (!nextLine[fields.get("metersAboveSeaLevel")].isEmpty())
                    city.setMetersAboveSeaLevel(Long.parseLong(nextLine[fields.get("metersAboveSeaLevel")]));
                else city.setMetersAboveSeaLevel(null);
                if (!nextLine[fields.get("establishmentDate")].isEmpty()) {
                    city.setEstablishmentDate(formatter.parse(nextLine[fields.get("establishmentDate")]));
                } else city.setEstablishmentDate(null);
                if (!nextLine[fields.get("agglomeration")].isEmpty())
                    city.setAgglomeration(Integer.parseInt(nextLine[fields.get("agglomeration")]));
                else city.setAgglomeration(null);
                city.setClimate(Climate.valueOf(nextLine[fields.get("climate")]));
                if (!nextLine[fields.get("age")].isEmpty()) {
                    city.setGovernor(new Human(Integer.parseInt(nextLine[fields.get("age")])));
                } else city.setGovernor(new Human(null));
                initCollection.add(city);
            }
        } catch (ParseException | NumberFormatException e) {
            //todo add com.console_project.exception handling
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return initCollection;
    }

    @Override
    public void write(Stream<City> collectionStream) {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.write(FIELDS + "\n");

            collectionStream.forEach(c -> writeObject(c, printWriter));
            printWriter.flush();
        } catch (IOException e) {
            //todo add com.console_project.exception handling
        }
    }

    private void writeObject(City city, PrintWriter writer) {
        writer.write(city.getId() + ",");
        writer.write(city.getName() + ",");
        writer.write(city.getCoordinates().getX() + ",");
        writer.write(city.getCoordinates().getY() + ",");
        writer.write(city.getCreationDate() + ",");
        writer.write(city.getArea() + ",");
        writer.write(city.getPopulation() + ",");
        if (city.getMetersAboveSeaLevel() == null) writer.write(",");
        else writer.write(city.getMetersAboveSeaLevel() + ",");
        if (city.getEstablishmentDate() == null) writer.write(",");
        else writer.write(city.getEstablishmentDate() + ",");
        if (city.getAgglomeration() == null) writer.write(",");
        else writer.write(city.getAgglomeration() + ",");
        writer.write(city.getClimate() + ",");
        if (city.getGovernor().getAge() == null) writer.write(",");
        else writer.write(city.getGovernor().getAge() + "");
        writer.write("\n");
    }
}


