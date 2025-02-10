package com.example.Model;

public class Admin {
    private int adminID;
    private String adminName;
    private String email;
    private String address;
    private String password;

    // Constructors, getters, and setters

    public Admin(String adminName, String password, String email, String address) {
        this.adminName = adminName;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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
