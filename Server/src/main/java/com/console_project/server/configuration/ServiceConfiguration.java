package com.console_project.server.configuration;

import com.console_project.server.repository.CityDataRepository;
import com.console_project.server.repository.DataRepository;
import com.console_project.server.repository.UserRepository;
import com.console_project.server.repository.UserRepositoryImpl;
import com.console_project.server.service.DataService;
import com.console_project.server.service.InDBDataService;
import com.console_project.server.service.UserService;
import com.console_project.shared.model.City;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ServiceConfiguration {

    private static final Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream in = new FileInputStream("Server/src/main/resources/connection.properties")) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Требуется корректный файл конфигурации подключения", e);
        }
    }

    public static UserService getUserService() throws SQLException {
        UserRepository userRepository = UserRepositoryImpl.builder()
                .URL(properties.getProperty("db.url"))
                .USER(properties.getProperty("db.user"))
                .PASS(properties.getProperty("db.password"))
                .build();
        userRepository.init();
        return new UserService(userRepository);
    }

    public static DataService<City> getDataService() throws SQLException {
        DataRepository<City> dataRepository = CityDataRepository.builder()
                .URL(properties.getProperty("db.url"))
                .USER(properties.getProperty("db.user"))
                .PASS(properties.getProperty("db.password"))
                .build();
        dataRepository.init();
        return new InDBDataService(dataRepository);
    }
}
