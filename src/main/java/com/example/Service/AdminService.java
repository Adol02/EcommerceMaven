package com.example.Service;

import com.example.Model.Admin;
import com.example.DAO.*;
import com.example.DAOImpl.*;
public class AdminService {
    private AdminDAO adminDao = new AdminDAOImpl();



    public boolean authenticateAdmin(String username, String password) {
        Admin admin = adminDao.getAdmin(username);
        return admin != null && admin.getPassword().equals(password);
    }
}
