package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/monami?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                if (conn == connection) {
                    connection = null;
                }
            } catch (SQLException e) {
                // Log or handle if needed
            }
        }
    }

    public static Connection getCurrentConnection() {
        return connection;
    }
}
