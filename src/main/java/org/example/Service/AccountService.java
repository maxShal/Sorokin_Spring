package org.example.Service;

import org.example.AccountProperties;
import org.example.HibernateUtil;
import org.example.entity.User;
import org.example.exeption.ExceptionAccount;
import org.example.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Component
public class AccountService
{

    @Autowired
    AccountProperties accountProperties;

    @Autowired
    SessionFactory sessionFactory;



    public Account createAccount( long userId)
    {
       return executeInTransaction(() -> {
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
/*        Session session = null;
        Transaction transaction = null;
        Account account = null;

        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if(user == null)
            {
                throw new IllegalArgumentException("Пользователь с id " + userId + " не найден");
            }
            account = new Account(user,accountProperties.getDefaultAmount());
            session.persist(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return account;*/
    }

    public Account getById(Long accountId)
    {
        return executeInTransaction(() -> {
            Account account = sessionFactory.getCurrentSession().get(Account.class, accountId);
            if(account != null)
            {
                return account;
            }
            else {
                throw new ExceptionAccount("Не существует такого аккаунта ");
            }
        });
/*        Session session = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Account account = session.get(Account.class, accountId);
            if(account != null)
            {
                return account;
            }
            else {
                throw new ExceptionAccount(" Не существует такого аккаунта ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;*/
    }

    public void topUp(Account account, double moneyToUp)
    {
        executeInTransaction(()->{
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
/*        Session session = null;
        Transaction transaction = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Account account1 = session.get(Account.class, account.getId());
            if(moneyToUp <= 0)
            {
                throw new ExceptionAccount("Недопустимая сумма к пополнению");
            }
            if (account1 == null || account1.getId() <= 0) {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            account1.setMoneyAmount(account1.getMoneyAmount()+moneyToUp);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }*/

    }

    public void withdraw (Account account, long moneyToDown)
    {
        executeInTransaction(()->{
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
        /*Session session = null;
        Transaction transaction = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Account account1 = session.get(Account.class, account.getId());
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
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }*/
    }

    public void closeAccount(long accountId )
    {
        executeInTransaction(() ->{
            Account account = sessionFactory.getCurrentSession().get(Account.class, accountId);
            if (account == null)
            {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            sessionFactory.getCurrentSession().remove(account);
            return null;
        });
/*        Session session = null;
        Transaction transaction = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            Account account = session.get(Account.class, accountId);
            if (account == null)
            {
                throw new ExceptionAccount("Нет такого аккаунта");
            }
            session.remove(account);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }*/

    }

    public void transfer(long firstAccount, long secondAccount, double transfer)
    {
        executeInTransaction(() -> {
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
        /*Session session = null;
        Transaction transaction = null;

        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Account firstAcc = session.get(Account.class, firstAccount);
            Account secondAcc = session.get(Account.class, secondAccount);
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
            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

*/
    }
    public<T> T executeInTransaction(Supplier<T> action) {
        var session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        if (!transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
            return action.get();
        }
        try {
            session.beginTransaction();
            T returnValue = action.get();
            transaction.commit();
            return returnValue;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
