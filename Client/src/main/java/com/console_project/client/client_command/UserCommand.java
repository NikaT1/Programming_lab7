package com.console_project.client.client_command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Доступные для пользователя команды.
 */

@AllArgsConstructor
@Getter
public enum UserCommand {
    HELP("help", false),
    INFO("info", false),
    SHOW("show", false),
    ADD("add", true),
    UPDATE_ID("update_id id {element}", true),
    REMOVE_BY_ID("remove_by_id id", false),
    CLEAR("clear", false),
    EXECUTE_SCRIPT("execute_script file_name", false),
    EXIT("exit", false),
    REMOVE_HEAD("remove_head", false),
    ADD_IF_MAX("add_if_max {element}", true),
    ADD_IF_MIN("add_if_min {element}", true),
    AVERAGE_OF_METERS_ABOVE_SEA_LEVEL("average_of_meters_above_sea_level", false),
    GROUP_COUNTING_BY_METERS_ABOVE_SEA_LEVEL("group_counting_by_meters_above_sea_level", false),
    PRINT_ASCENDING("print_ascending", false);

    private final String name;
    private final boolean needObject;
}
