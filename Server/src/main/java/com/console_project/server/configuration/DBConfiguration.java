package com.console_project.server.configuration;

public class DBConfiguration {
    public final static String MAIN_TABLE_CREATION = "CREATE TABLE IF NOT EXISTS city (\n" +
            "id INTEGER PRIMARY KEY CHECK(id > 0) DEFAULT nextval('idgeneration'),\n" +
            "name VARCHAR(255) NOT NULL,\n" +
            "x REAL NOT NULL CHECK(x > -742),\n" +
            "y INTEGER NOT NULL CHECK(y > -989),\n" +
            "creationDate VARCHAR(255) NOT NULL,\n" +
            "area INTEGER NOT NULL CHECK(area > 0),\n" +
            "population BIGINT NOT NULL CHECK(population > 0),\n" +
            "metersAboveSeaLevel BIGINT,\n" +
            "establishmentDate VARCHAR(255),\n" +
            "agglomeration INTEGER,\n" +
            "climate VARCHAR(255),\n" +
            "age INTEGER CHECK(age > 0),\n" +
            "owner VARCHAR(255));";
    public final static String ID_SEQUENCE_CREATION = "CREATE SEQUENCE IF NOT EXISTS idgeneration START 1;";
    public final static String USER_TABLE_CREATION = "CREATE TABLE IF NOT EXISTS users (password VARCHAR(255), " +
            "login VARCHAR(255)PRIMARY KEY);";
    public final static String MAIN_TABLE_UPDATE = "UPDATE city SET name = ?, x = ?," +
            "y = ?, creationDate = ?, area = ?, population = ?, metersAboveSeaLevel = ?," +
            "establishmentDate = ?, agglomeration = ?, climate = ?, age = ? WHERE id = ? AND owner = ?";
    public final static String MAIN_TABLE_DELETE = "DELETE FROM city WHERE id = ? AND owner = ?";
    public final static String MAIN_TABLE_DELETE_ALL = "DELETE FROM city WHERE owner = ?";
    public final static String MAIN_TABLE_INSERT = "INSERT INTO city (name, x, y, creationdate," +
            " area, population, metersabovesealevel, establishmentdate, agglomeration, climate, age, owner)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    public final static String USER_TABLE_INSERT = "INSERT INTO users VALUES (?,?);";
    public final static String USER_TABLE_SELECT = "SELECT password FROM users WHERE login = ?";
}
