package com.console_project.server.connection;

import com.console_project.server.command.CommandInvoker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static com.console_project.server.Server.log;

@RequiredArgsConstructor
public class TCPClientHandler<T> implements ClientHandler<T> {

    private final int SOCKET_TIMEOUT = 600000;
    private final int MAX_CLIENTS_COUNT = 10;

    private final String CLIENT_CONNECTION_ERROR_MESSAGE = "Возникла проблема соединения с клиентом ";
    private final String CONNECTION_ERROR_MESSAGE = "Невозможно открыть соединение для клиентов ";
    private final String SELECTOR_ERROR_MESSAGE = "Возникла ошибка в работе селектора ";
    private final String TIMEOUT_ERROR_MESSAGE = "Время ожидания подключения клиентов истекло ";

    private final int port;
    private final CommandInvoker<T> commandInvoker;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CLIENTS_COUNT);

    private boolean isRunning = true;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public void handleClients() throws SocketException, SocketTimeoutException {
        initConnection();
        try {
            while (isRunning) {
                selector.select();
                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey skey = (SelectionKey) it.next();
                    it.remove();
                    if (skey.isAcceptable()) {
                        SocketChannel clientChannel = handleNewConnection();
                        threadPool.submit(new RequestHandler<T>(clientChannel, commandInvoker));
                    }
                }
            }
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (SocketTimeoutException e) {
            log.log(Level.SEVERE, TIMEOUT_ERROR_MESSAGE + e.getMessage());
            throw e;
        } catch (IOException e) {
            log.log(Level.SEVERE, SELECTOR_ERROR_MESSAGE + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll();
        }

    }

    private void initConnection() throws SocketException {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().setSoTimeout(SOCKET_TIMEOUT);
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            closeAll();
            log.log(Level.SEVERE, CONNECTION_ERROR_MESSAGE + e.getMessage());
            SocketException exc = new SocketException();
            exc.addSuppressed(e);
            throw exc;
        }
    }

    private SocketChannel handleNewConnection() {
        SocketChannel ch = null;
        try {
            ch = serverSocketChannel.accept();
            log.log(Level.INFO, "Новое соединение: " + ch.socket());
            ch.configureBlocking(true);
            return ch;
        } catch (IOException e) {
            log.log(Level.SEVERE, CLIENT_CONNECTION_ERROR_MESSAGE + e.getMessage());
            closeClientConnection(ch);
        }
        return null;
    }

    public void stop() {
        closeAll();
        isRunning = false;
    }

    @SneakyThrows
    private void closeClientConnection(SocketChannel sc) {
        if (sc != null && sc.isOpen()) {
            sc.close();
        }
    }

    @SneakyThrows
    private void closeAll() {
        if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
            serverSocketChannel.close();
        }
        if (selector != null && selector.isOpen()) {
            selector.close();
        }
    }

}
