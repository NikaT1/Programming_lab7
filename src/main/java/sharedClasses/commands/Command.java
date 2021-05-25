package sharedClasses.commands;

import server.IOForClient;
import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.User;

import java.io.Serializable;

/**
 * Абстрактный класс для всех команд.
 */

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 147364832874L;
    /**
     * Дополнительная информация о команде.
     */
    private final String someInformation;
    /**
     * Название команды.
     */
    private final String name;
    private final int amountOfArguments;
    private String argument;
    private City city;
    private final boolean needCity;
    private final User user;

    /**
     * Конструктор.
     *
     * @param name            название команды.
     * @param someInformation дополнительная информация о команде.
     */
    public Command(String name, String someInformation, int amountOfArguments, boolean needCity, User user) {
        this.name = name;
        this.someInformation = someInformation;
        this.amountOfArguments = amountOfArguments;
        this.needCity = needCity;
        this.user = user;
    }

    /**
     * Метод, исполняющий команду.
     *
     * @param ioForClient     объект, через который производится ввод/вывод.
     * @param commandsControl объект, содержащий объекты доступных команд.
     * @param priorityQueue   хранимая коллекция.
     * @throws Exception в случае ошибки при выполнении команды.
     */
    public abstract byte[] doCommand(IOForClient ioForClient, CommandsControl commandsControl, PriorityQueueStorage priorityQueue) throws Exception;

    /**
     * Метод, возвращающий название команды.
     *
     * @return название команды.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, возвращающий информацию о команде.
     *
     * @return информация о команде.
     */
    public String getSomeInformation() {
        return someInformation;
    }

    public User getUser() { return user; }

    public int getAmountOfArguments() {
        return amountOfArguments;
    }

    public String getArgument() {
        return argument;
    }

    public City getCity() {
        return city;
    }

    public boolean isNeedCity() {
        return needCity;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Метод, возвращающий объект в строковом представлении.
     *
     * @return объект в строковом представлении.
     */
    public String toString() {
        return getName() + " : " + getSomeInformation();
    }
}
