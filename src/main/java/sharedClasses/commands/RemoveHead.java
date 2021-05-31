package sharedClasses.commands;


import server.serverUtils.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.elementsOfCollection.City;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.User;

import java.sql.SQLException;

/**
 * Класс для команды remove_head, которая выводит и удаляет первый элемент из коллекции.
 */

public class RemoveHead extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveHead(User user, CommandsControl commandsControl) {
        super("remove_head", "вывести первый элемент коллекции и удалить его", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        synchronized (priorityQueue.getCollection()) {
            try {
                if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста");
                else {
                    City city = priorityQueue.pollFromQueue();
                    boolean flag = priorityQueue.remove(city, getUser());
                    if (flag) result.append("удаление элемента успешно завершено");
                    else {
                        result.append("удаление элемента не выполнено!");
                        priorityQueue.getCollection().add(city);
                    }
                }
            } catch (SQLException e) {
                result.append("ошибка при попытке удаления значения из БД; удаление не выполнено");
            }
        }
        return Serialization.serializeData(result.toString());
    }
}
