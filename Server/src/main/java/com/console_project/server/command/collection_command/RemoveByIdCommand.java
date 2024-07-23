package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды remove_by_id, которая удаляет элемент из коллекции по его id.
 */

@AllArgsConstructor
public class RemoveByIdCommand<T> implements Command<T> {

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    public CommandResponse execute(UserRequest<T> request) {
        CommandResponse response;
        try {
            int id = Integer.parseInt(request.params());
            dataService.deleteById(id);
            response = new CommandResponse("OK", "Удаление завершено");
        } catch (NumberFormatException e) {
            response = new CommandResponse("ERROR", "Неправильный формат id");
        }
        return response;
    }
}
