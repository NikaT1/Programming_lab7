package com.console_project.server.connection;

import com.console_project.server.command.CommandInvoker;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.serialization.ObjectSerializer;
import com.console_project.shared.serialization.ObjectSerializerImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import static com.console_project.server.Server.log;

@RequiredArgsConstructor
public class RequestHandler<T> implements Runnable {

    private final int MAX_BUFFER_SIZE = 100000;
    private final String CLIENT_CONNECTION_ERROR_MESSAGE = "Возникла проблема соединения с клиентом ";

    private final ObjectSerializer<UserRequest<T>> requestSerializer = new ObjectSerializerImpl<>();
    private final ObjectSerializer<CommandResponse> responseSerializer = new ObjectSerializerImpl<>();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private final SocketChannel socketChannel;
    private final CommandInvoker<T> commandInvoker;

    @Override
    public void run() {
        try {
            while (socketChannel.isOpen()) {
                log.log(Level.INFO, "Чтение команды");
                ByteBuffer buffer = ByteBuffer.wrap(new byte[MAX_BUFFER_SIZE]);
                socketChannel.read(buffer);
                UserRequest<T> request = requestSerializer.deserializeObject(buffer.array());
                Thread requestTask = new Thread(() -> processRequest(request));
                requestTask.join();
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, CLIENT_CONNECTION_ERROR_MESSAGE + e.getMessage());
            closeClientConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void processRequest(UserRequest<T> request) {

        CommandResponse commandResponse = commandInvoker.processRequest(request);
        threadPool.submit(() -> {
            try {
                socketChannel.write(ByteBuffer.wrap(responseSerializer.serializeObject(commandResponse)));
                log.log(Level.INFO, "Обработка команды " + request.command() + " завершена");
            } catch (IOException e) {
                log.log(Level.SEVERE, CLIENT_CONNECTION_ERROR_MESSAGE + e.getMessage());
                closeClientConnection();
            }
        }).get();

    }

    @SneakyThrows
    private void closeClientConnection() {
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
    }
}

