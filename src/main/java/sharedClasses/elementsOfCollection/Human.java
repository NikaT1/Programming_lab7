package sharedClasses.elementsOfCollection;

import java.io.Serializable;

/**
 * Класс Human.
 */

public class Human implements Serializable {
    /**
     * Возраст человека. Значение поля должно быть больше 0.
     */
    private final Integer age;

    /**
     * Конструктор.
     *
     * @param age возраст человека.
     */
    public Human(Integer age) {
        this.age = age;
    }

    /**
     * Метод, возвращающий значение возраста.
     *
     * @return возраст.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Метод, возвращающий объект в строковом представлении.
     *
     * @return объект в строковом представлении.
     */
    public String toString() {
        return "возраст человека: " + age;
    }
}