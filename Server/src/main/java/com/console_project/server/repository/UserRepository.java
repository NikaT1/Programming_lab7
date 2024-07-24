package com.console_project.server.repository;

import com.console_project.shared.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import static com.console_project.server.Server.log;
import static com.console_project.server.configuration.DBConfiguration.USER_TABLE_INSERT;
import static com.console_project.server.configuration.DBConfiguration.USER_TABLE_SELECT;

public interface UserRepository {

    void init() throws SQLException;

    boolean find(User user);

    boolean add(User user);
}
