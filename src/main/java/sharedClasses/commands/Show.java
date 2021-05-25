package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;

/**
 * Класс для команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */

public class Show extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Show() {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, false);
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
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста").append('\n');
        else
            priorityQueue.getCollection().stream().
                    sorted((city1, city2) -> city2.getName().compareTo(city1.getName())).
                    forEach(city -> result.append(city.toString()).append('\n'));
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}