package server;

import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;

public class DataBaseControl {
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASS = "Iwanttocreate";
    private Connection connection;
    private Statement stat;

    public void setConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public void createUsersTable() throws SQLException {
        stat = connection.createStatement();
        String creation = "CREATE TABLE IF NOT EXISTS users (\n" +
                "password VARCHAR(255), login VARCHAR(255)PRIMARY KEY);";
        stat.executeUpdate(creation);
        stat.close();
    }

    public City addToDataBase(City city) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO city (name, x, y, creationdate," +
                " area, population, metersabovesealevel, establishmentdate, agglomeration, climate, age)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?);");
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
        int count = statement.executeUpdate();
        if (count > 0) {
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                city.setId(resultSet.getInt("id"));
                System.out.println(city.getId());
            }
        }
        statement.close();
        return city;
    }

    public void createSequence() throws SQLException {
        stat = connection.createStatement();
        String creation = "CREATE SEQUENCE IF NOT EXISTS idgeneration START 1 AS integer ;";
        stat.executeUpdate(creation);
        stat.close();
    }

    public void createTableCities() throws SQLException {
        stat = connection.createStatement();
        String creation = "CREATE TABLE IF NOT EXISTS city (\n" +
                "id INT PRIMARY KEY CHECK(id > 0) DEFAULT nextval('idgeneration'),\n" +
                "name VARCHAR(255) NOT NULL,\n" +
                "x REAL NOT NULL,\n" +
                "y INT NOT NULL,\n" +
                "creationDate VARCHAR(255) NOT NULL,\n" +
                "area INT NOT NULL,\n" +
                "population BIGINT NOT NULL,\n" +
                "metersAboveSeaLevel BIGINT,\n" +
                "establishmentDate VARCHAR(255),\n" +
                "agglomeration INT,\n" +
                "climate VARCHAR(255),\n" +
                "age INT\n" +
                ");";
        stat.executeUpdate(creation);
        stat.close();
    }

    public void takeAllFromDB(PriorityQueueStorage priorityQueue) throws SQLException, ClassNotFoundException, ParseException {
        Class.forName("org.postgresql.Driver");
        setConnection();
        createSequence();
        createUsersTable();
        createTableCities();
        stat = connection.createStatement();
        ResultSet resultSet = stat.executeQuery("SELECT * FROM city");
        while (resultSet.next()) {
            City city = new City();
            city.setId(resultSet.getInt("id"));
            System.out.println(city.getId());
            if (!priorityQueue.getIdSet().add(city.getId())) throw new NumberFormatException();
            city.setName(resultSet.getString("name"));
            city.setCoordinates(new Coordinates(resultSet.getFloat("x"), resultSet.getInt("y")));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            city.setCreationDate(Instant.ofEpochMilli(formatter.parse(resultSet.getString("creationDate")).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            city.setArea(resultSet.getInt("area"));
            city.setPopulation(resultSet.getLong("population"));
            if (resultSet.getString("metersAboveSeaLevel") != null)
                city.setMetersAboveSeaLevel(resultSet.getLong("metersAboveSeaLevel"));
            else city.setMetersAboveSeaLevel(null);
            if (resultSet.getString("establishmentDate") != null) {
                city.setEstablishmentDate(formatter.parse(resultSet.getString("establishmentDate")));
            } else city.setEstablishmentDate(null);
            if (resultSet.getString("agglomeration") != null)
                city.setAgglomeration(resultSet.getInt("agglomeration"));
            else city.setAgglomeration(null);
            city.setClimate(Climate.valueOf(resultSet.getString("climate")));
            if (resultSet.getString("age") != null) {
                city.setGovernor(new Human(resultSet.getInt("age")));
            } else city.setGovernor(new Human(null));
            priorityQueue.checkElement(city);
            priorityQueue.getCollection().add(city);
        }
        stat.close();
    }

    public String checkUser(User user) throws SQLException {
        String result = "Пользователь не найден";
        PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE login = ?");
        statement.setString(1, user.getLogin());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) result = resultSet.getString(1);
        return result;
    }
}
