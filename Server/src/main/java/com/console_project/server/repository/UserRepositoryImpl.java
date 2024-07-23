package com.console_project.server.repository;

import com.console_project.server.exception.InitializingSqlSchemaException;
import com.console_project.shared.model.*;
import lombok.Builder;

import java.sql.*;
import java.util.logging.Level;

import static com.console_project.server.Server.log;
import static com.console_project.server.configuration.DBConfiguration.*;

@Builder
public class UserRepositoryImpl implements UserRepository {
    private final String USER_TABLE_CREATION_ERROR_MESSAGE = "Невозможно создать схему таблицы User";
    private final String ADD_USER_ERROR_MESSAGE = "Ошибка при попытке добавления пользователя";
    private final String STATEMENT_ERROR_MESSAGE = "Невозможно выполнить запрос";
    private String URL;
    private String USER;
    private String PASS;
    private Connection connection;

    public UserRepositoryImpl(String[] args) throws SQLException {
        init();
    }

    private void init() throws SQLException {
        //Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(URL, USER, PASS);
        createUsersTable();
    }

    private void createUsersTable() throws InitializingSqlSchemaException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(USER_TABLE_CREATION);
        } catch (SQLException e) {
            throw new InitializingSqlSchemaException(USER_TABLE_CREATION_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean find(User user) {
        try (PreparedStatement statement = connection.prepareStatement(USER_TABLE_SELECT)) {
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1).equals(user.getPassword());
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, STATEMENT_ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean add(User user) {
        try (PreparedStatement statement = connection.prepareStatement(USER_TABLE_INSERT)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getUsername());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            log.log(Level.SEVERE, ADD_USER_ERROR_MESSAGE);
        }
        return false;
    }

}
