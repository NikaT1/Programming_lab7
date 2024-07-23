package com.console_project.server.command.user_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.UserService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды add user, которая регистрирует нового пользователя.
 */

@AllArgsConstructor
public class AddUserCommand<T> implements Command<T> {

    private final UserService userService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public CommandResponse execute(UserRequest<T> request) {
        if (userService.addUser(request.user())) {
            return new CommandResponse("OK", "Добавлен новый пользователь");
        }
        return new CommandResponse("ERROR", "Неверные данные для создания пользователя");
    }

}
