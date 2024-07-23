package com.console_project.server.command.user_command;

import com.console_project.server.command.Command;
import com.console_project.server.command.TypeOfCommand;
import com.console_project.server.service.UserService;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.AllArgsConstructor;

/**
 * Класс для команды check user, которая проверяет существование и корректность данных пользователя.
 */

@AllArgsConstructor
public class CheckUserCommand<T> implements Command<T> {

    private final UserService userService;
    private final TypeOfCommand type;

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public CommandResponse execute(UserRequest<T> request) {
        if (userService.findUser(request.user())) {
            return new CommandResponse("OK", "Пользователь найден и его данные корректны");
        }
        return new CommandResponse("ERROR", "Неверные данные для входа в аккаунт");
    }

}
