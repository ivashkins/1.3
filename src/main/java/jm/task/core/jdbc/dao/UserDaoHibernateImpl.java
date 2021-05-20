package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Session session;


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Statement statement = Util.getStatement();
        try {
            assert statement != null;
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                    " id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " age INTEGER not NULL)");
            statement.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Statement statement = Util.getStatement();
        try {
            statement.execute("drop table if exists user ");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();


    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        if (session.get(User.class, id) != null) {
            session.delete(session.get(User.class, id));
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("from User").list();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("Delete From User").executeUpdate();
        session.getTransaction().commit();
        session.close();

    }
}
