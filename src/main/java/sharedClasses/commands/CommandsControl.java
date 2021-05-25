package sharedClasses.commands;

import sharedClasses.User;

import java.util.HashMap;

/**
 * Класс для создания объектов комманд и их хранения.
 */

public class CommandsControl {
    /**
     * Поле, использующееся для хранения объектов команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandsControl(User user) {
        commands.put("help", new Help(user));
        commands.put("show", new Show(user));
        commands.put("info", new Info(user));
        commands.put("add", new Add(user));
        commands.put("update", new UpdateId(user));
        commands.put("remove_by_id", new RemoveById(user));
        commands.put("clear", new CommandClear(user));
        commands.put("execute_script", new ExecuteScript(user));
        commands.put("exit", new CommandExit(user));
        commands.put("remove_head", new RemoveHead(user));
        commands.put("add_if_max", new AddIfMax(user));
        commands.put("add_if_min", new AddIfMin(user));
        commands.put("average_of_meters_above_sea_level", new AverageOfMetersAboveSeaLevel(user));
        commands.put("group_counting_by_meters_above_sea_level", new GroupCountingByMetersAboveSeaLevel(user));
        commands.put("print_ascending", new PrintAscending(user));
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
