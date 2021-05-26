package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;
import sharedClasses.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Класс для команды add_if_min, которая добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего
 * элемента этой коллекции.
 */

public class AddIfMin extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AddIfMin(User user) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0, true, user);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        try {
            synchronized (priorityQueue.getCollection()) {
                City city = this.getCity();
                if (priorityQueue.getCollection().stream().min(Comparator.comparingInt(City::getArea)).isPresent()) {
                    City minCity = priorityQueue.getCollection().stream().min(Comparator.comparingInt(City::getArea)).get();
                    if (city.getArea() < minCity.getArea()) {
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
