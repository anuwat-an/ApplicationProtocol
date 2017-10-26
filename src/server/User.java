package server;

import java.util.ArrayList;

public class User {

    private String user;
    private String pass;
    private int balance;
    private ArrayList<String> contents;

    public User(String user, String pass) {
        this.user = user;
        this.pass = pass;
        this.balance = 0;
        this.contents = new ArrayList<>();
    }

    public boolean checkUser(String user, String pass) {
        return this.user.equalsIgnoreCase(user) && this.pass.equals(pass);
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int balance) {
//        if (balance > 0)
            this.balance += balance;
    }

    public void subBalance(int balance) {
//        if (balance > 0)
            this.balance -= balance;
    }

    public void addContent(String content) {
        this.contents.add(content);
    }

    public ArrayList<String> getContents() {
        return contents;
    }
}
