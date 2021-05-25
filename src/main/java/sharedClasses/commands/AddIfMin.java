package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;

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
    public AddIfMin() {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0, true);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        PriorityQueue<City> dop = new PriorityQueue<>(10, Comparator.comparingInt(City::getArea));
        StringBuilder result = new StringBuilder();
        try {
            while (!priorityQueue.getCollection().isEmpty()) {
                City city1 = priorityQueue.pollFromQueue();
                dop.add(city1);
            }
            City city = this.getCity();
            if (dop.peek() != null) {
                if (city.getArea() < dop.peek().getArea()) {
                    priorityQueue.addToCollection(city);
                    result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
                } else result.append("В коллекцию не добавлен элемент: ").append(city.toString());
            } else {
                priorityQueue.addToCollection(city);
                result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
            }
            while (!dop.isEmpty()) {
                priorityQueue.addToCollection(dop.poll());
            }
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            result.append("Возникла ошибка при попытке соединения с БД, новый объект не добавлен");
        }
        return Serialization.serializeData(result.toString());
    }
}
