package com.console_project.shared.connection_model;

import java.io.Serializable;

public record CommandResponse(
        String status,
        String message
) implements Serializable {
}
