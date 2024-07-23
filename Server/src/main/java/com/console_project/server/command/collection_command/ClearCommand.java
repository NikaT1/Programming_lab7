package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды clear, которая очищает коллекцию.
 */

@AllArgsConstructor
public class ClearCommand<T> implements Command<T> {

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }
    public CommandResponse execute(UserRequest<T> request) {
        dataService.clear("");
        return new CommandResponse("OK", "Коллекция успешно очищена");
    }
}
