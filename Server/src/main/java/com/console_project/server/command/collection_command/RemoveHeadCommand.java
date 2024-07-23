package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды remove_head, которая выводит и удаляет первый элемент из коллекции.
 */

@AllArgsConstructor
public class RemoveHeadCommand<T> implements Command<T> {

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }
    public CommandResponse execute(UserRequest<T> request) {
        if (dataService.getSize() == 0) return new CommandResponse("OK", "Коллекция пуста");
        T t = dataService.removeHead();
        return new CommandResponse("OK", "Удаление элемента успешно завершено: " + t.toString());
    }
}
