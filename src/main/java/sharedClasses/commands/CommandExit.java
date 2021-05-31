package sharedClasses.commands;


import sharedClasses.elementsOfCollection.City;
import sharedClasses.utils.IOInterface;
import sharedClasses.utils.Serialization;
import sharedClasses.utils.StorageInterface;
import sharedClasses.utils.User;

/**
 * Класс для команды exit, которая завершает программу без сохранения в файл коллекции.
 */

public class CommandExit extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public CommandExit(User user, CommandsControl commandsControl) {
        super("exit", "завершить программу (без сохранения в файл)", 0, false, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOInterface ioForClient, StorageInterface<City> priorityQueue) {
        String result = "Работа приложения завершается";
        return Serialization.serializeData(result);
    }
}
