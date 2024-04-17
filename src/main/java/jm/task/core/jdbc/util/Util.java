package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String dbURL = "jdbc:mysql://localhost:3306/my_db";
    private static String dbUsername = "root";
    private static String dbPassword = "root";
    private static Connection connection = null;

    public static Connection getConnect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnect(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
