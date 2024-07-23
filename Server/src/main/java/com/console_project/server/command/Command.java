package com.console_project.server.command;

import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;

public interface Command<T> {
    CommandResponse execute(UserRequest<T> request);

    String getName();
}
