package com.example.Service;

import com.example.Model.User;


import java.util.ArrayList;

import com.example.DAO.*;
import com.example.DAOImpl.*;

public class UserService  {
    private UserDAO userDao = new UserDAOImpl();

    public void registerUser(User user) {
        userDao.addUser(user);
    }
    public ArrayList<User> getAllUsers() {
        return userDao.getAllUsers();
    }
 
    public boolean authenticateUser(String username, String password) {
        User user = userDao.getUser(username);
        return user != null && user.getPassword().equals(password);
    }
    
}
