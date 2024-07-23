package com.console_project.server.repository;

import com.console_project.server.exception.InitializingSqlSchemaException;
import com.console_project.shared.model.*;
import lombok.Builder;
import lombok.SneakyThrows;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.console_project.server.Server.log;
import static com.console_project.server.configuration.DBConfiguration.*;

@Builder
public class CityDataRepository implements DataRepository<City> {
    private final String SEQUENCE_CREATION_ERROR_MESSAGE = "Невозможно создать последовательность для генерации id";
    private final String MAIN_TABLE_CREATION_ERROR_MESSAGE = "Невозможно создать схему таблицы City";
    private final String STATEMENT_ERROR_MESSAGE = "Невозможно выполнить запрос";
    private String URL;
    private String USER;
    private String PASS;
    private Connection connection;

    public CityDataRepository() throws SQLException {
        init();
    }

    private void init() throws SQLException {
        //Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(URL, USER, PASS);
        createSequence();
        createCitiesTable();
    }

    private void createSequence() throws InitializingSqlSchemaException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(ID_SEQUENCE_CREATION);
        } catch (SQLException e) {
            throw new InitializingSqlSchemaException(SEQUENCE_CREATION_ERROR_MESSAGE);
        }
    }

    private void createCitiesTable() throws InitializingSqlSchemaException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(MAIN_TABLE_CREATION);
        } catch (SQLException e) {
            throw new InitializingSqlSchemaException(MAIN_TABLE_CREATION_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean add(City city) {
        try (PreparedStatement statement = connection.prepareStatement(MAIN_TABLE_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {
            fillStatementWithObject(city, statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.log(Level.SEVERE, STATEMENT_ERROR_MESSAGE);
        }
        return false;
    }

    @SneakyThrows
    @Override
    public List<City> getAll() {
        List<City> collection = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM city");
            while (resultSet.next()) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                City city = City.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .coordinates(new Coordinates(resultSet.getFloat("x"), resultSet.getInt("y")))
                        .creationDate(Instant.ofEpochMilli(formatter.parse(resultSet.getString("creationDate")).getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
                        .area(resultSet.getInt("area"))
                        .population(resultSet.getLong("population"))
                        .metersAboveSeaLevel((resultSet.getString("metersAboveSeaLevel") != null) ? resultSet.getLong("metersAboveSeaLevel") : null)
                        .establishmentDate((resultSet.getString("establishmentDate") != null) ? formatter.parse(resultSet.getString("establishmentDate")) : null)
                        .agglomeration((resultSet.getString("agglomeration") != null) ? resultSet.getInt("agglomeration") : null)
                        .climate(Climate.valueOf(resultSet.getString("climate")))
                        .governor((resultSet.getString("age") != null) ? new Human(resultSet.getInt("age")) : new Human(resultSet.getInt(null)))
                        .owner(resultSet.getString("owner"))
                        .build();
                collection.add(city);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, STATEMENT_ERROR_MESSAGE);
        }
        return collection;
    }

    @Override
    public boolean clear(String user) {
        try (PreparedStatement statement = connection.prepareStatement(MAIN_TABLE_DELETE_ALL)) {
            statement.setString(1, user);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.log(Level.SEVERE, STATEMENT_ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean remove(City city) {
        try (PreparedStatement statement = connection.prepareStatement(MAIN_TABLE_DELETE)) {
            statement.setInt(1, city.getId());
            statement.setString(2, city.getOwner());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.log(Level.SEVERE, STATEMENT_ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean update(int id, City city) {
        try (PreparedStatement statement = connection.prepareStatement(MAIN_TABLE_UPDATE)) {
            fillStatementWithObject(city, statement);
            statement.setInt(12, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.log(Level.SEVERE, STATEMENT_ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public int find(City city) {
        return 0; //todo
    }

    private void fillStatementWithObject(City city, PreparedStatement statement) throws SQLException {
        statement.setString(1, city.getName());
        statement.setFloat(2, city.getCoordinates().getX());
        statement.setInt(3, city.getCoordinates().getY());
        statement.setString(4, city.getCreationDate().toString());
        statement.setInt(5, city.getArea());
        statement.setLong(6, city.getPopulation());
        if (city.getMetersAboveSeaLevel() != null) statement.setLong(7, city.getMetersAboveSeaLevel());
        else statement.setNull(7, Types.BIGINT);
        if (city.getEstablishmentDate() != null) statement.setString(8, city.getEstablishmentDate());
        else statement.setNull(8, Types.VARCHAR);
        if (city.getAgglomeration() != null) statement.setInt(9, city.getAgglomeration());
        else statement.setNull(9, Types.INTEGER);
        statement.setString(10, city.getClimate().toString());
        if (city.getGovernor().getAge() != null) statement.setInt(11, city.getGovernor().getAge());
        else statement.setNull(11, Types.INTEGER);
        statement.setString(13, city.getOwner());
    }
}
