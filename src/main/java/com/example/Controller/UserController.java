package com.example.OnlineShoppingSystem;
import java.util.*;
import com.example.Service.*;
import com.example.Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class UserController {
	static CartService cartService = new CartService();
	static ProductService productService = new ProductService();
	static WishListService wishListService = new WishListService();
	static OrderService orderService = new OrderService();
	static RatingService ratingService = new RatingService();
    private static final Logger logger = LogManager.getLogger(UserController.class);
	private UserController() {}
	public static void userMenu() {
	
	  Scanner sc = new Scanner(System.in);
	  logger.info("-------------------------------------------------------------------------------");
	  
	  logger.info("Select option:\n1. Add to Cart\n2. Add to Wishlist\n3. Search By Category\n4. View WishList\n5. View Cart\n6. View Order History\n7. View Product Rating\n8. LogOut");

      int option = sc.nextInt();
      switch (option) {
      case 1:
          addToCart();
          MainController.browseProducts();
          userMenu();
          break;
      case 2:
          addToWishList();
          MainController.browseProducts();
          userMenu();
          break;
      case 3:
          MainController.searchByCategory();
          userMenu();
          break;
      case 4:
          ViewWishList();
          userMenu();
          break;
      case 5:
    	  viewCart();
    	  MainController.cartoptions();
    	  userMenu();
          break;
      case 6:
    	  viewOrderHistory();
    	  MainController.browseProducts();
    	  userMenu();
          break;
      case 7:
      	  viewRating();
      	  MainController.browseProducts();
          userMenu();
          break;  
      case 8:
      	  MainController.mainoptions();
          break;  
      default:
          logger.info("Invalid option.");
  }   
      }
	
	
	private static void viewRating() {
		Scanner sc = new Scanner(System.in);
		logger.info("Enter the product name");
		String p = sc.nextLine();
		ArrayList<Rating> ratingList = ratingService.getRating(p);
        if (ratingList.isEmpty()) {
            logger.info("No ratings found");
        } else {
            for (Rating r : ratingList) {
                logger.info( "Username : "+ r.getUsername()+ "  Ratings :  " + r.getRating()+ "  Reviews : " +r.getReview());
            }
        }
		
	}


	private static void viewOrderHistory() {
		String s = MainController.currentuser;
		ArrayList<Order> orders = orderService.getOrdersByUserName(s);
		ArrayList<Rating> ratings = ratingService.getAllRating();
        if (orders.isEmpty()) {
            logger.info("No orders found.");
        } else {
            for (Order order : orders) {
                boolean rated = false;
                logger.info("Order ID: " + order.getOrderID() + ", Product Name: " + order.getProductName() + ", Quantity: " + order.getQuantity() + ", Price: " + order.getPrice() + ", Status: " + order.getStatus());
                if(order.getStatus().equals("Order Delivered")) {
                	for(Rating r : ratings) {
                	if(order.getProductName().equals(r.getProductname()) && s.equals(r.getUsername())) {
                		logger.info("You have already given the ratings.. thanks");
               		rated = true;
                		break;
                	}
                	}
                	if(!rated) {
                		Scanner sc = new Scanner(System.in);
        		        logger.info("Rate the product outof 5:" + order.getProductName());
        		        int rate = sc.nextInt();
        		        logger.info("Your reviews");
        		        String review = sc.next();
        		        Rating rating = new Rating(0, order.getUserName(), order.getProductName(), rate, review);
        		        ratingService.addRating(rating);
        		        logger.info("Thanks for giving Rating");	
                	
                   }
                }
            }
        }
		
	}

	private static void addToWishList() {
	    MainController.browseProducts();
	    Scanner sc = new Scanner(System.in);
	    
	    logger.info("Enter the product name to add:");
	    String productName = sc.nextLine();
	    
	    ArrayList<Product> products = productService.getAllProducts();
	    ArrayList<WishList> wishList = wishListService.getWishList(MainController.currentuser);

	    boolean productExists = false;
	    boolean alreadyInWishlist = false;

	    // Check if the product exists
	    for (Product p : products) {
	        if (p.getName().equals(productName)) {
	            productExists = true;
	            break;
	        }
	    }

	    if (productExists) {
	        // Check if the product is already in the wishlist
	        for (WishList w : wishList) {
	            if (w.getProductName().equals(productName)) {
	                alreadyInWishlist = true;
	                break;
	            }
	        }

	        if (alreadyInWishlist) {
	            logger.info("Product already exists in the wishlist.");
	        } else {
	            WishList newWishList = new WishList(MainController.currentuser, productName);
	            wishListService.addToWishList(newWishList);
	            logger.info("Product added to Wish List.");
	        }
	    } else {
	        logger.info("Product not found.");
	    }
	}


	 static void addToCart() {
		Scanner sc = new Scanner(System.in);
		logger.info("Enter the product name to add");
        String productName = sc.nextLine();
        ArrayList<Product> products = productService.getAllProducts();
        boolean productfound=false;
       
        for(Product p : products) {
            if(p.getName().equals(productName)) {
            	productfound = true;
            	
                    logger.info("Enter quantity:");
                    int quantity = sc.nextInt();
                    if(p.getQuantity()<quantity && p.getQuantity()>0) {
                    	logger.info("Only " +p.getQuantity()+ " available");
                    	
                    	break;
                     }
                    if(p.getQuantity()>=quantity) {
                   Cart cartItem = new Cart(MainController.currentuser, productName, quantity);
                    cartService.addToCart(cartItem);
                    logger.info("Product added to cart.");
                    break;
                    }
        	}
        	
        }
        if(!productfound) {
        	logger.info("Product not found");
        }
	}
	
	static void viewCart() {
        List<Cart> cartItems = cartService.getCartItems(MainController.currentuser);
        if(cartItems.isEmpty()) {
        	logger.info("Cart is Empty");
        }
        else {
        logger.info("Product List:");
        double totalAmount = 0;
        for (Cart c : cartItems) {
            double itemTotal = c.getPrice() * c.getQuantity();
            totalAmount += itemTotal;
            logger.info("Product Name: " + c.getProductName() + ", Quantity: " + c.getQuantity() + ", Price: $" + c.getPrice() + ", Total: $" + itemTotal);
        }
        logger.info("Total Amount: $" +totalAmount);
       
        }
        
    }
	 static void ViewWishList() {
		 List<WishList> wishList = wishListService.getWishList(MainController.currentuser);
		 if(wishList.isEmpty()) {
			 logger.info("Add items first");
			 MainController.browseProducts();
		 }
		 else {
			 for(WishList w : wishList) {
				 logger.info("Product Name: " + w.getProductName());
			 }
			 MainController.wishListOptions();
		 }
	 
	}
}
