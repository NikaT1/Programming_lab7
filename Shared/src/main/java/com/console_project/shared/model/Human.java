package com.console_project.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Класс Human.
 */

@AllArgsConstructor
@Getter
@Setter
public class Human implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Возраст человека. Значение поля должно быть больше 0.
     */
    private final Integer age;

    public String toString() {
        return "возраст человека: " + age;
    }
}