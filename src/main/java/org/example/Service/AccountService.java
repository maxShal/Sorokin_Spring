package org.example.Service;

import org.example.AccountProperties;
import org.example.TransactionHelper;
import org.example.entity.User;
import org.example.exeption.ExceptionAccount;
import org.example.entity.Account;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class AccountService
{

    @Autowired
    AccountProperties accountProperties;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    TransactionHelper transactionHelper;

    public Account createAccount( long userId)
    {
        return transactionHelper.executeInTransaction(() -> {
            Account account = null;
            User user = sessionFactory.getCurrentSession().get(User.class, userId);
            if(user == null)
            {
                throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
            }
            account = new Account(user,accountProperties.getDefaultAmount());
            sessionFactory.getCurrentSession().persist(account);
            return account;
        });
    }

    public Account getById(Long accountId)
    {
        return transactionHelper.executeInTransaction(() -> {
            Account account = sessionFactory.getCurrentSession().get(Account.class, accountId);
            if(account != null)
            {
                return account;
            }
            else {
                throw new ExceptionAccount("Не существует такого аккаунта ");
            }
        });
    }

    public void topUp(Account account, double moneyToUp)
    {
        transactionHelper.executeInTransaction(()->{
            Account account1 = sessionFactory.getCurrentSession().get(Account.class, account.getId());
            if(moneyToUp <= 0)
            {
                throw new ExceptionAccount("Недопустимая сумма к пополнению");
            }
            if (account1 == null || account1.getId() <= 0) {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            account1.setMoneyAmount(account1.getMoneyAmount()+moneyToUp);
            return null;
        });

    }

    public void withdraw (Account account, long moneyToDown)
    {
        transactionHelper.executeInTransaction(()->{
            Account account1 = sessionFactory.getCurrentSession().get(Account.class, account.getId());
            if(moneyToDown <= 0)
            {
                throw new ExceptionAccount("Недопустимая сумма к снятию");
            }
            if (account1 == null ) {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            if (account1.getMoneyAmount() < moneyToDown)
            {
                throw new ExceptionAccount("Недостаточно средств на аккаунте");
            }
            account1.setMoneyAmount(account1.getMoneyAmount()-moneyToDown);
            return  null;
    });
    }

    public void closeAccount(long accountId )
    {
        transactionHelper.executeInTransaction(() ->{
            Account account = sessionFactory.getCurrentSession().get(Account.class, accountId);
            if (account == null)
            {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            sessionFactory.getCurrentSession().remove(account);
            return null;
        });

    }

    public void transfer(long firstAccount, long secondAccount, double transfer)
    {
        transactionHelper.executeInTransaction(() -> {
            Account firstAcc = sessionFactory.getCurrentSession().get(Account.class, firstAccount);
            Account secondAcc = sessionFactory.getCurrentSession().get(Account.class, secondAccount);
            if (firstAcc == null || secondAcc == null)
            {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            if(transfer <= 0)
            {
                throw new ExceptionAccount("Недопустимая сумма к переводу");
            }
            if(!Objects.equals(firstAcc.getUser().getId(), secondAcc.getUser().getId()))
            {
                if (firstAcc.getMoneyAmount() < transfer)
                {
                    throw new ExceptionAccount("Недостаточно средств");
                }
                double commission = transfer * accountProperties.getTransferCommission() + transfer;
                firstAcc.setMoneyAmount(firstAcc.getMoneyAmount() - commission);

                secondAcc.setMoneyAmount(secondAcc.getMoneyAmount() + transfer);
            }
            else {
                if (firstAcc.getMoneyAmount() < transfer)
                {
                    throw new ExceptionAccount("Недостаточно средств");
                }
                firstAcc.setMoneyAmount(firstAcc.getMoneyAmount() - transfer);

                secondAcc.setMoneyAmount(secondAcc.getMoneyAmount() + transfer);;
            }
            return null;
        });
    }

}
