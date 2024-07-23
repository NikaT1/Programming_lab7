package com.console_project.server.command;

import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;

import java.util.HashMap;

public class CommandInvoker<T> {

    private final HashMap<String, Command<T>> commands = new HashMap<>();

    public void registerCommand(Command<T> command) {
        commands.put(command.getName(), command);
    }

    public Command<T> getCommand(TypeOfCommand command) {
        return commands.get(command.getName());
    }

    public CommandResponse processRequest(UserRequest<T> request) {
        return commands.get(request.command()).execute(request);
    }
}
