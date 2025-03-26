package org.example.entity;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class User {

    private Long id;

    private String login;

    private List<Account> accountList = new ArrayList<>();

    public User(long id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList != null ? accountList : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(accountList, user.accountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, accountList);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
