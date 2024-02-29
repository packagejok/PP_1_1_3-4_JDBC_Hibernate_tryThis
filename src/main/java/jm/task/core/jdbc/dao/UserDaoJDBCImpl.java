package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    //private static final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String sqlIfExists = "CREATE TABLE IF NOT EXISTS users ";
        final String sqlCreate = "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlIfExists + sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setInt(3, age);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        final String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT * FROM users";
        try (Connection connection = Util.getConnection(); ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        final String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
