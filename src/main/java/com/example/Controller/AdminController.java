package com.example.OnlineShoppingSystem;

import java.util.*;
import com.example.Model.Order;
import com.example.Model.Product;
import com.example.Service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class AdminController {
	private AdminController() {}
	static ProductService productService = new ProductService();
	static OrderService orderService = new OrderService();
    private static final Logger logger = LogManager.getLogger(AdminController.class);

		   static void adminMenu() {
		        Scanner sc = new Scanner(System.in);
		        while (true) {
		            logger.info("Admin Menu:");
		            logger.info("1. Add Product");
		            logger.info("2. Update StockLevels");
		            logger.info("3. Delete Product");
		            logger.info("4. All product list");
		            logger.info("5. Approve Orders");
		            logger.info("6. Generate sales report");
		            logger.info("7. Logout");
		            
	 
		            int choice = sc.nextInt();
		            sc.nextLine(); // consume newline
	 
		            switch (choice) {
		                case 1:
		                    addProduct();
		                    break;
		                case 2:
		                	updateStockLevels();
		                    break;
		                case 3:
		                    deleteProduct();
		                    break;
		                case 4:
		                	getAllProducts();
		                    break;
		                case 5:
		                	approveProducts();
		                    break;
		                case 6:
		                	generateTotalSalesReport();
		                	break;
		                case 7:
		                    return;
		                default:
		                    logger.info("Invalid choice. Try again.");
		            }
		        }
		    }

		   public static void generateTotalSalesReport() {
			    Map<String, Double> salesReport = orderService.generateTotalSalesReport();

			    logger.info("Total Sales Report:");
			    for (Map.Entry<String, Double> entry : salesReport.entrySet()) {
			        logger.info("Product: " + entry.getKey() + ", Total Sales: " + entry.getValue());
			    }
			}


			private static void approveProducts() {
				Scanner sc=new Scanner(System.in);
				ArrayList<Order> checkouts = orderService.getAllOrders();
				if(checkouts.isEmpty()) {
					 logger.info("No orders found");
				}
			    for (Order order : checkouts) {
			    	String s = "Rejected";
			        if (order.getStatus().equals("Order Placed")) {
			            logger.info("Order ID: " + order.getOrderID() + ", Product Name: " + order.getProductName() + ", Quantity: " + order.getQuantity() + ", Price: " + order.getPrice());
			           logger.info("Approve this order? (yes/no)");
			            String response = sc.nextLine();
			            if (response.equalsIgnoreCase("yes")) {
			            	orderService.updateOrderStatus(order.getOrderID(), "Order Delivered");
			                logger.info("Order approved.");
			            } else {
			            	orderService.updateOrderStatus(order.getOrderID(), s);
			                logger.info("Order rejected.");
			            }
			        }else  if (order.getStatus().equals(s)) {
			            logger.info("Order ID: " + order.getOrderID() + ", Product Name: " + order.getProductName() + ", Quantity: " + order.getQuantity() + ", Price: " + order.getPrice());
			           logger.info(" You have rejected this order.Do you need to Approve this order? (yes/no)");
			            String response = sc.nextLine();
			            if (response.equalsIgnoreCase("yes")) {
			            	orderService.updateOrderStatus(order.getOrderID(), "Order Delivered");
			                logger.info("Order approved.");
			            } else {
			            	orderService.updateOrderStatus(order.getOrderID(), s);
			                logger.info("Order rejected.");
			            }
			        }
			   }
			
		}

			private static void getAllProducts() {
				ArrayList<Product> products = productService.getAllProducts();
		        if (products.isEmpty()) {
		            logger.info("No products found.");
		        } else {
		            for (Product product : products) {
		                logger.info(product);
		            }
		        }
			
		}

			private static void addProduct() {
		        Scanner sc = new Scanner(System.in);
		        logger.info("Enter product Category:");
		        String category = sc.nextLine();
		        
		        logger.info("Enter product name:");
		        String name = sc.nextLine();
	 
		        logger.info("Enter product description:");
		        String description = sc.nextLine();
	 
		        logger.info("Enter product price:");
		        double price = sc.nextDouble();
		        
	 
		        logger.info("Enter product quantity:");
		        int quantity = sc.nextInt();
	 
		        Product product = new Product(0,category, name, description, price, quantity);
		        productService.addProduct(product);
	 
		        logger.info("Product added successfully!");
		    }
	 
		    private static void updateStockLevels() {
		        Scanner sc = new Scanner(System.in);
		        MainController.browseProducts();
		        logger.info("Enter product Name to update:");
		        String name = sc.nextLine();
		   	 
		        logger.info("Enter new product price:");
		        double price = sc.nextDouble();
		       
	 
		        logger.info("Enter new product quantity:");
		        int quantity = sc.nextInt();
	 
		        Product product = new Product(name, price, quantity);
		        productService.updateProduct(product);
	 
		        logger.info("Product updated successfully!");
		    }
	 
		    private static void deleteProduct() {
		        Scanner sc = new Scanner(System.in);
		        MainController.browseProducts();
		        logger.info("Enter product Name to delete:");
		        String name = sc.nextLine();
		        productService.deleteProduct(name);
		        logger.info("Product deleted successfully!");
		    }
	 
		   
}
