package org.example.Service;

import jakarta.annotation.Nullable;
import org.example.AccountProperties;
import org.example.HibernateUtil;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exeption.ExceptionAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class UserService
{
    @Autowired
    AccountProperties accountProperties;
    @Autowired
    AccountService accountService;
    @Autowired
    SessionFactory sessionFactory;


    public User createUser(String login){

        return executeInTransaction(() -> {
            User user = new User(login);
            sessionFactory.getCurrentSession().persist(user);
            return user;
        });
/*        Session session = null;
        Transaction transaction = null;
        User user = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = new User(login);
            session.persist(user);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();

        }

*//*        User user = new User(userMap.size()+1, login, null);
        Account account = accountService.createAccount(user.getId());
        user.getAccountList().add(account);
        userMap.put((long)userMap.size()+1 , user);*//*
        return user;*/
    }

    public User getById(Long userId)
    {
        return executeInTransaction(() -> {
            User user = sessionFactory.getCurrentSession().get(User.class, userId);
            if(user != null)
            {
                return user;
            }
            else {
                throw new ExceptionAccount("Не существует такого аккаунта ");
            }
        });
/*        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            User user = session.get(User.class, userId);
            if(user != null)
            {
                return user;
            }
            else {
                throw new ExceptionAccount("Не существует такого аккаунта ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;*/
    }
    public List<User> getAllUsers()
    {
        return executeInTransaction(() -> {
            List<User> users = new ArrayList<>();
            users = sessionFactory.getCurrentSession()
                    .createQuery("FROM User", User.class).list();
            return users;
        });
/*        Session session = null;
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;*/
    }

    public<T> T executeInTransaction(Supplier<T> action) {
        var session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (!transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
            return action.get();
        }
        try {
            session.beginTransaction();
            T returnValue = action.get();
            transaction.commit();
            return returnValue;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
