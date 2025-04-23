package org.example.Service;

import jakarta.annotation.Nullable;
import org.example.AccountProperties;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exeption.ExceptionAccount;
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
    private Map<Long, User> userMap =  new HashMap<>();
    @Autowired
    AccountProperties accountProperties;
    @Autowired
    AccountService accountService;

    //private AccountService accountService = new AccountService();


    public Map<Long, User> getUserMap() {
        return userMap;
    }

    public User createUser(String login){

        User user = new User(userMap.size()+1, login, null);
        Account account = accountService.createAccount(user.getId());
        user.getAccountList().add(account);
        userMap.put((long)userMap.size()+1 , user);
        return user;
    }

    public User getById(Long userId)
    {
        if(!userMap.containsKey(userId))
        {
            throw new ExceptionAccount("Нет такого юзера");
        }
        return userMap.get(userId);
    }
    public List<User> getAllUsers()
    {
       return new ArrayList<>(userMap.values());
    }
}
