package com.console_project.server.exception;

import java.sql.SQLException;

public class InitializingSqlSchemaException extends SQLException {
    public InitializingSqlSchemaException(String message){
        super(message);
    }
}
