package com.example.Service;
import java.util.*;
import com.example.DAO.*;
import com.example.DAOImpl.CartDAOImpl;
import com.example.Model.*;
public class CartService {

    private CartDAO cartDAO = new CartDAOImpl();
   

    public void addToCart(Cart cartItem) {
        cartDAO.addToCart(cartItem);
    }

    public ArrayList<Cart> getCartItems(String username) {
        return cartDAO.getCartItems(username);
    }

    public void removeFromCart(String productName) {
        cartDAO.removeFromCart(productName);
    }

    

	public void removeProductFromCart(String currentuser, String checkoutProductName) {
		cartDAO.removeProductFromCart(currentuser, checkoutProductName);
	}
}


