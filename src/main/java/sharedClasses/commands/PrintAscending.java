package sharedClasses.commands;

import sharedClasses.elementsOfCollection.City;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

import java.util.Comparator;

/**
 * Класс для команды print_ascending, которая выводит элементы коллекции в порядке возрастания.
 */

public class PrintAscending extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public PrintAscending(User user, CommandsControl commandsControl) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<City> priorityQueue) {
        StringBuilder result = new StringBuilder();
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста").append('\n');
        else priorityQueue.getCollection().stream().
                sorted(Comparator.comparing(City::getName)).
                forEach(city -> result.append(city.toString()).append('\n'));
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}
