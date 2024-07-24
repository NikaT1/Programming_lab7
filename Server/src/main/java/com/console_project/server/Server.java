package com.console_project.server;


import com.console_project.server.command.CommandInvoker;
import com.console_project.server.configuration.CommandConfiguration;
import com.console_project.server.configuration.ServiceConfiguration;
import com.console_project.server.connection.ClientHandler;
import com.console_project.server.connection.TCPClientHandler;
import com.console_project.server.service.DataService;
import com.console_project.server.service.UserService;
import com.console_project.shared.model.City;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Главный класс сервера.
 *
 * @author Troynikova Veronika
 */

public class Server {

    public static final Logger log = Logger.getLogger(Server.class.getName());
    private final int PORT = 8080;

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        try {
            CommandInvoker<City> commandInvoker = init();
            log.log(Level.INFO, "Настройка приема подключений от клиентов");
            ClientHandler<City> clientHandler = new TCPClientHandler<>(PORT, commandInvoker);
            clientHandler.handleClients();
        } catch (SocketException | SocketTimeoutException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Возникли проблемы с соединением с БД " + e.getMessage());
            serverExit(1);
        }
        serverExit(0);
    }

    private CommandInvoker<City> init() throws SQLException {
        log.log(Level.INFO, "Инициализация команд и коллекции");
        DataService<City> dataService = ServiceConfiguration.getDataService();
        UserService userService = ServiceConfiguration.getUserService();

        return CommandConfiguration.getCommandsInvoker(dataService,
                userService);
    }

    private void serverExit(int status) {
        log.log(Level.INFO, "Сервер завершает работу");
        System.exit(status);
    }

}
