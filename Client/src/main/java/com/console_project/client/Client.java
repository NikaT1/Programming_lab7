package com.console_project.client;


import com.console_project.client.account.UserAccountHandler;
import com.console_project.client.connection.ConnectionHandler;
import com.console_project.client.connection.TCPConnectionHandler;
import com.console_project.client.io_util.IOutil;
import com.console_project.client.io_util.IOutilCity;
import com.console_project.client.io_util.IOutilUser;
import com.console_project.client.io_util.UserInput;
import com.console_project.client.account.UserAccount;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.model.City;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Scanner;

/**
 * Главный класс клиента.
 *
 * @author Troynikova Veronika
 */

public class Client {

    private final String EXIT_COMMAND = "exit";
    private final String CONNECTION_ERROR_MESSAGE = "Проблемы с соединением, повторная попытка подключиться... ";
    private final String INIT_CONNECTION_ERROR_MESSAGE = "Невозможно установить соединение";
    private static final String INIT_CLIENT_ERROR_MESSAGE = "Требуется аргумент для запуска - " +
            "номер порта сервера для подключения";
    private final int RECONNECTION_SLEEP_TIME = 10000;
    private boolean isRunning = true;
    private ConnectionHandler<City> connectionHandler;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(INIT_CLIENT_ERROR_MESSAGE);
            return;
        }
        new Client().run(args[0]);
    }

    @SneakyThrows
    private void run(String port) {
        try {
            connectionHandler = new TCPConnectionHandler<>(Integer.parseInt(port));
        } catch (IOException e) {
            System.out.println(INIT_CONNECTION_ERROR_MESSAGE);
            return;
        }

        IOutil<City> ioUtil = new IOutilCity(new Scanner(System.in));
        UserAccount userAccount = new IOutilUser(new Scanner(System.in)).readObject();
        UserInput<City> userInput = new UserInput<>(ioUtil, userAccount.user());
        UserAccountHandler userAccountHandler = new UserAccountHandler(userAccount, connectionHandler);

        CommandResponse commandResponse = userAccountHandler.authorizeUser();
        if (commandResponse.status().equals("ERROR")) {
            System.out.println(commandResponse.message());
            stop();
        }

        while (isRunning) {
            try {
                UserRequest<City> request = userInput.getUserRequest();
                if (request.command().equals(EXIT_COMMAND) && userInput.checkBeforeExit()) {
                    isRunning = false;
                } else {
                    ioUtil.write(connectionHandler.sendCommand(request).message());
                }
            } catch (IOException e) {
                System.out.println(CONNECTION_ERROR_MESSAGE + e.getMessage());
                Thread.sleep(RECONNECTION_SLEEP_TIME);
                connectionHandler.reconnection();
            }
        }
    }

    private void stop() {
        isRunning = false;
        connectionHandler.close();
    }
}
