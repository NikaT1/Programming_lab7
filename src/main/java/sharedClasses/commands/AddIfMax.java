package sharedClasses.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;

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
    public AddIfMax() {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", 0, true);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        City city = this.getCity();
        StringBuilder result = new StringBuilder();
        try {
            if (priorityQueue.getCollection().peek() != null) {
                if (city.getArea() > priorityQueue.getCollection().peek().getArea()) {
                    priorityQueue.addToCollection(city);
                    result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
                } else result.append("В коллекцию не добавлен элемент: ").append(city.toString());
            } else {
                priorityQueue.addToCollection(city);
                result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
            }
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            result.append("Возникла ошибка при попытке соединения с БД, новый объект не добавлен");
        }
        return Serialization.serializeData(result.toString());
    }
}
