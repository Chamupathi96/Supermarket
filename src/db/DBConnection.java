package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
// Static instance for singleton
    private static DBConnection instance;
    
    // Database connection object
    private Connection connection;

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/SINGHESUPERMARKET";
    private static final String USER = "root";
    private static final String PASSWORD = "Janith@123";

    // Private constructor to restrict instantiation from other classes
    private DBConnection() throws SQLException {
        try {
            // Initialize the connection object
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to create database connection.", e);
        }
    }

    // Public method to provide access to the singleton instance
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    // Method to get the connection object
    public Connection getConnection() {
        return connection;
    }

}
