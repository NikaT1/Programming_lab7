package sharedClasses.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;

/**
 * Класс для команды add, которая добавляет новый элемент в коллекцию.
 */

public class Add extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Add() {
        super("add", "добавить новый элемент в коллекцию", 0, true);
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
            City city = this.getCity();
            priorityQueue.addToCollection(city);
            result.append("В коллекцию добавлен новый элемент: ").append(city.toString());
        } catch (IllegalStateException e) {
            result.append("Ошибка: в коллекции слишком много элементов; объект коллекции не создан");
        } catch (NoSuchElementException e) {
            result.append("В скрипте не указаны значения для создания элемента коллекции. Команда add не выполнена");
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            result.append("Возникла ошибка при попытке соединения с БД, новый объект не добавлен");
            e.printStackTrace();
        }
        return Serialization.serializeData(result.toString());
    }
}
