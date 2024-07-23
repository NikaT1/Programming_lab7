package com.console_project.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Класс Coordinates.
 */

@AllArgsConstructor
@Getter
@Setter
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Координата х. Значение поля должно быть больше -742, Поле не может быть null.
     */
    private final Float x;
    /**
     * Координата y. Значение поля должно быть больше -989, Поле не может быть null.
     */
    private final Integer y;

    public String toString() {
        return "координаты х: " + x + ", y: " + y;
    }
}