package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Configuration;

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
            connection = DriverManager.getConnection(URL, ROOT_NAME, ROOT_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/kata_1_1_3_db");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "djnaTWPI");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.hbm2ddl.auto", "none");
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }

}
