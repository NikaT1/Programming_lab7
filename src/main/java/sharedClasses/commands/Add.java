package sharedClasses.commands;


import sharedClasses.elementsOfCollection.City;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

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
    public Add(User user, CommandsControl commandsControl) {
        super("add", "добавить новый элемент в коллекцию", 0, true, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<City> priorityQueue) {
        StringBuilder result = new StringBuilder();
        try {
            City city = this.getCity();
            synchronized (priorityQueue.getCollection()) {
                priorityQueue.addToCollection(city, getUser());
            }
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
