package com.example.Service;

import java.util.*;

import com.example.DAO.*;
import com.example.DAOImpl.*;
import com.example.Model.*;
public class WishListService {
	 private WishListDAO wishListDAO = new WishListDAOImpl();
	   

	    public void addToWishList(WishList wishList) {
	    	wishListDAO.addToWishList(wishList);
	    }
	    public ArrayList<WishList> getWishList(String username) {
	        return wishListDAO.getWishList(username);
	    }
	    public void deleteFromWishlist(String currentuser, String checkoutProductName) {
	    	wishListDAO.deleteFromWishlist(currentuser, checkoutProductName);
		}
	    public ArrayList<WishList> getAllWishList() {
	        return wishListDAO.getAllWishlist();
	    }
	    
}
