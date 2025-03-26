package org.example.Service;

import org.example.ExceptionAccount;
import org.example.entity.Account;
import org.example.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountService
{

    private Map<Long, Account> accountMap = new HashMap<>();

    public Account createAccount( long userId, long moneyAmount)
    {
        Account account = new Account(accountMap.size()+1, userId, moneyAmount);
        accountMap.put((long) accountMap.size()+1, account);
        return account;
    }

    public Account getById(Long accountId)
    {
        return accountMap.get(accountId);
    }

    public void topUp(Account account, long moneyToUp)
    {
        account.setMoneyAmount(account.getMoneyAmount()+moneyToUp);
        accountMap.put(account.getId(), account);
    }

    public void withdraw (Account account, long moneyToDown)
    {
        if (account.getMoneyAmount() < moneyToDown)
        {
            throw new ExceptionAccount("Недостаточно средств");
        }
        account.setMoneyAmount(account.getMoneyAmount()-moneyToDown);
        accountMap.put(account.getId(), account);
    }

    public void closeAccount(long accountId )
    {
        accountMap.remove(accountId);
    }

    public void transfer(long firstAccount, long secondAccount, long transfer)
    {
        Account firstAcc = accountMap.get(firstAccount);
        Account secondAcc = accountMap.get(secondAccount);

        if (firstAcc.getMoneyAmount() < transfer)
        {
            throw new ExceptionAccount("Недостаточно средств");
        }

        firstAcc.setMoneyAmount(firstAcc.getMoneyAmount() - transfer);
        accountMap.put(firstAccount, firstAcc);

        secondAcc.setMoneyAmount(secondAcc.getMoneyAmount() + transfer);
        accountMap.put(secondAccount, secondAcc);
    }
}
