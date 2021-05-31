package server;

import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.Serialization;
import sharedClasses.User;
import sharedClasses.WrapperForObjects;
import sharedClasses.commands.Command;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private String[] args;
    private final Serialization serialization;
    private PriorityQueueStorage priorityQueue;
    private final IOForClient ioForClient;
    private DatagramSocket datagramSocket;
    private DataBaseControl dataBaseControl;
    public static final Logger log = Logger.getLogger(Server.class.getName());
    private final int port = 1200;
    private final ExecutorService newFixedThreadPool;

    public Server() {
        log.log(Level.INFO, "Запуск сервера");
        serialization = new Serialization();
        ioForClient = new IOForClient(true);
        newFixedThreadPool = Executors.newFixedThreadPool(10);
    }

    public static void main(String[] args) {
        Server server = new Server();
        if (args.length == 5) {
            server.args = args;
            server.run();
        } else
            log.log(Level.SEVERE, "Неверный формат ввода аргументов: <host>, <port>, <database>, <user>, <password>");
    }

    public void run() {
        try {
            dataBaseControl = new DataBaseControl(args);
            log.log(Level.INFO, "Заполнение коллекции");
            priorityQueue = new PriorityQueueStorage(dataBaseControl);
            dataBaseControl.takeAllFromDB(priorityQueue);
            log.log(Level.INFO, "Коллекция успешно заполнена");
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE, "Значения полей объектов введены неверно");
            e.printStackTrace();
            System.exit(-1);
        } catch (NullPointerException e) {
            log.log(Level.SEVERE, "Значения полей объектов введены неверно");
            System.exit(-1);
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Дата в базе данных введена в неправильном формате");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "Не найден PostgreSQL JDBC Driver");
            System.exit(-1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при подключении к базе данных");
            System.exit(-1);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ошибка при чтении из базы данных");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            new InetSocketAddress("localhost", port);
            datagramSocket = new DatagramSocket(port);
            ioForClient.setDatagramSocket(datagramSocket);
            while (true) {
                this.execute(newFixedThreadPool.submit(this::readClientRequest).get());
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

    public void processUser(WrapperForObjects object) {
        try {
            User user = (User) object.getObject();
            byte[] result;
            if (object.getDescription().equals("oldUser")) {
                result = Serialization.serializeData(dataBaseControl.checkUser(user));
            } else result = Serialization.serializeData(dataBaseControl.addUser(user));
            log.log(Level.INFO, "Отправка клиенту результата проверки/добавления");
            boolean flag = Executors.newCachedThreadPool().submit(() -> ioForClient.output(result)).get();
            if (flag) Server.log.log(Level.INFO, "Отправка результата пользователю успешно завершена");
            else Server.log.log(Level.SEVERE, "Возникла ошибка при попытке отправки результата пользователю.");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Возникла ошибка при попытке соединения с БД");
        } catch (InterruptedException | ExecutionException e) {
            log.log(Level.SEVERE, "Возникла ошибка в побочном потоке");
            e.printStackTrace();
        }
    }

    public WrapperForObjects readClientRequest() {
        try {
            datagramSocket.setSoTimeout(100000);
            byte[] bytes = new byte[100000];
            log.log(Level.INFO, "Чтение сообщения от пользователя");
            bytes = ioForClient.input(bytes);
            return (WrapperForObjects) serialization.deserializeData(bytes);
        } catch (SocketTimeoutException e) {
            log.log(Level.SEVERE, "Время ожидания истекло");
            System.exit(1);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Возникла ошибка при попытке считывания запроса пользователя");
        }
        return null;
    }

    public void execute(WrapperForObjects object) {
        if (object.getDescription().equals("Command")) {
            Command command = (Command) object.getObject();
            log.log(Level.INFO, "Получение результата работы команды и отправка клиенту результата работы команды");
            CommandExecutor commandExecutor = new CommandExecutor(command, ioForClient, priorityQueue);
            new Thread(commandExecutor).start();
        } else {
            log.log(Level.INFO, "Получение результата проверки пользователя/добавления пользователя");
            new Thread(() -> this.processUser(object)).start();
        }
    }
}
