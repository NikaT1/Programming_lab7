package com.console_project.shared.connection_model;

import com.console_project.shared.model.User;

import java.io.Serializable;

public record UserRequest<T>(
        String params,
        String command,
        T object,
        User user
) implements Serializable {
}
