package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;
import sharedClasses.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для команды group_counting_by_meters_above_sea_level, которая группирует элементы коллекции по значению
 * поля metersAboveSeaLevel и выводит количество элементов в каждой группе.
 */

public class GroupCountingByMetersAboveSeaLevel extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public GroupCountingByMetersAboveSeaLevel(User user) {
        super("group_counting_by_meters_above_sea_level", "сгруппировать элементы коллекции по значению поля metersAboveSeaLevel, вывести количество элементов в каждой группе", 0, false, user);
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
        if (priorityQueue.getCollection().isEmpty()) result.append("Коллекция пуста" + '\n');
        else {
            Map<Object, List<City>> groups = priorityQueue.getCollection().stream().
                    filter(city -> city.getMetersAboveSeaLevel() != null).
                    collect(Collectors.groupingBy(City::getMetersAboveSeaLevel));
            groups.forEach((meters, cities) -> result.append("Группа ").append(meters).append(" (м):").append('\n').append(print(cities)).append('\n'));
        }
        result.delete(result.length() - 2, result.length());
        return Serialization.serializeData(result.toString());
    }

    public String print(List<City> cities) {
        StringBuilder result = new StringBuilder();
        for (City city : cities) {
            result.append(city.toString()).append('\n');
        }
        return result.toString();
    }
}
