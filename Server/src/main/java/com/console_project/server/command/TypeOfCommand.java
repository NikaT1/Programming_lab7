package com.console_project.server.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum TypeOfCommands.
 */

@AllArgsConstructor
@Getter
public enum TypeOfCommand {
    ADD_USER("add_user", "регистрация нового пользователя", false),
    CHECK_USER("check_user", "проверка существования и корректности данных пользователя", false),
    HELP("help", "вывести справку по доступным командам", false),
    INFO("info", "вывести в стандартный поток вывода информацию о коллекции " +
            "(тип, дата инициализации, количество элементов и т.д.)", false),
    SHOW("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
            false),
    ADD("add", "добавить новый элемент в коллекцию", true),
    UPDATE_ID("update_id id {element}", "обновить значение элемента коллекции, id которого равен заданному",
            true),
    REMOVE_BY_ID("remove_by_id id", "удалить элемент из коллекции по его id", false),
    CLEAR("clear", "очистить коллекцию", false),
    SAVE("save", "сохранить коллекцию в файл", false),
    EXECUTE_SCRIPT("execute_script file_name", "считать и исполнить скрипт из указанного файла. " +
            "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
            false),
    EXIT("exit", "завершить программу (без сохранения в файл)", false),
    REMOVE_HEAD("remove_head", "вывести первый элемент коллекции и удалить его", false),
    ADD_IF_MAX("add_if_max {element}", "добавить новый элемент в коллекцию, " +
            "если его значение превышает значение наибольшего элемента этой коллекции", true),
    ADD_IF_MIN("add_if_min {element}", "добавить новый элемент в коллекцию, " +
            "если его значение меньше, чем у наименьшего элемента этой коллекции", true),
    AVERAGE_OF_METERS_ABOVE_SEA_LEVEL("average_of_meters_above_sea_level",
            "вывести среднее значение поля metersAboveSeaLevel для всех элементов коллекции", false),
    GROUP_COUNTING_BY_METERS_ABOVE_SEA_LEVEL("group_counting_by_meters_above_sea_level", "сгруппировать " +
            "элементы коллекции по значению поля metersAboveSeaLevel, вывести количество элементов в каждой группе",
            false),
    PRINT_ASCENDING("print_ascending", "вывести элементы коллекции в порядке возрастания", false);

    private final String name;
    private final String info;
    private final boolean needObject;
}
