package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    /**
     * Метод для создания таблицы users при помощи JDBC
     */
    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE users ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(225) NOT NULL, "
                + "lastname varchar(225) NOT NULL, "
                + "age TINYINT UNSIGNED NOT NULL) ";
        String checkTable = "SHOW TABLES LIKE 'users'";

        try (Connection connection = Util.getConnection();
             Statement createTableStatement = connection.createStatement();
             ResultSet resultSet = createTableStatement.executeQuery(checkTable)) {
            if (!resultSet.next()) {
                createTableStatement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для удаления таблицы users при помощи JDBC
     */
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        String checkTable = "SHOW TABLES LIKE 'users'";

        try (Connection connection = Util.getConnection();
             Statement dropTableStatement = connection.createStatement();
             ResultSet resultSet = dropTableStatement.executeQuery(checkTable)) {
            if (resultSet.next()) {
                dropTableStatement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для добавления новой строки в таблицу users
     * @param name Имя пользователя
     * @param lastName Фамилия пользователя
     * @param age Возраст пользователя, должен быть положительным
     */
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Удаляет пользователя по его id - уникальному номеру
     * @param id - номер пользователя
     */
    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Формирует и возвращает список всех пользователей из таблицы users в виде List-a
     * @return List параметризованный классом User
     */
    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Очищает таблицу users от значений.
     */
    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User ").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
