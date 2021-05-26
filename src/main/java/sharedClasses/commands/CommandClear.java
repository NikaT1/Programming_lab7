package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;
import sharedClasses.User;

import java.sql.SQLException;

/**
 * Класс для команды clear, которая очищает коллекцию.
 */

public class CommandClear extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandClear(User user) {
        super("clear", "очистить коллекцию", 0, false, user);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        String s = "Элементы пользователя успешно удалены";
        synchronized (priorityQueue.getCollection()) {
            try {
                priorityQueue.clear(getUser());
            } catch (SQLException e) {
                s = "Элементы пользователя не удалены, так как возникла проблема с подключением к БД";
            }
        }
        return Serialization.serializeData(s);
    }
}
