package sharedClasses.commands;


import server.serverUtils.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.User;

/**
 * Класс для команды info, которая выводит в стандартный поток вывода информацию о коллекции.
 */

public class Info extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Info(User user, CommandsControl commandsControl) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, PriorityQueueStorage priorityQueue) {
        String result = "тип: " + priorityQueue.getCollection().getClass() + '\n' + "дата инициализации: " + priorityQueue.getCreationDate() + '\n' +
                "количество элементов: " + priorityQueue.getCollection().size();
        return Serialization.serializeData(result);
    }
}
