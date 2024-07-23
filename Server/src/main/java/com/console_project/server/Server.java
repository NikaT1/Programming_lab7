package com.console_project.server;


import com.console_project.server.command.CommandInvoker;
import com.console_project.server.command.ExitHandler;
import com.console_project.server.configuration.CommandConfiguration;
import com.console_project.server.configuration.ServiceConfiguration;
import com.console_project.server.connection.ClientHandler;
import com.console_project.server.connection.TCPClientHandler;
import com.console_project.server.file_util.CSVFileCityReaderWriter;
import com.console_project.server.file_util.FileReaderWriter;
import com.console_project.server.service.DataService;
import com.console_project.server.service.UserService;
import com.console_project.shared.model.City;

import java.net.SocketException;
import java.net.SocketTimeoutException;
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
        new Server().run(args);
    }

    private void run(String[] args) {
        if (args.length == 0) {
            log.log(Level.SEVERE, "Требуется аргумент для запуска - путь к файлу");
            serverExit(1);
        }

        CommandInvoker<City> commandInvoker = init(args[0]);
        try {
            log.log(Level.SEVERE, "Настройка приема подключений от клиентов");
            ClientHandler<City> clientHandler = new TCPClientHandler<>(PORT, commandInvoker);
            clientHandler.handleClients();
        } catch (SocketException | SocketTimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            serverExit(1);
        }
    }

    private CommandInvoker<City> init(String filePath) {
        log.log(Level.INFO, "Инициализация команд и коллекции");
        FileReaderWriter<City> fileReaderWriter = new CSVFileCityReaderWriter(filePath);
        DataService<City> dataService = ServiceConfiguration.getDataService();
        UserService userService = ServiceConfiguration.getUserService();
        CommandInvoker<City> commandInvoker = CommandConfiguration.getCommandsInvoker(dataService,
                userService,
                fileReaderWriter);

        ExitHandler exitHandler = CommandConfiguration.getExitHandler(commandInvoker);
        Runtime.getRuntime().addShutdownHook(new Thread(exitHandler::commandBeforeExitExecute));

        return commandInvoker;
    }

    private void serverExit(int status) {
        log.log(Level.INFO, "Сервер завершает работу");
        System.exit(status);
    }

}
