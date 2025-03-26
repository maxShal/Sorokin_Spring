package org.example.entity;


import java.util.Objects;


public class Account {

    private long id;

    private long userId;

    private long moneyAmount;

    public Account(long id, long userId, long moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setUserId(long userId) {

        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && userId == account.userId && moneyAmount == account.moneyAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, moneyAmount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
