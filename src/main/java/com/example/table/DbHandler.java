package com.example.table;
import com.example.table.model.Person;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandler {

    private static final String CON_STR = "jdbc:sqlite:D:/SQLite/persons";

    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<Person> getAllPersons() {

        try (Statement statement = this.connection.createStatement()) {
            List<Person> persons = new ArrayList<Person>();

            ResultSet resultSet = statement.executeQuery("SELECT id, userName, email, active FROM persons");
            while (resultSet.next()) {
                persons.add(new Person(resultSet.getInt("id"),
                        resultSet.getString("userName"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("active")));
            }
            return persons;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addPerson(Person newPerson, Person oldPperson) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE persons SET userName = ? WHERE id = ?")) {
            statement.setObject(1, newPerson.userName);
            statement.setObject(2, oldPperson.id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE persons SET active = ? WHERE id = ?")) {
            statement.setObject(1, newPerson.active);
            statement.setObject(2, oldPperson.id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
