package com.console_project.client.connection;

import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;

import java.io.IOException;

public interface ConnectionHandler<T> {
    void reconnection() throws IOException;

    CommandResponse sendCommand(UserRequest<T> userRequest) throws IOException;

    void close();
}
