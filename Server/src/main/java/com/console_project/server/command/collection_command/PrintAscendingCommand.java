package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс для команды print_ascending, которая выводит элементы коллекции в порядке возрастания.
 */

@AllArgsConstructor
public class PrintAscendingCommand<T> implements Command<T> {

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    public CommandResponse execute(UserRequest<T> request) {
        Stream<T> stream = dataService.getCollectionSortedStream();
        String result = stream.map(Object::toString)
                .collect(Collectors.joining(";\n", "Текущая коллекция\n", ""));
        return new CommandResponse("OK", result);
    }
}
