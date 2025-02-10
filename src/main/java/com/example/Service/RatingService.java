package com.example.Service;

import java.util.ArrayList;

import com.example.DAO.*;
import com.example.DAOImpl.*;
import com.example.Model.*;

public class RatingService {
	private RatingDAO ratingDAO = new RatingDAOImpl();
	public void addRating(Rating rating) {
        ratingDAO.addRating(rating);
    }
	public ArrayList<Rating> getRating(String p) {
		return ratingDAO.getRating(p);
	}
	public ArrayList<Rating> getAllRating() {
		return ratingDAO.getAllRating();
	}
}
