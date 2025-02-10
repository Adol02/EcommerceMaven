package com.example.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.DAO.ProductDAO;
import com.example.DB.DBConfig;
import com.example.Model.Product;
 
 
public class ProductDAOImpl implements ProductDAO {
    private List<Product> productList = new ArrayList<>();
    static String category = "category";
 
    public ProductDAOImpl() {
        createTableIfNotExists();
    }
 
    private void createTableIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS products (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "category VARCHAR(255)," +
                "name VARCHAR(255)," +
                "description TEXT," +
                "price DOUBLE," +
                "rating int," +
                "review TEXT," +
                "quantity INT)";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void addProduct(Product product) {
        String query = "INSERT INTO products (category, name, description, price, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
        	pstmt.setString(1, product.getCategory());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getQuantity());
            pstmt.executeUpdate();
 
            // Get the generated product ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
 
            productList.add(product); // Add to list
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString(category),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
 
    @Override
    public void updateProduct(Product product) {
        String query = "UPDATE products SET  price = ?, quantity = ? WHERE name = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, product.getPrice());
            pstmt.setInt(2, product.getQuantity());
            pstmt.setString(3, product.getName());
            pstmt.executeUpdate();
 
            // Update list
            for (Product p : productList) {
                if (p.getName().equals(product.getName())) {
                    p.setPrice(product.getPrice());
                    p.setQuantity(product.getQuantity());
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void deleteProduct(String name) {
    	// Remove from list
        productList.remove(name);
        String query = "DELETE FROM products WHERE name = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Product> getByCategory(String category) {
    	String query = "SELECT id, name, category, price, quantity FROM products WHERE category = ?";
    	ArrayList<Product> products = new ArrayList<>();
    	   try (Connection conn = DBConfig.getConnection();
    	             PreparedStatement pstmt = conn.prepareStatement(query)) {
    	            pstmt.setString(1,category);
    	            ResultSet rs = pstmt.executeQuery();
    	            while (rs.next()) {
    	                Product product = new Product(
    	                        rs.getInt("id"),
    	                        rs.getString("category"),
    	                        rs.getString("name"),
    	                        rs.getString("description"),
    	                        rs.getDouble("price"),
    	                        rs.getInt("quantity")
    	                );
    	                products.add(product);
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	        return products;
}
    @Override
    public Product getProductByName(String productName) {
        Product product = null;
            String query = "SELECT id, name, category, price, quantity FROM products WHERE name = ?";
            try (Connection conn = DBConfig.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString(category));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return product;
    }
    @Override
    public void reduceProductQuantity(String productName, int quantity) {
        String query = "UPDATE products SET quantity = quantity - ? WHERE name = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2,  productName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
}
