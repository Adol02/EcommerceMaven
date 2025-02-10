package com.example.Model;

public class Cart {
    private String userName;
    private String productName;
    private int quantity;
    private int cartID;
    private double price; // New field for price

    public Cart(String username, String productname, int quantity) {
        this.userName = username;
        this.productName = productname;
        this.quantity = quantity;
    }
    public Cart(String username, String productname, int quantity, double price) {
        this.userName = username;
        this.productName = productname;
        this.quantity = quantity;
        this.price = price;
    }

    public Cart() {}

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
