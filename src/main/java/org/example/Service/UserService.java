package org.example.Service;

import jakarta.annotation.Nullable;
import org.example.AccountProperties;
import org.example.HibernateUtil;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exeption.ExceptionAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService
{
    @Autowired
    AccountProperties accountProperties;
    @Autowired
    AccountService accountService;


    public User createUser(String login){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        User user = null;
        try{
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

/*        User user = new User(userMap.size()+1, login, null);
        Account account = accountService.createAccount(user.getId());
        user.getAccountList().add(account);
        userMap.put((long)userMap.size()+1 , user);*/
        return user;
    }

    public User getById(Long userId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
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
        return null;
    }
    public List<User> getAllUsers()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = new ArrayList<>();
        try {
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }
}
