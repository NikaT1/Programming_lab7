package com.console_project.server.connection;

import java.net.SocketException;
import java.net.SocketTimeoutException;

public interface ClientHandler<T> {
    void handleClients() throws SocketException, SocketTimeoutException;
}
