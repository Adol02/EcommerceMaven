package com.example.DAOImpl;

import java.sql.*;
import java.util.*;

import com.example.DAO.OrderDAO;
import com.example.DB.DBConfig;
import com.example.Model.Order;

public class OrderDAOImpl implements OrderDAO{
	public OrderDAOImpl() {
        createTableIfNotExists();
    }
 
    private void createTableIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS orders (" +
                "orderID INT AUTO_INCREMENT PRIMARY KEY," +
                "userName VARCHAR(200)," +
                "productName VARCHAR(200)," +
                "quantity INT," +
                "price DOUBLE," +
                "status VARCHAR(20))";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(createTableQuery)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	@Override
	public void addCheckout(Order order) {
		String query = "INSERT INTO orders (userName, productName, quantity, price, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, order.getUserName());
            pstmt.setString(2, order.getProductName());
            pstmt.setInt(3, order.getQuantity());
            pstmt.setDouble(4, order.getPrice());
            pstmt.setString(5, order.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public ArrayList<Order> getAllOrders() {
		ArrayList<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	orders.add(new Order(
                		rs.getInt("orderId"),
                        rs.getString("userName"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
	}

	@Override
	public void updateOrderStatus(int orderID, String status) {
		String query = "UPDATE orders SET status = ? WHERE orderID = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	 pstmt.setString(1, status);
            pstmt.setInt(2, orderID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public ArrayList<Order> getOrdersByUserName(String userName) {
		ArrayList<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE userName = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	Order order = new Order(
                        rs.getString("userName"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
            	order.setOrderID(rs.getInt("orderID"));
            	orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
	}

	@Override
	public Map<String, Double> generateTotalSalesReport() {
		 Map<String, Double> salesReport = new HashMap<>();
	        String query = "SELECT productName, SUM(quantity * price) AS totalSales FROM orders where status = ? GROUP BY productName";
	        try (Connection conn = DBConfig.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)){
	        		pstmt.setString(1, "Order Delivered");
	               ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                salesReport.put(rs.getString("productName"), rs.getDouble("totalSales"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return salesReport;
	}
	

}
