package org.example.Service;

import jakarta.annotation.Nullable;
import org.example.entity.Account;
import org.example.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService
{
    private Map<Long, User> userMap =  new HashMap<>();

    public User createUser(Long id, String login, @Nullable List<Account> accountList){

        User user = new User(id, login, accountList);
        userMap.put(id, user);
        return user;
    }

    public User getById(Long userId)
    {
       return userMap.get(userId);
    }

    public List<User> getAllUsers()
    {
       return new ArrayList<>(userMap.values());
    }
}
