package com.example.Model;

public class Rating {
	private int ratingid;
    private String username;
    private String productname;
    private int rating;
    private String review;
    
    public Rating(int ratingid, String username, String productname, int rating, String review) {
    	this.ratingid = ratingid;
        this.username = username;
        this.productname = productname;
        this.rating = rating;
        this.review= review;
    }

	public Rating() {
		
	}

	public int getRatingid() {
		return ratingid;
	}

	public void setRatingid(int ratingid) {
		this.ratingid = ratingid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
