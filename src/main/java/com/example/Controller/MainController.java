package com.example.OnlineShoppingSystem;
import java.util.*;
import com.example.Model.*;
import com.example.Service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class MainController {
    private static UserService userService = new UserService();
    private static WishListService wishListService = new WishListService();
    private static ProductService productService = new ProductService();
    private static CartService cartService = new CartService();
    private static OrderService orderService = new OrderService();
    private static final Logger logger = LogManager.getLogger(MainController.class.getName());
    static String currentuser;
    static  String username = "Enter username : ";
	static String password = "Enter password : ";
	static String invalidOption = "Invalid Option";
    public static void main(String[] args) {
    	mainoptions();
    }
    static void mainoptions() {
    Scanner sc = new Scanner(System.in);
    boolean b = true;
    while (b) {
        logger.info("Select option: ");
        logger.info("1. User 2. Admin 3. Browse Products 4. Exit");
        int option = sc.nextInt();
        sc.nextLine(); 
        switch (option) {
            case 1:
                handleUser();
                break;
            case 2:
                handleAdmin();
                break;
            case 3:
                browseProducts();
                mainoptions();
                break;
            case 4:
            	 logger.info("Visit Again");
                System.exit(0);
                break;
            default:
            	 logger.info(invalidOption);
                b=false;
        }
    }
    }
     private static void handleUser() {
    	Scanner sc = new Scanner(System.in);
    	 logger.info("1. Register 2. Login");
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (option == 1) {
             logger.info(username);
            String username = sc.nextLine();
             logger.info(password);
            String password = sc.nextLine();
             logger.info("Enter email:");
            String email = sc.nextLine();
             logger.info("Enter address:");
            String address = sc.nextLine();
            ArrayList<User> users = userService.getAllUsers();
             logger.info(users.size());
            if(users.isEmpty()) {
            	userService.registerUser(new User(username, password, email, address));
                 logger.info("User registered successfully.");
                 logger.info("Login to continue");
            }
            else {
            for(User u : users) {
            	if(u.getEmail().equals(email) && u.getUserName().equals(username)) {
            		 logger.info("User already exists");
            }
            	else {
            		userService.registerUser(new User(username, password, email, address));
                     logger.info("User registered successfully.");
                     logger.info("Login to continue");
                     logger.info("hiii");
                    break;
                    
            	}
            }
            }
            
        } else if (option == 2) {
             logger.info(username);
            String username = sc.nextLine();
             logger.info(password);
            String password = sc.nextLine();
            if (userService.authenticateUser(username, password)) {
                 logger.info("Login successful.");
                currentuser = username;
                browseProducts();
                UserController.userMenu();
            } else {
                 logger.info("Invalid credentials.");
            }
        } else {
             logger.info(invalidOption);
        }
    }

    private static void handleAdmin() {
    	Scanner sc = new Scanner(System.in);
         logger.info("Login");

             logger.info(username);
            String username = sc.nextLine();
             logger.info(password);
            String password = sc.nextLine();
            if(username.equals("admin")) {
            	if(password.equals("admin")) {
            		 logger.info("Login successful.");
                    AdminController.adminMenu();
            	}
            	else {
            		 logger.info("Password incorrect!");
            	}
            }  
            else {
                 logger.info("Invalid credentials.");
            }
        }
    
     static void browseProducts() {
        List<Product> products = productService.getAllProducts();
         logger.info("Product List:");
        for (Product product : products) {
             logger.info(product);
        }
    }

     static void searchByCategory() {
	        Scanner sc = new Scanner(System.in);
	         logger.info("Enter category to search:");
	        String category = sc.nextLine();
	        ArrayList<Product> products = productService.getByCategory(category);
	        if (products.isEmpty()) {
	             logger.info("No products found.");
	        } else {
	            for (Product product : products) {
	                 logger.info(product);
	              	            }
	        }
	    }
     
     static void cartoptions() {
    	 Scanner sc = new Scanner(System.in);
    	  logger.info("Select option: \n1. Check Out\n2. Delete Products from Cart\n3. Go Back");

         int option = sc.nextInt();
         sc.nextLine(); 
         switch (option) {
             case 1:
                 checkout();
                 UserController.userMenu();
                 break;
             case 2:
                 deleteProductsfromCart();
                 cartoptions();
                 break;
             case 3:
                 browseProducts();
                 UserController.userMenu();
                 break;
             default:
                  logger.info(invalidOption);
         }
     }
	private static void deleteProductsfromCart() {
		ArrayList<Cart> deleteProduct = cartService.getCartItems(currentuser);
		Scanner sc=new Scanner(System.in);
		 logger.info("Select product name to checkout:");
	    String deleteProductName = sc.nextLine();
	    boolean b = false;
	    if(deleteProduct.isEmpty()) {
	    	 logger.info("No items in the cart");
	    }
	    else {
	    for (Cart c :deleteProduct) {
	    	if(c.getProductName().equals(deleteProductName)){
	    		cartService.removeProductFromCart(currentuser, deleteProductName);
	    		 logger.info(deleteProductName ,"{} removed from the cart successfully");
	    		b = true;
	    		break;
	    	}
	    }
	    if(!b) {
	    	 logger.info("Enter valid product name");
	    }
	    }
		
		
	}
	private static void checkout() {
	    Scanner sc = new Scanner(System.in);
	     logger.info("Select product name to checkout:");
	    String checkoutProductName = sc.nextLine();
	    ArrayList<Cart> checkoutProduct = cartService.getCartItems(currentuser);
	    ArrayList<Product> products = productService.getAllProducts();
	    boolean isProductFound = false;

	    for (Cart c : checkoutProduct) {
	        if (c.getProductName().equals(checkoutProductName)) {
	             logger.info("Enter quantity:");
	            int quantity = sc.nextInt();
	            isProductFound = true;

	            for (Product p : products) {
	                if (p.getName().equals(checkoutProductName)) {
	                    if (p.getQuantity() < quantity) {
	                         logger.info("Insufficient quantity available.");
	                    } else {
	                        double price = p.getPrice() * quantity;
	                        Order checkout = new Order(currentuser, p.getName(), quantity, price, "Order Placed");
	                        orderService.addCheckout(checkout);
	                        productService.reduceProductQuantity(p.getName(), quantity);
	                         logger.info("Order Placed!");
	                        browseProducts();
	                        cartService.removeProductFromCart(currentuser, checkoutProductName);
	                    }
	                    break; // Exit the product loop as we found and processed the product
	                }
	            }

	            if (!isProductFound) {
	                 logger.info("Product not found in the product list.");
	            }
	            break; // Exit the cart loop after processing the product
	        }
	    }

	    if (!isProductFound) {
	         logger.info("Please enter a valid cart product to checkout.");
	    }
	}

	
	public static void wishListOptions() {
		 Scanner sc = new Scanner(System.in);
		  logger.info("Select option:\n1. Delete Products from WishList\n3. Go Back");

         int option = sc.nextInt();
         sc.nextLine(); 
         // Consume newline
         switch (option) {
             case 1:
                 deleteProductsfromWishList();
                 break;
             case 3:
                 browseProducts();
                 UserController.userMenu();
                 break;
             default:
                  logger.info(invalidOption);
		
	}
}
	private static void deleteProductsfromWishList() {
		ArrayList<WishList> deleteProduct = wishListService.getWishList(currentuser);
		Scanner sc=new Scanner(System.in);
		 logger.info("Select product name to delete:");
	    String deleteProductName = sc.nextLine();
	    for (WishList w :deleteProduct) {
	    	if(w.getProductName().equals(deleteProductName)){
	    		wishListService.deleteFromWishlist(currentuser, deleteProductName);
	    		 logger.info(deleteProductName +" removed from the wishlist");
	    		break;
	    		
	    	}
	    }
	    UserController.ViewWishList();
	}
}

