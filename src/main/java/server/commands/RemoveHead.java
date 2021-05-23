package server.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;

/**
 * Класс для команды remove_head, которая выводит и удаляет первый элемент из коллекции.
 */

public class RemoveHead extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveHead() {
        super("remove_head", "вывести первый элемент коллекции и удалить его", 0, false);
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
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста");
        else {
            result.append(priorityQueue.pollFromQueue().toString()).append("удаление элемента успешно завершено");
        }
        return Serialization.serializeData(result.toString());
    }
}
