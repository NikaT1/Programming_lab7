package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды help, которая выводит справку по доступным коммандам.
 */

@AllArgsConstructor
public class HelpCommand<T> implements Command<T> {

    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }
    public CommandResponse execute(UserRequest<T> request) {
        StringBuilder result = new StringBuilder("Доступные команды:\n");
        for (TypeOfCommand command : TypeOfCommand.values()) {
            result.append(command.getName()).append(": ").append(command.getInfo()).append("\n");
        }
        return new CommandResponse("OK", result.toString());
    }
}
