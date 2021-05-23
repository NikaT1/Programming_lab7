package sharedClasses;

import java.io.Serializable;

/**
 * Класс Coordinates.
 */

public class Coordinates implements Serializable {
    /**
     * Координата х. Значение поля должно быть больше -742, Поле не может быть null.
     */
    private final Float x;
    /**
     * Координата y. Значение поля должно быть больше -989, Поле не может быть null.
     */
    private final Integer y;

    /**
     * Конструктор.
     *
     * @param x координата х.
     * @param y координата y.
     */
    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод, возвращающий значение координаты х.
     *
     * @return координата х.
     */
    public Float getX() {
        return x;
    }

    /**
     * Метод, возвращающий значение координаты у.
     *
     * @return координата у.
     */
    public Integer getY() {
        return y;
    }

    /**
     * Метод, возвращающий объект в строковом представлении.
     *
     * @return объект в строковом представлении.
     */
    public String toString() {
        return "координаты х: " + x + ", y: " + y;
    }
}