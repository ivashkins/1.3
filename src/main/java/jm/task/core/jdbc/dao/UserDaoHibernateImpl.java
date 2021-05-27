package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private Transaction tx = null;


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Statement statement = Util.getStatement();
        String sql=("CREATE TABLE IF NOT EXISTS User (" +
                " id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                " name VARCHAR(50), " +
                " lastName VARCHAR (50), " +
                " age INTEGER not NULL)");

        try {
            session=Util.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            tx.commit();
            session.close();


        } catch (Exception e) {
          tx.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
           String sql="drop table if exists user ";
            session=Util.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
            session.close();
        } catch (HibernateException throwables) {
            tx.rollback();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            tx.rollback();
        }


    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            if (session.get(User.class, id) != null) {
                session.delete(session.get(User.class, id));
            }
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            tx.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            userList = session.createQuery("from User").list();
            session.close();
        } catch (HibernateException ex) {
            tx.rollback();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createQuery("Delete From User").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            tx.rollback();
        }

    }
}
