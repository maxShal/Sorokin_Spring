package org.example.Service;

import org.example.TransactionHelper;
import org.example.entity.User;
import org.example.exeption.ExceptionAccount;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    TransactionHelper transactionHelper;


    public User createUser(String login){

        return transactionHelper.executeInTransaction(() -> {
            User user = new User(login);
            sessionFactory.getCurrentSession().persist(user);
            return user;
        });
    }

    public User getById(Long userId)
    {
        return transactionHelper.executeInTransaction(() -> {
            User user = sessionFactory.getCurrentSession().get(User.class, userId);
            if(user != null)
            {
                return user;
            }
            else {
                throw new ExceptionAccount("Не существует такого аккаунта ");
            }
        });
    }
    public List<User> getAllUsers()
    {
        return transactionHelper.executeInTransaction(() -> {
            List<User> users = new ArrayList<>();
            users = sessionFactory.getCurrentSession()
                    .createQuery("FROM User", User.class).list();
            return users;
        });
    }
}
