package com.console_project.server.command.collection_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.DataService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * Класс для команды group_counting_by_meters_above_sea_level, которая группирует элементы коллекции по значению
 * поля metersAboveSeaLevel и выводит количество элементов в каждой группе.
 */

@AllArgsConstructor
public class GroupCountingByMetersAboveSeaLevelCommand<T> implements Command<T> {

    private final DataService<T> dataService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    public CommandResponse execute(UserRequest<T> request) {
        if (dataService.getSize() == 0) return new CommandResponse("OK", "Коллекция пуста");
        Map<Long, Long> answer = dataService.groupCountingByMetersAboveSeaLevel();
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Long, Long> entry : answer.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return new CommandResponse("OK", result.toString());
    }
}
