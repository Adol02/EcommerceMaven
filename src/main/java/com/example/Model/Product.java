
package com.example.Model;

public class Product {
    private int id;
    private String category;
    private String name;
    private String description;
    private double price;
    private int quantity;
    public Product(int id,String Category, String name, String description, double price, int quantity) {
        this.id = id;
        this.category = Category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
 
    public Product(String name2, double price2, int quantity2) {
    	this.name = name2;
    	this.price = price2;
    	 this.quantity = quantity2;
	}
 
    public Product() {}
 
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
 
    @Override
    public String toString() {
        return "Product [id=" + id + ", category=" + category +", name=" + name + ", description=" + description + ", price=" + price + ", quantity=" + quantity + "]";
    }

	
}
