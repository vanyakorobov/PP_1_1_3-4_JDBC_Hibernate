package jm.task.core.jdbc.dao;

import java.sql.*;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = (Connection) Util.getConnect();
        String sql = "CREATE TABLE IF NOT EXIST" + "UsersTable" + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50)," +
                "last_name VARCHAR(255)," + "age INT" + ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Connection connection = (Connection) Util.getConnect();
        String sql = "DROP TABLE IF EXISTS UsersTable";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.closeConnect(connection);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = (Connection) Util.getConnect();
        String sql = "INSERT INTO UsersTable (name, last_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь успешно сохранен");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.closeConnect(connection);
        }
    }


    public void removeUserById(long id) {
        Connection connection = (Connection) Util.getConnect();
        String sql = "DELETE FROM UsersTable WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь успешно удален");
            } else {
                System.out.println("Пользователь с id " + id + " не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.closeConnect(connection);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection connection = (Connection) Util.getConnect();
        String sql = "SELECT id, name, last_name, age FROM UsersTable";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.closeConnect(connection);
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = (Connection) Util.getConnect();
        String sql = "TRUNCATE TABLE UsersTable";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица успешно очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.closeConnect(connection);
        }
    }
}
