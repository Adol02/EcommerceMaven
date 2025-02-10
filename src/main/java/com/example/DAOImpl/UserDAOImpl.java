package com.example.DAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.DAO.UserDAO;
import com.example.DB.DBConfig;
import com.example.Model.*;

public class UserDAOImpl implements UserDAO {
    private List<User> usersList = new ArrayList<>();
    
    public UserDAOImpl() {
    	createTableifNonExists();
    }

    private void createTableifNonExists() {
    	String query = "CREATE TABLE IF NOT EXISTS users (" +
                "userID INT AUTO_INCREMENT PRIMARY KEY," +
                "userName VARCHAR(20)," +
                "Email VARCHAR(25)," +
                "Address VARCHAR(100)," +
                "passfield VARCHAR(25))";
    
    			try (Connection conn = DBConfig.getConnection();
    		             Statement stmt = conn.createStatement()) {
    		            stmt.execute(query);
    		        } catch (SQLException e) {
    		            e.printStackTrace();
    		        }
	}
    @Override
    public void addUser(User user) {
        String query = "INSERT INTO users (userName, Email, Address, passfield) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getPassword());
            pstmt.executeUpdate();

            // Get the generated userID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserID(generatedKeys.getInt(1));
            }

            usersList.add(user); // Add to cache
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    @Override
    public User getUser(String username) {
        // First check in list
        for (User user : usersList) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        String query = "SELECT * FROM users WHERE userName = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("userName"),
                        rs.getString("passfield"),
                        rs.getString("Email"),
                        rs.getString("Address")
                );
                usersList.add(user); // Add to list
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public ArrayList<User> getAllUsers() {
		 ArrayList<User> users = new ArrayList<>();
	        String query = "SELECT * FROM users";
	        try (Connection conn = DBConfig.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                User user = new User(
	                		rs.getString("userName"),
	                        rs.getString("passfield"),
	                        rs.getString("Email"),
	                        rs.getString("Address")
	                );
	                users.add(user);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return users;
	}
}
