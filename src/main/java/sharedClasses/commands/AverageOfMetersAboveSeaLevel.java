package sharedClasses.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;
import sharedClasses.User;

/**
 * Класс для команды average_of_meters_above_sea_level, которая выводит среднее значение поля metersAboveSeaLevel
 * для всех элементов коллекции.
 */

public class AverageOfMetersAboveSeaLevel extends Command {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public AverageOfMetersAboveSeaLevel(User user) {
        super("average_of_meters_above_sea_level", "вывести среднее значение поля metersAboveSeaLevel для всех элементов коллекции", 0, false, user);
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
        if (priorityQueue.getCollection().isEmpty())
            result.append("Коллекция пуста; среднее значение поля metersAboveSeaLevel установить невозможно");
        else {
            double answer = priorityQueue.getCollection().stream().
                    filter(city -> city.getMetersAboveSeaLevel() != null)
                    .mapToLong(City::getMetersAboveSeaLevel).average().getAsDouble();
            String numberResult = String.format("%.3f", answer);
            result.append("Среднее значение поля metersAboveSeaLevel для всех элементов коллекции: ").append(numberResult);
        }
        return Serialization.serializeData(result.toString());
    }
}
