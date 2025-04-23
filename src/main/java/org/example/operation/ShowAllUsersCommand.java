package org.example.operation;

import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUsersCommand implements OperationCommand
{
    @Autowired
    UserService userService;
    @Override
    public void execute() {
        System.out.println("List of all users:");
        System.out.println(userService.getAllUsers());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
