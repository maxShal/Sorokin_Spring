package org.example.operation;

import org.example.AccountProperties;
import org.example.Service.AccountService;
import org.example.entity.Account;
import org.example.exeption.ExceptionAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class AccountDepositCommand implements OperationCommand{
    @Autowired
    AccountService accountService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account ID:");
        long accountId = scanner.nextLong();
        if(accountId <= 0)
        {
            throw new ExceptionAccount("Incorrect ID");
        }
        System.out.println("Enter amount to deposit:");
        long deposit = scanner.nextLong();
        Account accForTopUp = accountService.getById(accountId);
        if (accountService.getAccountMap().containsKey(accountId))
        {
            throw new ExceptionAccount("Such account does not exist");
        }
        accountService.topUp(accForTopUp, deposit);
        System.out.println("Amount " + deposit + " deposited  to account ID: " + accountId);
        System.out.println(accForTopUp.getMoneyAmount());

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
