package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/kata_1_1_3_db";
    private static final String ROOT_NAME = "root";
    private static final String ROOT_PASSWORD = "djnaTWPI";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,ROOT_NAME,ROOT_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
