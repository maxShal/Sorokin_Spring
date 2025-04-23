package org.example.operation;

import org.example.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountCloseCommand  implements OperationCommand{

    @Autowired
    AccountService accountService;


    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account ID to close:");
        long accountToClose = scanner.nextLong();
        accountService.closeAccount(accountToClose);
        System.out.println("Account with ID" + accountToClose + "has been closed.");

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
