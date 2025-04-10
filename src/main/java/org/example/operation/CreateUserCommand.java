package org.example.operation;

import org.example.Service.AccountService;
import org.example.Service.UserService;
import org.example.entity.Account;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter login for new user: ");
        String login = scanner.nextLine();
        System.out.println("User created: " + userService.createUser(login));

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
