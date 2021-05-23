package server.commands;

import java.util.HashMap;

/**
 * Класс для создания объектов комманд и их хранения.
 */

public class CommandsControl {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Поле, использующееся для хранения объектов команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    {
        commands.put("help", new Help());
        commands.put("show", new Show());
        commands.put("info", new Info());
        commands.put("add", new Add());
        commands.put("update", new UpdateId());
        commands.put("remove_by_id", new RemoveById());
        commands.put("clear", new CommandClear());
        commands.put("execute_script", new ExecuteScript());
        commands.put("exit", new CommandExit());
        commands.put("remove_head", new RemoveHead());
        commands.put("add_if_max", new AddIfMax());
        commands.put("add_if_min", new AddIfMin());
        commands.put("average_of_meters_above_sea_level", new AverageOfMetersAboveSeaLevel());
        commands.put("group_counting_by_meters_above_sea_level", new GroupCountingByMetersAboveSeaLevel());
        commands.put("print_ascending", new PrintAscending());
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
