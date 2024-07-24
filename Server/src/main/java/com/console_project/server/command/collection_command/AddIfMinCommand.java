package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

import java.util.NoSuchElementException;

/**
 * Класс для команды add_if_min, которая добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего
 * элемента этой коллекции.
 */

@AllArgsConstructor
public class AddIfMinCommand<T> implements Command<T> {

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    public CommandResponse execute(UserRequest<T> request) {
        CommandResponse response = null;
        try {
            boolean result = dataService.addElementIfMin(request.object());
            if (result) {
                response = new CommandResponse("OK", "В коллекцию добавлен новый элемент");
            } else {
                response = new CommandResponse("OK", "В коллекцию не добавлен новый элемент");
            }
        } catch (NoSuchElementException e) {
            response = new CommandResponse("ERROR", "Не указаны значения для создания " +
                    "элемента коллекции. Команда add не выполнена");
        }
        return response;
    }
}
