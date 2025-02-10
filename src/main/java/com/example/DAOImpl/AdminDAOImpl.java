package com.example.DAOImpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.DAO.AdminDAO;
import com.example.DB.DBConfig;
import com.example.Model.Admin;

public class AdminDAOImpl implements AdminDAO {
    private List<Admin> adminsList = new ArrayList<>();


    @Override
    public Admin getAdmin(String username) {
        // First check in cache
        for (Admin admin : adminsList) {
            if (admin.getAdminName().equals(username)) {
                return admin;
            }
        }
        // If not found in cache, query the database
        String query = "SELECT * FROM admins WHERE adminName = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin(
                        rs.getString("adminName"),
                        rs.getString("passfield"),
                        rs.getString("Email"),
                        rs.getString("Address")
                );
                adminsList.add(admin); // Add to cache
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
