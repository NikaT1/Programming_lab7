package sharedClasses.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;
import sharedClasses.User;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды remove_by_id, которая удаляет элемент из коллекции по его id.
 */

public class RemoveById extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public RemoveById(User user) {
        super("remove_by_id id", "удалить элемент из коллекции по его id", 1, false, user);
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
        try {
            int id = Integer.parseInt(this.getArgument());
            List<City> cities = priorityQueue.getCollection().stream().
                    filter(city -> city.getId() == id).
                    collect(Collectors.toList());
            if (cities.size() > 0) {
                boolean flag  = priorityQueue.remove(cities.get(0), getUser());
                if (flag) result.append("удаление элемента успешно завершено");
                else result.append("удаление элемента не выполнено!");
            } else result.append("Элемент с id ").append(id).append(" не существует");
        } catch (NumberFormatException e) {
            result.append("неправильный формат id");
        } catch (SQLException e) {
            result.append("ошибка при попытке удаления значения из БД; удаление не выполнено");
        }
        return Serialization.serializeData(result.toString());
    }
}
