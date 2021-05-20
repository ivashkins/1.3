package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Statement statement= Util.getStatement();
    Connection connection;

    {
        try {
            assert statement != null;
            connection = statement.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                    " id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " age INTEGER not NULL)");

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("drop table IF EXISTS USER ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement prst=connection.prepareStatement("INSERT INTO User VALUES (id,?,?,?)");
            prst.setString(1, name);
            prst.setString(2,lastName);
            prst.setInt(3,age);
            prst.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement prst=connection.prepareStatement("DELETE FROM User WHERE id= (?)");
            prst.setLong(1,id);
            prst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User>userList=new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * from User");
            while (resultSet.next()){
                User user=new User(resultSet.getString("name"),resultSet.getString("lastname"), (byte) resultSet.getInt("age"));
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            statement.execute("TRUNCATE table User");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
