package jm.task.core.jdbc.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private final Statement statement = Util.getStatement();
    private Connection connection;

    public UserDaoJDBCImpl() throws SQLException {
        this.connection = this.statement.getConnection();
    }

    public void createUsersTable() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS User ( id BIGINT AUTO_INCREMENT PRIMARY KEY,  name VARCHAR(50),  lastName VARCHAR (50),  age INTEGER not NULL)";

        try {
            this.connection.setAutoCommit(false);
            this.connection.prepareStatement(sqlCreate).execute();
            this.connection.commit();
        } catch (Exception var6) {
            this.connection.rollback();
            var6.printStackTrace();
        } finally {
            this.connection.setAutoCommit(true);
        }

    }

    public void dropUsersTable() throws SQLException {
        String sql = "drop table IF EXISTS USER ";
        this.connection.setAutoCommit(false);

        try {
            this.connection.prepareStatement(sql).execute();
            this.connection.commit();
        } catch (Exception var6) {
            this.connection.rollback();
            var6.printStackTrace();
        } finally {
            this.connection.setAutoCommit(true);
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        this.connection.setAutoCommit(false);

        try {
            PreparedStatement prst = this.connection.prepareStatement("INSERT INTO User VALUES (id,?,?,?)");
            prst.setString(1, name);
            prst.setString(2, lastName);
            prst.setInt(3, age);
            prst.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            this.connection.commit();
        } catch (Exception var8) {
            this.connection.rollback();
            var8.printStackTrace();
        } finally {
            this.connection.setAutoCommit(true);
        }

    }

    public void removeUserById(long id) throws SQLException {
        this.connection.setAutoCommit(false);

        try {
            PreparedStatement prst = this.connection.prepareStatement("DELETE FROM User WHERE id= (?)");
            prst.setLong(1, id);
            prst.executeUpdate();
            this.connection.commit();
        } catch (Exception var7) {
            this.connection.rollback();
            var7.printStackTrace();
        } finally {
            this.connection.setAutoCommit(true);
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList();
        this.connection.setAutoCommit(false);

        try {
            ResultSet resultSet = this.connection.prepareStatement("SELECT * from User").executeQuery();
            this.connection.commit();

            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"), (byte)resultSet.getInt("age"));
                userList.add(user);
            }
        } catch (Exception var7) {
            this.connection.rollback();
            var7.printStackTrace();
        } finally {
            this.connection.setAutoCommit(true);
        }

        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        this.connection.setAutoCommit(false);

        try {
            this.connection.prepareStatement("TRUNCATE table User").execute();
            this.connection.commit();
        } catch (Exception var5) {
            this.connection.rollback();
            var5.printStackTrace();
        } finally {
            this.connection.setAutoCommit(true);
        }

    }
}