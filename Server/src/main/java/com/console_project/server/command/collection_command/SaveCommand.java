package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.file_util.FileReaderWriter;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды save, которая сохраняет в файл коллекцию.
 */

@AllArgsConstructor
public class SaveCommand<T> implements Command<T> {

    private final FileReaderWriter readerWriter;
    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    public CommandResponse execute(UserRequest<T> request) {
        CommandResponse response;
        try {
            readerWriter.write(dataService.getCollectionStream());
            response = new CommandResponse("OK", "Коллекция сохранена");
        } catch (Exception e) {
            response = new CommandResponse("ERROR", e.getMessage());
        }
        return response;
    }
}
