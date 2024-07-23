package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды update, которая обновляет значение элемента коллекции по его id.
 */

@AllArgsConstructor
public class UpdateIdCommand<T> implements Command<T> {

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
            if (dataService.updateById(id, request.object())) {
                response = new CommandResponse("OK", "Обновление проведено");
            } else {
                response = new CommandResponse("OK", "Объект не найден");
            }
        } catch (NumberFormatException e) {
            response = new CommandResponse("ERROR", "Неправильный формат id");
        }
        return response;
    }
}
