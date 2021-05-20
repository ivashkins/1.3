package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {


        UserServiceImpl user=new UserServiceImpl();
        user.createUsersTable();

        user.saveUser("Id","D", (byte) 21);
        user.saveUser("Ida","DI", (byte) 22);
        user.saveUser("Ia","OT", (byte) 23);
        user.saveUser("Is","))", (byte) 24);
        System.out.println("check");

        List<User> users=user.getAllUsers();
        users.forEach(System.out::println);
        user.removeUserById(1L);


        user.cleanUsersTable();
   //     user.dropUsersTable();
        Util.closeHibernate();









    }
}
