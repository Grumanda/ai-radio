package de.gozilalp;

import java.net.URL;
import java.sql.*;
import java.util.logging.Logger;

public class DatabaseConnector {

    private Logger logger = Logger.getLogger("DatabaseConnector");

    private static DatabaseConnector instance;
    private Connection connection;
    private String jdbcURL = "jdbc:h2./data";
    private String user = "my-radio";
    private String pwd = "java.lang.NullPointerException";

    private DatabaseConnector() {
        try {
            connection = DriverManager.getConnection(jdbcURL, user, pwd);
            logger.info("Connected to database!");

            // Create Tables if not exists
            Statement statement = connection.createStatement();
            statement.execute(SQLCommands.CREATE_SCHEDULE_TABLE);
            statement.execute(SQLCommands.CREATE_SETTINGS_TABLE);

            // Test if data is already in database
            // settings data
            ResultSet resultSet = statement.executeQuery(SQLCommands.SELECT_SETTINGS_DATA);
            if (!resultSet.next()) {
                // Insert data
                PreparedStatement prepStatement = connection.prepareStatement(
                        SQLCommands.CREATE_SETTINGS_TABLE);
                prepStatement.executeUpdate();
            }
            // schedule data
            resultSet = statement.executeQuery(SQLCommands.SELECT_SCHEDULE_DATA);
            if (!resultSet.next()) {
                for (int i = 1; i <= 24; i++) {
                    PreparedStatement prepStatement = connection.prepareStatement(
                            SQLCommands.INSERT_SCHEDULE_DATA);
                    prepStatement.setInt(1, i);
                    prepStatement.setInt(2, i);
                    prepStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            logger.severe("Failed to connect to database");
            logger.severe(e.getLocalizedMessage());
        }
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.severe(e.getLocalizedMessage());
        }
    }
}
