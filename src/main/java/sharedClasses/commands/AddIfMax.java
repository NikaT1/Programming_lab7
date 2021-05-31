package sharedClasses.commands;


import sharedClasses.elementsOfCollection.City;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Класс для команды add_if_max, которая добавляет новый элемент в коллекцию, если его значение превышает значение
 * наибольшего элемента этой коллекции.
 */

public class AddIfMax extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AddIfMax(User user, CommandsControl commandsControl) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", 0, true, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<City> priorityQueue) {
        City city = this.getCity();
        StringBuilder result = new StringBuilder();
        try {
            synchronized (priorityQueue.getCollection()) {
                if (priorityQueue.getCollection().stream().max(City::compareTo).isPresent()) {
                    if (city.getArea() > priorityQueue.getCollection().stream().max(City::compareTo).get().getArea()) {
                        priorityQueue.addToCollection(city, getUser());
                        result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
                    } else result.append("В коллекцию не добавлен элемент: ").append(city.toString());
                } else {
                    priorityQueue.addToCollection(city, getUser());
                    result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
                }
            }
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            result.append("Возникла ошибка при попытке соединения с БД, новый объект не добавлен");
        }
        return Serialization.serializeData(result.toString());
    }
}
