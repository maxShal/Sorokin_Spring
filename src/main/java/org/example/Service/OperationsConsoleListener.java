package org.example.Service;

import org.example.ExceptionAccount;
import org.example.entity.Account;
import org.example.entity.User;

import java.util.*;


public class OperationsConsoleListener
{


    public static void  main(String[] args)
    {
        UserService userService = new UserService();
        AccountService accountService =new AccountService();
        Scanner scanner = new Scanner(System.in);

        int command;

        System.out.println("Please enter one of operation type:");
        System.out.println("1) -ACCOUNT_CREATE");
        System.out.println("2) -SHOW_ALL_USERS");
        System.out.println("3) -ACCOUNT_CLOSE");
        System.out.println("4) -ACCOUNT_WITHDRAW");
        System.out.println("5) -ACCOUNT_DEPOSIT");
        System.out.println("6) -ACCOUNT_TRANSFER");
        System.out.println("7) -USER_CREATE");
        //List<Account> accountList = new ArrayList<>();

        while(true) {
            List<Account> accountList = new ArrayList<>();

            try {
                command = scanner.nextInt();
                scanner.nextLine();
                switch (command) {
                    case 1:
                        System.out.println("Enter the user id for which to create an account:");
                        long userId = scanner.nextLong();
                        Account account1 = accountService.createAccount(userId, 500L);
                        User user1 = userService.getById(userId);
                        user1.getAccountList().add(account1);
                        System.out.println("New account created with ID: " + userService.getById(userId).getAccountList().size() + " for user: " + userService.getById(userId).getLogin());
                        break;
                    case 2:
                        System.out.println("List of all users:");
                        System.out.println(userService.getAllUsers());
                        break;
                    case 3:
                        System.out.println("Enter account ID to close:");
                        long accountToClose = scanner.nextLong();
                        accountService.closeAccount(accountToClose);
                        System.out.println("Account with ID" + accountToClose + "has been closed.");
                    case 4:
                        System.out.println("Enter account ID to withdraw from:");
                        long accountIDToWithdraw = scanner.nextLong();
                        System.out.println("Enter amount to withdraw:");
                        long amountToWithdraw = scanner.nextLong();
                        accountService.withdraw(accountService.getById(accountIDToWithdraw), amountToWithdraw);
                        break;
                    case 5:
                        System.out.println("Enter account ID:");
                        long accountId = scanner.nextLong();
                        System.out.println("Enter amount to deposit:");
                        long deposit = scanner.nextLong();
                        Account accForTopUp = accountService.getById(accountId);
                        accountService.topUp(accForTopUp, deposit);
                        System.out.println("Amount " + deposit + " deposited  to account ID: " + accountId);
                        System.out.println(accForTopUp.getMoneyAmount());
                        break;
                    case 6:
                        System.out.println("Enter source account ID:");
                        long sourceAccount = scanner.nextLong();
                        System.out.println("Enter target account ID");
                        long targetAccount = scanner.nextLong();
                        System.out.println("Enter amount to transfer");
                        long amountToTransfer = scanner.nextLong();
                        accountService.transfer(sourceAccount, targetAccount, amountToTransfer);
                        System.out.println("Amount " + amountToTransfer + " transferred from account ID: " + sourceAccount + " to account ID: " + targetAccount);
                        break;
                    case 7:
                        System.out.println("Enter login for new user: ");
                        String login = scanner.nextLine();
                        User user = new User(userService.getAllUsers().size() + 1, login, accountList);
                        Account account = accountService.createAccount(user.getId(), 500L);
                        user.getAccountList().add(account);

                        userService.createUser(user.getId(), user.getLogin(), user.getAccountList());
                        System.out.println("User created: " + user);
                        break;
                    default:
                        System.out.println("Enter valid command");
                }

            }catch (ExceptionAccount e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("Please enter one of operation type:");
            System.out.println("1) -ACCOUNT_CREATE");
            System.out.println("2) -SHOW_ALL_USERS");
            System.out.println("3) -ACCOUNT_CLOSE");
            System.out.println("4) -ACCOUNT_WITHDRAW");
            System.out.println("5) -ACCOUNT_DEPOSIT");
            System.out.println("6) -ACCOUNT_TRANSFER");
            System.out.println("7) -USER_CREATE");
        }

    }

}
