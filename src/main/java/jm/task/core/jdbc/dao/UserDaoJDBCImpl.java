package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    /**
     * Метод для создания таблицы users
     */
    public void createUsersTable() {
        String sql = "CREATE TABLE users ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(225) NOT NULL, "
                + "lastname varchar(225) NOT NULL, "
                + "age TINYINT UNSIGNED NOT NULL) ";
        String checkTable = "SHOW TABLES LIKE 'users'";

        try (Connection connection = Util.getConnection();
             Statement createTableStatement = connection.createStatement();
             ResultSet resultSet = createTableStatement.executeQuery(checkTable)){
            if (!resultSet.next()) {
                createTableStatement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для удаления таблицы users
     */
    public void dropUsersTable() {
        String sql = "DROP TABLE users";
        String checkTable = "SHOW TABLES LIKE 'users'";


        try (Connection connection = Util.getConnection();
             Statement dropTableStatement = connection.createStatement();
             ResultSet resultSet = dropTableStatement.executeQuery(checkTable)){
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
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement saveUserStatement = connection.prepareStatement(sql)){
            saveUserStatement.setString(1, name);
            saveUserStatement.setString(2, lastName);
            saveUserStatement.setByte(3, age);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет пользователя по его id - уникальному номеру
     * @param id - номер пользователя
     */

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id (?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement removeUserStatement = connection.prepareStatement(sql)){
            removeUserStatement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Формирует и возвращает список всех пользователей из таблицы users в виде List-a
     * @return List параметризованный классом User
     */
    public List<User> getAllUsers() {
        List<User> resultListIUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             Statement getAllUsersStatement = connection.createStatement();
             ResultSet setFromUsers = getAllUsersStatement.executeQuery(sql)){
            while (setFromUsers.next()) {
                User addUser = new User(setFromUsers.getString("name"),
                        setFromUsers.getString("lastname"),
                        setFromUsers.getByte("age"));
                addUser.setId(setFromUsers.getLong("id"));
                resultListIUsers.add(addUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultListIUsers;
    }

    /**
     * Очищает таблицу users от значений.
     */

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection();
             Statement clearUsersStatement = connection.createStatement()) {
            clearUsersStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
