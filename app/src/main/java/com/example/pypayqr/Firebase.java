package com.example.pypayqr;

public class Firebase {

    int balance;
    String email;
    String fname;
    String lname;
    String pass;
    String user;

    public Firebase(){
    }

    public Firebase(int balance, String email, String fname, String lname, String pass, String user) {
        this.balance = balance;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.pass = pass;
        this.user = user;
    }

    public int getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }
}
