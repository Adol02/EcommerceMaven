package com.example.Model;

public class WishList {
	private String userName;
    private String productName;
	private int wishListID;
	
	public WishList(String username, String productname){
		this.userName = username;
		this.productName= productname;
	}
	
	public WishList() {}
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
	public int getWishListID() {
		return wishListID;
	}
	public void setWishListID(int wishListID) {
		this.wishListID = wishListID;
	}
}
