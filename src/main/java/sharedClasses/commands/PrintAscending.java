package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;
import sharedClasses.User;

import java.util.Comparator;

/**
 * Класс для команды print_ascending, которая выводит элементы коллекции в порядке возрастания.
 */

public class PrintAscending extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public PrintAscending(User user) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания", 0, false, user);
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
        else priorityQueue.getCollection().stream().
                sorted(Comparator.comparing(City::getName)).
                forEach(city -> result.append(city.toString()).append('\n'));
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}
