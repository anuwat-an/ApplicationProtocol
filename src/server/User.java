/**
 * Anuwat Angkuldee 5810401066
 */

package server;

import java.util.Vector;

public class User {

    private String user;
    private String pass;
    private int balance;
    private boolean loggedin;
    private Vector<String> contents;

    public User(String user, String pass) {
        this.user = user;
        this.pass = pass;
        this.balance = 0;
        this.loggedin = false;
        this.contents = new Vector<>();
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

    public boolean isLoggedin() {
        return loggedin;
    }

    public Vector<String> getContents() {
        return contents;
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

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

//    public void toggleLoggedin() {
//        this.loggedin = !this.loggedin;
//    }

    public void addContent(String content) {
        this.contents.add(content);
    }
}
