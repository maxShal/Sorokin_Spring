package org.example.Service;

import org.example.AccountProperties;
import org.example.exeption.ExceptionAccount;
import org.example.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountService
{

    @Autowired
    AccountProperties accountProperties;
    private Map<Long, Account> accountMap = new HashMap<>();
    private long nextAccountId = 1;

    public Account createAccount( long userId)
    {
        Account account = new Account(nextAccountId++, userId, accountProperties.getDefaultAmount());
        accountMap.put(account.getId(), account);
        return account;
    }

    public Account getById(Long accountId)
    {
        if(!accountMap.containsKey(accountId))
        {
            throw new ExceptionAccount("Не существует такого аккаунта ");
        }
        return accountMap.get(accountId);
    }

    public void topUp(Account account, double moneyToUp)
    {
        if(moneyToUp <= 0)
        {
            throw new ExceptionAccount("Недопустимая сумма к пополнению");
        }
        if (account == null || account.getId() <= 0) {
            throw new ExceptionAccount("Нет такого аккаунта");
        }
        account.setMoneyAmount(account.getMoneyAmount()+moneyToUp);
        accountMap.put(account.getId(), account);
    }

    public void withdraw (Account account, long moneyToDown)
    {
        if (account == null || account.getId() <= 0) {
            throw new ExceptionAccount("Нет такого аккаунта");
        }
        if (account.getMoneyAmount() < moneyToDown)
        {
            throw new ExceptionAccount("Недостаточно средств");
        }
        if(moneyToDown <= 0)
        {
            throw new ExceptionAccount("Недопустимая сумма к снятию");
        }
        account.setMoneyAmount(account.getMoneyAmount()-moneyToDown);
        accountMap.put(account.getId(), account);
    }

    public void closeAccount(long accountId )
    {
        if (accountId <= 0)
        {
            throw new ExceptionAccount("Нет такого аккаунта");
        }
        accountMap.remove(accountId);
    }

    public void transfer(long firstAccount, long secondAccount, double transfer)
    {
        Account firstAcc = accountMap.get(firstAccount);
        Account secondAcc = accountMap.get(secondAccount);

        if (firstAccount <= 0 || secondAccount <= 0)
        {
            throw new ExceptionAccount("Нет такого аккаунта");
        }
        if (firstAcc.getMoneyAmount() < transfer)
        {
            throw new ExceptionAccount("Недостаточно средств");
        }
        if(transfer <= 0)
        {
            throw new ExceptionAccount("Недопустимая сумма к переводу");
        }
        if(firstAcc.getUserId() != secondAcc.getUserId())
        {
            double commission = transfer * accountProperties.getTransferCommission() + transfer;
            firstAcc.setMoneyAmount(firstAcc.getMoneyAmount() - commission);
            accountMap.put(firstAccount, firstAcc);

            secondAcc.setMoneyAmount(secondAcc.getMoneyAmount() + commission);
            accountMap.put(secondAccount, secondAcc);
        }
        else {
            firstAcc.setMoneyAmount(firstAcc.getMoneyAmount() - transfer);
            accountMap.put(firstAccount, firstAcc);

            secondAcc.setMoneyAmount(secondAcc.getMoneyAmount() + transfer);
            accountMap.put(secondAccount, secondAcc);
        }
    }
}
