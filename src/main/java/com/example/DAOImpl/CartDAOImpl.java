package com.example.DAOImpl;

import java.sql.*;
import java.util.*;
import com.example.DAO.*;
import com.example.DB.DBConfig;
import com.example.Model.*;
import com.example.Service.ProductService;

public class CartDAOImpl implements CartDAO {
	    
	    ProductService productService = new ProductService();
	 
	    public CartDAOImpl() {
	        createTableIfNotExists();
	    }
	    
	    private void createTableIfNotExists() {
	    	 String createCartTable = "CREATE TABLE IF NOT EXISTS cart ("
	                    + "cartID INT AUTO_INCREMENT PRIMARY KEY,"
	                    + "username VARCHAR(255),"
	                    + "productname VARCHAR(255),"
	                    + "quantity INT,"
	                    + "price DECIMAL(10, 2)"
	                    + ")";
	        try (Connection conn = DBConfig.getConnection();
	             Statement stmt = conn.createStatement()) {
	        	 stmt.execute(createCartTable);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    public void addToCart(Cart cartItem) {
	        try(Connection conn = DBConfig.getConnection()) {
	            // Check if the product is already in the cart
	            String checkQuery = "SELECT * FROM cart WHERE username = ? AND productname = ?";
	            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	            checkStmt.setString(1, cartItem.getUserName());
	            checkStmt.setString(2, cartItem.getProductName());
	            ResultSet rs = checkStmt.executeQuery();

	            if (rs.next()) {
	                // If the product is already in the cart, update the quantity and price
	                int existingQuantity = rs.getInt("quantity");
	                int newQuantity = existingQuantity + cartItem.getQuantity();
	                
	                // Fetch the latest price from ProductService
	                Product product = productService.getProductByName(cartItem.getProductName());
	                double price = product.getPrice();

	                String updateQuery = "UPDATE cart SET quantity = ?, price = ? WHERE username = ? AND productname = ?";
	                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
	                updateStmt.setInt(1, newQuantity);
	                updateStmt.setDouble(2, price);
	                updateStmt.setString(3, cartItem.getUserName());
	                updateStmt.setString(4, cartItem.getProductName());
	                updateStmt.executeUpdate();
	            } else {
	                // If the product is not in the cart, insert a new record
	                // Fetch the latest price from ProductService
	                Product product = productService.getProductByName(cartItem.getProductName());
	                double price = product.getPrice();
	                cartItem.setPrice(price);

	                String insertQuery = "INSERT INTO cart (username, productname, quantity, price) VALUES (?, ?, ?, ?)";
	                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	                insertStmt.setString(1, cartItem.getUserName());
	                insertStmt.setString(2, cartItem.getProductName());
	                insertStmt.setInt(3, cartItem.getQuantity());
	                insertStmt.setDouble(4, cartItem.getPrice());
	                insertStmt.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

		@Override
		public ArrayList<Cart> getCartItems(String username) {
			ArrayList<Cart> cartItems = new ArrayList<>();
	        String query = "SELECT cartID, username, productname, quantity, price FROM cart WHERE username = ?";
	        try (Connection conn = DBConfig.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setString(1, username);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                Cart cartItem = new Cart();
	                cartItem.setCartID(rs.getInt("cartID"));
	                cartItem.setUserName(rs.getString("username"));
	                cartItem.setProductName(rs.getString("productname"));
	                cartItem.setQuantity(rs.getInt("quantity"));
	                cartItem.setPrice(rs.getDouble("price"));
	                cartItems.add(cartItem);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return cartItems;
		}

		//if product is checked out it removes the product from the cart..
		@Override
		public void removeProductFromCart(String username, String productName) {
	        String query = "DELETE FROM cart WHERE username = ? AND productname =?";
	        try (Connection conn = DBConfig.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(query)) {
		            pstmt.setString(1, username);
		            pstmt.setString(2, productName);
		            pstmt.executeUpdate();
		}catch (SQLException e) {
            e.printStackTrace();
        }
		}
	
		    @Override
		    public void removeFromCart(String productName) {
		        String sql = "DELETE FROM cart WHERE productname = ?";
		        try (Connection conn = DBConfig.getConnection();
			             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            pstmt.setString(1, productName);
		            pstmt.executeUpdate();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

	 
	
}
