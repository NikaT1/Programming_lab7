package sharedClasses.utils;

import sharedClasses.elementsOfCollection.City;
import sharedClasses.elementsOfCollection.Climate;
import sharedClasses.elementsOfCollection.Coordinates;
import sharedClasses.elementsOfCollection.Human;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class UserInput {

    IOInterface ioInterface;

    public UserInput(IOInterface ioInterface) {
        this.ioInterface = ioInterface;
    }

    /**
     * Метод, считывающий значение поля name.
     *
     * @return значение поля name.
     */
    private String readName() {
        boolean flag = false;
        String name = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                name = readField("Введите название города:");
                if (name.equals("")) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            name = readField("Введите название города:");
            if (name.equals("")) throw new NumberFormatException();
        }
        return name;
    }

    /**
     * Метод, считывающий значение одного поля.
     *
     * @param message сообщение пользователю.
     * @return значение поля.
     */
    public String readField(String message) {
        String s;
        if (ioInterface.getPrintMessages()) System.out.println(message);
        s = ioInterface.getScanner().nextLine();
        return s;
    }

    /**
     * Метод, считывающий значение поля area.
     *
     * @return значение поля area.
     */
    private int readArea() throws NumberFormatException {
        boolean flag = false;
        int area = 1;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    area = Integer.parseInt(readField("Введите размер территории (в квадратных метрах):"));
                    if (area <= 0) {
                        ioInterface.output("Значение меньше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            area = Integer.parseInt(readField("Введите размер территории (в квадратных метрах):"));
            if (area <= 0) throw new NumberFormatException();
        }
        return area;
    }

    /**
     * Метод, считывающий значение поля population.
     *
     * @return значение поля population.
     */
    private long readPopulation() throws NumberFormatException {
        boolean flag = false;
        long population = 1;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    population = Long.parseLong(readField("Введите численность населения:"));
                    if (population <= 0) {
                        ioInterface.output("Значение меньше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            population = Long.parseLong(readField("Введите численность населения:"));
            if (population <= 0) throw new NumberFormatException();
        }
        return population;
    }

    /**
     * Метод, считывающий значение поля metersAboveSeaLevel.
     *
     * @return значение поля metersAboveSeaLevel.
     */
    private Long readMetersAboveSeaLevel() throws NumberFormatException {
        boolean flag = false;
        Long metersAboveSeaLevel = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                String s = readField("Введите количество метров над уровнем моря:");
                if (s.equals("")) {
                    return null;
                }
                try {
                    metersAboveSeaLevel = Long.parseLong(s);
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            String s = readField("Введите количество метров над уровнем моря:");
            if (s.equals("")) {
                return null;
            }
            metersAboveSeaLevel = Long.parseLong(s);
        }
        return metersAboveSeaLevel;
    }

    /**
     * Метод, считывающий значение поля establishmentDate.
     *
     * @return значение поля establishmentDate.
     */
    private Date readEstablishmentDate() throws ParseException {
        boolean flag = false;
        Date establishmentDate = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                String s = readField("Введите дату создания (yyyy-MM-dd):");
                if (s.equals("")) {
                    return null;
                }
                try {
                    establishmentDate = formatter.parse(s);
                } catch (ParseException e) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            String s = readField("Введите дату создания (yyyy-MM-dd):");
            if (s.equals("")) {
                return null;
            }
            establishmentDate = formatter.parse(s);
        }
        return establishmentDate;
    }

    /**
     * Метод, считывающий значение поля agglomeration.
     *
     * @return значение поля agglomeration.
     */
    private Integer readAgglomeration() throws NumberFormatException {
        boolean flag = false;
        Integer agglomeration = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                String s = readField("Введите размер агломерации (в квадратных метрах):");
                if (s.equals("")) {
                    return null;
                }
                try {
                    agglomeration = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            String s = readField("Введите размер агломерации (в квадратных метрах):");
            if (s.equals("")) {
                return null;
            }
            agglomeration = Integer.parseInt(s);
        }
        return agglomeration;
    }

    /**
     * Метод, считывающий значение поля climate.
     *
     * @return значение поля climate.
     */
    private Climate readClimate() throws NumberFormatException {
        boolean flag = false;
        Climate climate = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    climate = Climate.valueOf(readField("Введите тип климата (RAIN_FOREST, MONSOON, HUMIDSUBTROPICAL):"));
                } catch (IllegalArgumentException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else climate = Climate.valueOf(readField("Введите тип климата (RAIN_FOREST, MONSOON, HUMIDSUBTROPICAL):"));
        return climate;
    }

    /**
     * Метод, считывающий значение поля governor.
     *
     * @return значение поля governor.
     */
    private Human readGovernor() throws NumberFormatException {
        boolean flag = false;
        Integer age = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                String s = readField("Введите возраст губернатора:");
                if (s.equals("")) {
                    return new Human(null);
                }
                try {
                    age = Integer.parseInt(s);
                    if (age <= 0) {
                        ioInterface.output("Неверный формат данных, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            String s = readField("Введите возраст губернатора:");
            if (s.equals("")) {
                return new Human(null);
            }
            age = Integer.parseInt(s);
            if (age <= 0) throw new NumberFormatException();
        }
        return new Human(age);
    }

    /**
     * Метод, считывающий и создающий объект класса City.
     *
     * @return новый объект класса City.
     */
    public City readCity() throws NumberFormatException, ParseException {
        City city = new City();
        city.setName(readName());
        city.setCoordinates(readCoordinates());
        city.setCreationDate(LocalDate.now());
        city.setArea(readArea());
        city.setPopulation(readPopulation());
        city.setMetersAboveSeaLevel(readMetersAboveSeaLevel());
        city.setEstablishmentDate(readEstablishmentDate());
        city.setAgglomeration(readAgglomeration());
        city.setClimate(readClimate());
        city.setGovernor(readGovernor());
        return city;
    }

    /**
     * Метод, считывающий значение поля coordinates.
     *
     * @return значение поля coordinates.
     */
    public Coordinates readCoordinates() throws NumberFormatException {
        boolean flag = false;
        Float x = null;
        Integer y = null;
        if (ioInterface.getPrintMessages()) {
            while (!flag) {
                flag = true;
                try {
                    x = Float.parseFloat(readField("Введите координату х:"));
                    if (x <= -724) {
                        ioInterface.output("Значение меньше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }

            flag = false;
            while (!flag) {
                flag = true;
                try {
                    y = Integer.parseInt(readField("Введите координату у:"));
                    if (y <= -989) {
                        ioInterface.output("Значение меньше допустимого, повторите ввод:");
                        flag = false;
                    }
                } catch (NumberFormatException ex) {
                    ioInterface.output("Неверный формат данных, повторите ввод:");
                    flag = false;
                }
            }
        } else {
            x = Float.parseFloat(readField("Введите координату х:"));
            y = Integer.parseInt(readField("Введите координату у:"));
            if (y <= -989 || x <= -724) throw new NumberFormatException();
        }
        return new Coordinates(x, y);
    }
}
