package com.example.DAO;

import java.util.ArrayList;
import java.util.Map;

import com.example.Model.*;

public interface OrderDAO {
	void addCheckout(Order order);
    ArrayList<Order> getAllOrders();
    void updateOrderStatus(int checkoutID, String status);
	ArrayList<Order> getOrdersByUserName(String userName);
	Map<String, Double> generateTotalSalesReport();
}
