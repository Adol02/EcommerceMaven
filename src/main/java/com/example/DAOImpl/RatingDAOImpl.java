package com.example.DAOImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.DAO.*;
import com.example.DB.DBConfig;
import com.example.Model.*;

public class RatingDAOImpl implements RatingDAO{
	private List<Rating> ratingList = new ArrayList<>();
    public RatingDAOImpl() {
        createTableIfNotExists();
    }
 
    private void createTableIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS rating (" +
                "ratingid INT AUTO_INCREMENT PRIMARY KEY," +
                "productname VARCHAR(255)," +
                "username VARCHAR(255)," +
                "rating int," +
                "review TEXT)";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addRating(Rating rating) {
        String query = "INSERT INTO rating (productname, username, rating, review) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
        	pstmt.setString(1, rating.getProductname());
            pstmt.setString(2, rating.getUsername());
            pstmt.setInt(3, rating.getRating());
            pstmt.setString(4, rating.getReview());
            pstmt.executeUpdate();
 
            // Get the generated rating ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                rating.setRatingid(generatedKeys.getInt(1));
            }
 
            ratingList.add(rating); // Add to list
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	@Override
	public ArrayList<Rating> getRating(String productName) {
		ArrayList<Rating> ratinglists = new ArrayList<>();
        String query = "SELECT * FROM rating WHERE productname = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Rating rating  = new Rating();
                rating.setProductname(rs.getString("productname"));
                rating.setRating(rs.getInt("rating"));
                rating.setReview(rs.getString("review"));
                rating.setUsername(rs.getString("username"));
                ratinglists.add(rating);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratinglists;
	}

	@Override
	public ArrayList<Rating> getAllRating() {
		ArrayList<Rating> rating = new ArrayList<>();
        String query = "SELECT * FROM rating";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	Rating rate = new Rating(
                        rs.getInt("ratingid"),
                        rs.getString("username"),
                        rs.getString("productname"),
                        rs.getInt("rating"),
                        rs.getString("review")
                );
                rating.add(rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rating;
	}

}
