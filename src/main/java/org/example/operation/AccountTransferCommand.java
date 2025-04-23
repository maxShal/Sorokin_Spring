package org.example.operation;

import org.example.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountTransferCommand implements OperationCommand{

    @Autowired
    AccountService accountService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter source account ID:");
        long sourceAccount = scanner.nextLong();
        System.out.println("Enter target account ID");
        long targetAccount = scanner.nextLong();
        System.out.println("Enter amount to transfer");
        long amountToTransfer = scanner.nextLong();
        accountService.transfer(sourceAccount, targetAccount, amountToTransfer);
        System.out.println("Amount " + amountToTransfer + " transferred from account ID: " + sourceAccount + " to account ID: " + targetAccount);

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
