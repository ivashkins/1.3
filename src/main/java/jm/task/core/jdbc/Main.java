package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;
import java.util.List;

public class Main {
    private static final String LOGIN="root";
    private static final String PASSWORD="2021";
    private static final String URL="jdbc:mysql://localhost:3306/firstdb?autoReconnect=true&useSSL=false";

    public static void main(String[] args) throws SQLException {

//        session.save(new User("I","D", (byte) 21));
//        session.save(new User("I","Di", (byte) 22));
//        session.save(new User("I","DO", (byte) 23));
//        session.save(new User("I","DT", (byte) 24));
//
//        List<User>userList=session.createQuery("from User").list();
//        userList.forEach(System.out::println);
//
//        session.createQuery("TRUNCATE table User").executeUpdate();



//        Connection connection;
//        DriverManager driverManager;
//
//
//        connection=DriverManager.getConnection(URL,LOGIN,PASSWORD);
//        Statement statement=connection.createStatement();

        UserServiceImpl user=new UserServiceImpl();
        user.createUsersTable();

        user.saveUser("Id","D", (byte) 21);
        user.saveUser("Ida","DI", (byte) 22);
        user.saveUser("Ia","OT", (byte) 23);
        user.saveUser("Is","))", (byte) 24);
        System.out.println("check");

        List<User> users=user.getAllUsers();


        users.forEach(System.out::println);

        user.cleanUsersTable();
        user.dropUsersTable();







    }
}
