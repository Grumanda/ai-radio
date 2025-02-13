package de.gozilalp;

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseConnector {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DatabaseConnector.class);
    private static Logger logger = Logger.getLogger("DatabaseConnector");

    private static DatabaseConnector instance;
    private Connection connection;
    private String jdbcURL = pathBuilder();
    private String username = "root";
    private String pwd = "NullPointerException";

    private DatabaseConnector() {
        try {
            logger.info("Connecting to following database URL: " + jdbcURL);
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(jdbcURL, username, pwd);
            logger.info("Connected to database!");
        } catch (SQLException e) {
            logger.severe("Failed to connect to database!");
            logger.severe(e.getLocalizedMessage());
            System.exit(0);
        } catch (ClassNotFoundException e) {
            logger.severe("Failed to load JDBC driver!");
            logger.severe(e.getLocalizedMessage());
            System.exit(0);
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

    public void setupSchedule() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM SCHEDULE";
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                int hour = set.getInt(1);
                boolean isActivated = set.getBoolean(2);
                Schedule schedule = new Schedule(hour, isActivated);
                Main.scheduleList.add(schedule);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveNewSchedule(List<Schedule> scheduleList) {
        try {
            for (Schedule schedule : scheduleList) {
                Statement statement = connection.createStatement();
                String sql = "UPDATE SCHEDULE SET isActivated = " + schedule.isActivated()
                        + " WHERE schedulehour = " + schedule.getHour();
                statement.execute(sql);
                statement.close();
            }
        } catch (SQLException e) {
            logger.severe(e.getLocalizedMessage());
        }
    }

    public void saveCity(String city) {
        try {
            String sql = "UPDATE settings SET city = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, city);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            logger.severe(e.getLocalizedMessage());
        }
    }

    public void changeDesign(String design) {
        String sql = "UPDATE settings SET ";
    }

    private static String pathBuilder() {
        String jdbcUrl = "jdbc:hsqldb:file:";
        String url = String.valueOf(DatabaseConnector.class.getResource("/database"));
        String rightURL = "";
        for (int i = 6; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                rightURL = rightURL + "\\";
            } else {
                rightURL = rightURL + url.charAt(i);
            }
        }
        jdbcUrl = jdbcUrl + rightURL + "\\database" + "; shutdown=true";
        return jdbcUrl;
    }
}
