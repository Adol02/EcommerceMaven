package com.example.Model;

public class Order {
	private int orderID;
    private String userName;
    private String productName;
    private int quantity;
    private double price;
    private String status;
 
    public Order(String userName, String ProductName, int quantity, double price, String status) {
        this.userName = userName;
        this.productName =ProductName ;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
    public Order( int orderid,String userName, String ProductName, int quantity, double price, String status) {
        this.orderID=orderid;
    	this.userName = userName;
        this.productName =ProductName ;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
    
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
}
