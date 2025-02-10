package com.example.Model;

public class User {
    //private int userID;
    private String userName;
    private String email;
    private String address;
    private String password;
    

    // Constructors, getters, and setters
    static int userId = 1002;
     int id;
    public User(String userName, String password, String email, String address) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.id = userId++;   
    }

    public int getUserID() {
        return id;
    }

    public void setUserID(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
