package sharedClasses;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Класс City.
 */

public class City implements Serializable {
    /**
     * Id города. Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически.
     */
    private Integer id;
    /**
     * Название города. Поле не может быть null, Строка не может быть пустой.
     */
    private String name;
    /**
     * Координаты города. Поле не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Дата создания объекта. Поле не может быть null, Значение этого поля должно генерироваться автоматически Поле не может быть null.
     */
    private LocalDate creationDate;
    /**
     * Размер территории города. Значение поля должно быть больше 0.
     */
    private int area = 0;
    /**
     * Численность населения города. Значение поля должно быть больше 0.
     */
    private long population;
    /**
     * Количество метров над уровнем моря.
     */
    private Long metersAboveSeaLevel;
    /**
     * Дата основания города.
     */
    private Date establishmentDate;
    /**
     * Размер агломерации.
     */
    private Integer agglomeration;
    /**
     * Тип климата. Поле не может быть null.
     */
    private Climate climate;
    /**
     * Губернатор города. Поле не может быть null.
     */
    private Human governor;

    /**
     * Метод, присваивающий название города.
     *
     * @param name название города.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод, присваивающий значение id.
     *
     * @param id идентификатор объекта.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Метод, присваивающий координаты города.
     *
     * @param coordinates координаты города.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Метод, присваивающий значение дата создания объекта.
     *
     * @param creationDate дата создания объекта.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Метод, присваивающий размер территории.
     *
     * @param area размер территории.
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * Метод, присваивающий значение численность населения.
     *
     * @param population численность населения.
     */
    public void setPopulation(long population) {
        this.population = population;
    }

    /**
     * Метод, присваивающий значение количество метров над уровнем моря.
     *
     * @param metersAboveSeaLevel количество метров над уровнем моря.
     */
    public void setMetersAboveSeaLevel(Long metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    /**
     * Метод, присваивающий значение дата основания.
     *
     * @param establishmentDate дата основания.
     */
    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    /**
     * Метод, присваивающий значение размера агломерации.
     *
     * @param agglomeration размер агломерации.
     */
    public void setAgglomeration(Integer agglomeration) {
        this.agglomeration = agglomeration;
    }

    /**
     * Метод, присваивающий значение полю климат.
     *
     * @param climate тип климата.
     */
    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    /**
     * Метод, присваивающий значение полю губернатор.
     *
     * @param governor объект класса Human.
     */
    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    /**
     * Метод, возвращающий id объекта.
     *
     * @return id объекта.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Метод, возвращающий название города.
     *
     * @return название города.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, возвращающий координаты города.
     *
     * @return координаты города.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод, возвращающий дату создания объекта.
     *
     * @return дата создания объекта
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, возвращающий размер территории.
     *
     * @return размер территории.
     */
    public int getArea() {
        return area;
    }

    /**
     * Метод, возвращающий численность населения.
     *
     * @return численность населения.
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Метод, возвращающий количество метров над уровнем моря.
     *
     * @return количество метров над уровнем моря.
     */
    public Long getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    /**
     * Метод, возвращающий дату основания.
     *
     * @return дата основания.
     */
    public String getEstablishmentDate() {
        if (establishmentDate != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(establishmentDate.getTime());
        } else return null;
    }

    /**
     * Метод, возвращающий размер агломерации.
     *
     * @return размер агломерации.
     */
    public Integer getAgglomeration() {
        return agglomeration;
    }

    /**
     * Метод, возвращающий тип климата.
     *
     * @return тип климата.
     */
    public Climate getClimate() {
        return climate;
    }

    /**
     * Метод, возвращающий значение поля губернатор.
     *
     * @return значение поля губернатор.
     */
    public Human getGovernor() {
        return governor;
    }

    /**
     * Метод, возвращающий объект в строковом представлении.
     *
     * @return объект в строковом представлении.
     */
    public String toString() {
        return getId() + ", название: " + getName() + ", " +
                coordinates.toString() + ", дата создания: " +
                getCreationDate() + ", площадь территории: " +
                getArea() + ", численность населения: " + getPopulation() +
                ", кол-во метров над уровнем моря: " + getMetersAboveSeaLevel() +
                ", дата основания: " + getEstablishmentDate() +
                ", площадь агломерации: " + getAgglomeration() +
                ", климат: " + getClimate() + ", возраст губернатора: " +
                getGovernor().getAge() + ".";
    }
}