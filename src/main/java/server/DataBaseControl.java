package server;

import server.collectionUtils.PriorityQueueStorage;
import sharedClasses.City;
import sharedClasses.Climate;
import sharedClasses.Coordinates;
import sharedClasses.Human;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Properties;

public class DataBaseControl {
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASS = "Iwanttocreate";
    private Connection connection;
    private Statement stat;

    public void setConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASS);
        stat = connection.createStatement();
    }

    /*public Properties getProperties(){
        Properties props = new Properties();
        props.put("user", USER);
        props.put("passwd", PASS);
        return props;
    }*/
    public void createUsersTable() {

    }

    public void addToDataBase(City city) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO city VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?);");
        statement.setString(1, city.getName());
        statement.setFloat(2,city.getCoordinates().getX());
        statement.setInt(3,city.getCoordinates().getY());
        statement.setString(4,city.getCreationDate().toString());
        statement.setInt(5,city.getArea());
        statement.setLong(6,city.getPopulation());
        statement.setLong(7,city.getMetersAboveSeaLevel());
        statement.setString(8,city.getEstablishmentDate());
        statement.setInt(9,city.getAgglomeration());
        statement.setString(10,city.getClimate().toString());
        statement.setInt(11,city.getGovernor().getAge());
        statement.executeUpdate();
    }

    public void createSequence() throws SQLException {
        String creation = "CREATE SEQUENCE IF NOT EXISTS idGeneration START 1 AS integer ;";
        stat.executeUpdate(creation);
    }

    public void createTableCities() throws SQLException {
        String creation = "CREATE TABLE IF NOT EXISTS city (\n" +
                "id INT PRIMARY KEY CHECK(id > 0) DEFAULT nextval('idGeneration'),\n" +
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

    }

    public void takeAllFromDB(PriorityQueueStorage priorityQueue) throws SQLException, ClassNotFoundException, ParseException {
        Class.forName("org.postgresql.Driver");
        setConnection();
        createSequence();
        createTableCities();
        ResultSet resultSet = stat.executeQuery("SELECT * FROM city");
        while (resultSet.next()) {
            City city = new City();
            city.setId(resultSet.getInt("id"));
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
            priorityQueue.addToCollection(city);
        }
    }
}
