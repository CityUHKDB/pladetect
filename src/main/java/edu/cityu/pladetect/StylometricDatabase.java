package edu.cityu.pladetect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StylometricDatabase {
    // JDBC driver name and connection url
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/stylometry";

    // database login information
    private static final String username = "dickson";
    private static final String password = "stylometry";

    private Connection con;
    private Statement stmt;

    // use singleton design pattern for database connection
    private static final StylometricDatabase instance = new StylometricDatabase();

    public static StylometricDatabase getInstance() {
        return instance;
    }

    private void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL, username, password);
        stmt = con.createStatement();
    }

    private void closeConnection() throws SQLException
    {
        stmt.close();
        con.close();
    }

    public void runInsertQueries(String queries) throws Exception
    {
        openConnection();
        stmt.executeUpdate(queries);
        closeConnection();
    }
}
