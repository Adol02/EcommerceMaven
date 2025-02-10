package com.example.Service;
import java.util.*;
import com.example.DAO.*;
import com.example.DAOImpl.*;
import com.example.Model.*;
public class OrderService {
	    private OrderDAO orderDAO = new OrderDAOImpl();
	 
	    public void addCheckout(Order checkout) {
	    	orderDAO.addCheckout(checkout);
	    }
	 
	    public ArrayList<Order> getAllOrders() {
	        return orderDAO.getAllOrders();
	    }
	 
	    public void updateOrderStatus(int checkoutID, String status) {
	    	orderDAO.updateOrderStatus(checkoutID, status);
	    }
	 
	    public ArrayList<Order> getOrdersByUserName(String userName) {
	        return orderDAO.getOrdersByUserName(userName);
	    }

	    public Map<String, Double> generateTotalSalesReport() {
			return orderDAO.generateTotalSalesReport();
	    }
		
	}

