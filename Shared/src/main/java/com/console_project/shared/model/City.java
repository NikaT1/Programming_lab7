package com.console_project.shared.model;

import lombok.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Класс City.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private String owner;


    public String getEstablishmentDate() {
        if (establishmentDate != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(establishmentDate.getTime());
        } else return null;
    }

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