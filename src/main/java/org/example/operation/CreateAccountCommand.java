package org.example.operation;

import org.example.Service.AccountService;
import org.example.Service.UserService;
import org.example.entity.Account;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand
{

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user id for which to create an account:");
        long userId = scanner.nextLong();
        Account account1 = accountService.createAccount(userId);
        User user1 = userService.getById(userId);
        user1.getAccountList().add(account1);
        System.out.println("New account created with ID: " + account1.getId() + " for user: " + userService.getById(userId).getLogin());

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
