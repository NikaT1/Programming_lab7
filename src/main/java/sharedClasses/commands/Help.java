package sharedClasses.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;

/**
 * Класс для команды help, которая выводит справку по доступным коммандам.
 */

public class Help extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public Help() {
        super("help", "вывести справку по доступным командам", 0, false);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder("Доступные команды:" + '\n');
        for (Command command : commandsControl.getCommands().values()) {
            result.append(command.toString()).append('\n');
        }
        result.delete(result.length() - 1, result.length());
        return Serialization.serializeData(result.toString());
    }
}
