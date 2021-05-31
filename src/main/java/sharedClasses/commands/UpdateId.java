package sharedClasses.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;
import sharedClasses.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для команды update, которая обновляет значение элемента коллекции по его id.
 */

public class UpdateId extends Command {
    private static final long serialVersionUID = 147364832874L;

    /**
     * Конструктор, присваивающий имя и дополнительную информацию о команде.
     */
    public UpdateId(User user, CommandsControl commandsControl) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", 1, true, user, commandsControl);
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient   объект, через который производится ввод/вывод.
     * @param priorityQueue хранимая коллекция.
     */
    public byte[] doCommand(IOForClient ioForClient, PriorityQueueStorage priorityQueue) {
        StringBuilder result = new StringBuilder();
        synchronized (priorityQueue.getCollection()) {
            try {
                int id = Integer.parseInt(getArgument());
                List<City> cities = priorityQueue.getCollection().stream().
                        filter(city -> city.getId() == id).
                        collect(Collectors.toList());
                if (cities.size() > 0) {
                    City city = getCity();
                    if (priorityQueue.update(cities.get(0), city, id, getUser()))
                        result.append("обновление элемента успешно завершено");
                    else result.append("обновление элемента не осуществлено!");
                } else result.append("Элемент с id ").append(id).append(" не существует");
            } catch (NumberFormatException e) {
                result.append("неправильный формат id");
            } catch (SQLException e) {
                result.append("Возникла ошибка при попытке соединения с БД, объект не обновлен");
            }
        }
        return Serialization.serializeData(result.toString());
    }
}
