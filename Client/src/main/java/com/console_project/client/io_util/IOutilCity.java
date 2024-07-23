package com.console_project.client.io_util;

import com.console_project.shared.model.City;
import com.console_project.shared.model.Climate;
import com.console_project.shared.model.Coordinates;
import com.console_project.shared.model.Human;
import lombok.RequiredArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, осуществляющий ввод/вывод.
 */

@RequiredArgsConstructor
public class IOutilCity implements IOutil<City> {

    private final String WRONG_INPUT_MESSAGE = "Неверный формат данных, повторите ввод!";
    private final Scanner scanner;

    /**
     * Метод, считывающий и создающий объект класса City.
     *
     * @return новый объект класса City.
     */
    @Override
    public City readObject() throws NumberFormatException, NoSuchElementException {
        return City.builder()
                .name(readName())
                .coordinates(readCoordinates())
                .metersAboveSeaLevel(readMetersAboveSeaLevel())
                .climate(readClimate())
                .area(readArea())
                .creationDate(LocalDate.now())
                .population(readPopulation())
                .establishmentDate(readEstablishmentDate())
                .agglomeration(readAgglomeration())
                .governor(readGovernor())
                .build();
    }

    /**
     * Метод, считывающий значение одного поля.
     *
     * @param message сообщение пользователю.
     * @return значение поля.
     */
    @Override
    public String readField(String message) {
        write(message);
        return scanner.nextLine();
    }

    /**
     * Метод, считывающий ответ пользвателя.
     *
     * @param message сообщение пользователю.
     * @return ответ пользователя.
     */
    @Override
    public boolean readAnswer(String message) {
        while (true) {
            String s = readField(message).toLowerCase();
            switch (s) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Неверный ввод! Введите yes/no");
            }
        }
    }

    /**
     * Метод, считывающий значение поля name.
     *
     * @return значение поля name.
     */
    private String readName() {
        boolean flag = false;
        String name = null;
        while (!flag) {
            flag = true;
            name = readField("Введите название города:");
            if (name.isEmpty()) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return name;
    }

    /**
     * Метод, считывающий значение поля area.
     *
     * @return значение поля area.
     */
    private int readArea() {
        boolean flag = false;
        int area = 1;
        while (!flag) {
            flag = true;
            try {
                area = Integer.parseInt(readField("Введите размер территории (в квадратных метрах):"));
                if (area <= 0) {
                    write("Значение меньше допустимого, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return area;
    }

    /**
     * Метод, считывающий значение поля population.
     *
     * @return значение поля population.
     */
    private long readPopulation() {
        boolean flag = false;
        long population = 1;
        while (!flag) {
            flag = true;
            try {
                population = Long.parseLong(readField("Введите численность населения:"));
                if (population <= 0) {
                    write("Значение меньше допустимого, повторите ввод:");
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return population;
    }

    /**
     * Метод, считывающий значение поля metersAboveSeaLevel.
     *
     * @return значение поля metersAboveSeaLevel.
     */
    private Long readMetersAboveSeaLevel() {
        boolean flag = false;
        Long metersAboveSeaLevel = null;
        while (!flag) {
            flag = true;
            String s = readField("Введите количество метров над уровнем моря:");
            if (s.isEmpty()) {
                return null;
            }
            try {
                metersAboveSeaLevel = Long.parseLong(s);
            } catch (NumberFormatException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return metersAboveSeaLevel;
    }

    /**
     * Метод, считывающий значение поля establishmentDate.
     *
     * @return значение поля establishmentDate.
     */
    private Date readEstablishmentDate() {
        boolean flag = false;
        Date establishmentDate = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        while (!flag) {
            flag = true;
            String s = readField("Введите дату создания (yyyy-MM-dd):");
            if (s.isEmpty()) {
                return null;
            }
            try {
                establishmentDate = formatter.parse(s);
            } catch (ParseException e) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return establishmentDate;
    }

    /**
     * Метод, считывающий значение поля agglomeration.
     *
     * @return значение поля agglomeration.
     */
    private Integer readAgglomeration() {
        boolean flag = false;
        Integer agglomeration = null;
        while (!flag) {
            flag = true;
            String s = readField("Введите размер агломерации (в квадратных метрах):");
            if (s.isEmpty()) {
                return null;
            }
            try {
                agglomeration = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return agglomeration;
    }

    /**
     * Метод, считывающий значение поля climate.
     *
     * @return значение поля climate.
     */
    private Climate readClimate() {
        boolean flag = false;
        Climate climate = null;
        while (!flag) {
            flag = true;
            try {
                climate = Climate.valueOf(readField("Введите тип климата (RAIN_FOREST, MONSOON, HUMIDSUBTROPICAL):"));
            } catch (IllegalArgumentException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return climate;
    }

    /**
     * Метод, считывающий значение поля governor.
     *
     * @return значение поля governor.
     */
    private Human readGovernor() {
        boolean flag = false;
        Integer age = null;
        while (!flag) {
            flag = true;
            String s = readField("Введите возраст губернатора:");
            if (s.isEmpty()) {
                return new Human(null);
            }
            try {
                age = Integer.parseInt(s);
                if (age <= 0) {
                    write(WRONG_INPUT_MESSAGE);
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return new Human(age);
    }

    /**
     * Метод, считывающий значение поля coordinates.
     *
     * @return значение поля coordinates.
     */
    public Coordinates readCoordinates() {
        boolean flag = false;
        Float x = null;
        Integer y = null;
        while (!flag) {
            flag = true;
            try {
                x = Float.parseFloat(readField("Введите координату х:"));
                if (x <= -724) {
                    write(WRONG_INPUT_MESSAGE);
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        flag = false;
        while (!flag) {
            flag = true;
            try {
                y = Integer.parseInt(readField("Введите координату у:"));
                if (y <= -989) {
                    write(WRONG_INPUT_MESSAGE);
                    flag = false;
                }
            } catch (NumberFormatException ex) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return new Coordinates(x, y);
    }
}
