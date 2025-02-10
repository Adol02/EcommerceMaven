package com.example.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.example.DAO.WishListDAO;
import com.example.DB.DBConfig;
import com.example.Model.*;

public class WishListDAOImpl implements WishListDAO {
	private List<WishList> WishList = new ArrayList<>();
	 
    public WishListDAOImpl() {
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
    	 String createCartTable = "CREATE TABLE IF NOT EXISTS wishlist ("
                    + "wishListID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(255),"
                    + "productname VARCHAR(255))";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {
        	 stmt.execute(createCartTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addToWishList(WishList wishList) {
        String query = "INSERT INTO wishlist (userName, productname) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, wishList.getUserName());
            pstmt.setString(2, wishList.getProductName());
            pstmt.executeUpdate();
            WishList.add(wishList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
	public ArrayList<WishList> getWishList(String username) {
		ArrayList<WishList> wishLists = new ArrayList<>();
        String query = "SELECT * FROM wishlist WHERE username = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                WishList wishList = new WishList();
                wishList.setProductName(rs.getString("productname"));
                wishLists.add(wishList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishLists;
	}

	@Override
	public void deleteFromWishlist(String username, String productName) {
		String query = "DELETE FROM wishlist WHERE username = ? AND productname =?";
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
	    public ArrayList<WishList> getAllWishlist() {
	        ArrayList<WishList> list = new ArrayList<>();
	        String query = "SELECT * FROM wishlist";
	        try (Connection conn = DBConfig.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                WishList wishlist = new WishList();
	                wishlist.setWishListID(rs.getInt("cartID"));
	                wishlist.setUserName(rs.getString("username"));
	                wishlist.setProductName(rs.getString("productname"));
	                list.add(wishlist);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
               
}
