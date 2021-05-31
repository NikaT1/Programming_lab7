package sharedClasses.commands;

import sharedClasses.User;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Класс для создания объектов комманд и их хранения.
 */

public class CommandsControl implements Serializable {
    /**
     * Поле, использующееся для хранения объектов команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandsControl(User user) {
        commands.put("help", new Help(user, this));
        commands.put("show", new Show(user, this));
        commands.put("info", new Info(user, this));
        commands.put("add", new Add(user, this));
        commands.put("update", new UpdateId(user, this));
        commands.put("remove_by_id", new RemoveById(user, this));
        commands.put("clear", new CommandClear(user, this));
        commands.put("execute_script", new ExecuteScript(user, this));
        commands.put("exit", new CommandExit(user, this));
        commands.put("remove_head", new RemoveHead(user, this));
        commands.put("add_if_max", new AddIfMax(user, this));
        commands.put("add_if_min", new AddIfMin(user, this));
        commands.put("average_of_meters_above_sea_level", new AverageOfMetersAboveSeaLevel(user, this));
        commands.put("group_counting_by_meters_above_sea_level", new GroupCountingByMetersAboveSeaLevel(user, this));
        commands.put("print_ascending", new PrintAscending(user, this));
    }

    /**
     * Метод, возвращающий карту команд.
     *
     * @return карту команд.
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
