package com.console_project.client.io_util;

import com.console_project.client.client_command.UserCommand;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.model.User;
import lombok.AllArgsConstructor;

/**
 * Класс для распознавания введенных комманд.
 */

@AllArgsConstructor
public class UserInput<T> {

    private final String INIT_MESSAGE = "Введите команду (список команд - help):";
    private final String EXIT_MESSAGE = "Вы точно хотите завершить выполнение программы?";
    private final String COMMAND_NOT_FOUND_MESSAGE = "Команда не найдена!";
    private final IOutil<T> ioUtil;
    private final User user;

    /**
     * Метод, отвечающий за распознавание команд и формирование userRequest.
     */
    public UserRequest<T> getUserRequest() {
        UserRequest<T> request = null;
        while (request == null) {
            String line = ioUtil.readField(INIT_MESSAGE);
            String[] args = line.split(" ");
            UserCommand UserCommand = null;
            try {
                UserCommand = UserCommand.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                ioUtil.write(COMMAND_NOT_FOUND_MESSAGE);
                continue;
            }

            T t = null;
            if (UserCommand.isNeedObject()) {
                t = ioUtil.readObject();
            }

            String params = "";
            if (args.length > 1) {
                params = args[1];
            }

            request = new UserRequest<>(params, UserCommand.getName(), t, user);
        }
        return request;
    }

    public boolean checkBeforeExit() {
        return ioUtil.readAnswer(EXIT_MESSAGE);
    }
}
