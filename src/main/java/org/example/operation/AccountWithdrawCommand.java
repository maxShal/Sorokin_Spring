package org.example.operation;

import org.example.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountWithdrawCommand implements OperationCommand{

    @Autowired
    AccountService accountService;
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account ID to withdraw from:");
        long accountIDToWithdraw = scanner.nextLong();
        System.out.println("Enter amount to withdraw:");
        long amountToWithdraw = scanner.nextLong();
        accountService.withdraw(accountService.getById(accountIDToWithdraw), amountToWithdraw);

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
