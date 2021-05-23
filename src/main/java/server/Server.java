package server;

import server.collectionUtils.PriorityQueueStorage;
import server.commands.Command;
import server.commands.CommandsControl;
import server.commands.ExecuteScript;
import sharedClasses.Serialization;

import java.io.*;
import java.net.*;
import java.security.InvalidAlgorithmParameterException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private String[] args;
    private final CommandsControl commandsControl;
    private final Serialization serialization;
    private PriorityQueueStorage priorityQueue;
    private final IOForClient ioForClient;
    private DatagramSocket datagramSocket;
    private final Logger log = Logger.getLogger(Server.class.getName());
    private final int port = 1200;

    public Server() {
        log.log(Level.INFO, "Запуск сервера");
        commandsControl = new CommandsControl();
        serialization = new Serialization();
        ioForClient = new IOForClient(true);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.args = args;
        server.run();
    }

    public void run() {
        try {
            log.log(Level.INFO, "Заполнение коллекции");
            DataBaseControl dataBaseControl = new DataBaseControl();
            priorityQueue = new PriorityQueueStorage(dataBaseControl);
            dataBaseControl.takeAllFromDB(priorityQueue);
            log.log(Level.INFO, "Коллекция успешно заполнена");
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE, "Значения полей объектов введены неверно");
            e.printStackTrace();
            System.exit(-1);
        } catch (NullPointerException e) {
            log.log(Level.SEVERE, "Файл сожержит не все поля, необходимые для создания элементов коллекции");
            System.exit(-1);
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Дата в базе данных введена в неправильном формате");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "Не найден PostgreSQL JDBC Driver");
            System.exit(-1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при подключении к базе данных");
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ошибка при чтении из базы данных");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    save.doCommand(ioForClient, commandsControl, priorityQueue);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }));*/
            new InetSocketAddress("localhost", port);
            datagramSocket = new DatagramSocket(port);
            ioForClient.setDatagramSocket(datagramSocket);
            while (true) {
                this.execute();
            }
        } catch (PortUnreachableException e) {
            log.log(Level.SEVERE, "Ошибка с доступом к порту");
        } catch (SocketException e) {
            log.log(Level.SEVERE, "Ошибка подключения");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Возникла непредвиденная ошибка");
            e.printStackTrace();
        }
    }

    public void execute() throws Exception {
        try {
            datagramSocket.setSoTimeout(600000);
            byte[] bytes = new byte[100000];
            log.log(Level.INFO, "Чтение команды");
            bytes = ioForClient.input(bytes);
            Command command = (Command) serialization.deserializeData(bytes);
            log.log(Level.INFO, "Получение результата работы команды");
            byte[] commandResult = command.doCommand(ioForClient, commandsControl, priorityQueue);
            log.log(Level.INFO, "Отправка клиенту результата работы команды");
            ioForClient.output(commandResult);
        } catch (InvalidAlgorithmParameterException e) {
            ioForClient.output(e.getMessage());
        } catch (NoSuchElementException | NumberFormatException | ParseException e) {
            ExecuteScript command = (ExecuteScript) commandsControl.getCommands().get("execute_script");
            command.getPaths().clear();
            ioForClient.output("Неверный формат введенных данных");
        } catch (SocketTimeoutException e) {
            log.log(Level.SEVERE, "Время ожидания истекло");
            System.exit(1);
        }
    }
}
