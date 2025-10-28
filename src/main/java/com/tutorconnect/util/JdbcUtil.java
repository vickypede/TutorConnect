package com.tutorconnect.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtil {
    // EDIT to your DB settings
    private static final String URL = "jdbc:mysql://localhost:3306/tutorconnect?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "rootpassword";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
