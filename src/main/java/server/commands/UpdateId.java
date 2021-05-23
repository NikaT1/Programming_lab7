package server.commands;


import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Serialization;

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
    public UpdateId() {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному", 1, true);
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
            int id = Integer.parseInt(getArgument());
            List<City> cities = priorityQueue.getCollection().stream().
                    filter(city -> city.getId() == id).
                    collect(Collectors.toList());
            if (cities.size() > 0) {
                priorityQueue.getCollection().remove(cities.get(0));
                City city = getCity();
                city.setId(id);
                priorityQueue.addToCollection(city);
                result.append("обновление элемента успешно завершено");
            } else result.append("Элемент с id ").append(id).append(" не существует");
        } catch (NumberFormatException e) {
            result.append("неправильный формат id");
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            result.append("Возникла ошибка при попытке соединения с БД, новый объект не добавлен");
        }
        return Serialization.serializeData(result.toString());
    }
}
