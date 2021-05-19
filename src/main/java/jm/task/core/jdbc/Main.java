package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.List;

public class Main {
    private static final String LOGIN="root";
    private static final String PASSWORD="2021";
    private static final String URL="jdbc:mysql://localhost:3306/firstdb?autoReconnect=true&useSSL=false";

    public static void main(String[] args) throws SQLException {


        Connection connection;
        DriverManager driverManager;


        connection=DriverManager.getConnection(URL,LOGIN,PASSWORD);
        Statement statement=connection.createStatement();

        UserServiceImpl user=new UserServiceImpl();
        user.createUsersTable();

        user.saveUser("I","D", (byte) 21);
        user.saveUser("I","DI", (byte) 22);
        user.saveUser("I","OT", (byte) 23);
        user.saveUser("I","))", (byte) 24);
        System.out.println("check");

        List<User> users=user.getAllUsers();


        users.forEach(System.out::println);

        user.cleanUsersTable();
        user.dropUsersTable();







    }
}
