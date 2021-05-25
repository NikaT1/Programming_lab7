package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;

/**
 * Класс для команды clear, которая очищает коллекцию.
 */

public class CommandClear extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandClear() {
        super("clear", "очистить коллекцию", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        priorityQueue.getCollection().clear();
        return Serialization.serializeData("Коллекция успешно очищена");
    }
}
