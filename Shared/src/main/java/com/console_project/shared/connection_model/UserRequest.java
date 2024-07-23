package com.console_project.shared.connection_model;

import com.console_project.shared.model.User;

public record UserRequest<T>(
        String params,
        String command,
        T object,
        User user
) {
}
