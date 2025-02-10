package com.example.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
	private DBConfig() {}
    private static final String URL = "jdbc:mysql://localhost:3306/OnlineShopping";
    private static final String USER = "root";
    private static final String PASSWORD = "root@123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
