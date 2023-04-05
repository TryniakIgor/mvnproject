package JDBC.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    private static final String MYSQL_DRIVE = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "dbadmin";
    private static final String PASSWORD = "belero111";
    private static final String DATABASE = "product";
    private static final String URL = "jdbc:mysql://database-1.c7d7kfy7ynjl.us-east-1.rds.amazonaws.com/" + DATABASE + "?serverTimeZone=UTC";

    public static Connection getConnection() {
        try {
            Class.forName(MYSQL_DRIVE);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
