package city.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;

public class Directory {
	public static Directory sharedInstance;
	
	Directory() {
		restaurants.add(stackRestaurant);
		
	}
	
	public static Directory sharedInstance() {
		if(sharedInstance == null) {
    		sharedInstance = new Directory();
    	}
    	return sharedInstance;
	}
//ROLES
	public Map<String, String> roleDirectory = new HashMap<String, String>(); {
		//Bank Roles
		roleDirectory.put("bank.BankCustomerRole", "Bank");
		roleDirectory.put("bank.BankManagerRole", "Bank");
		roleDirectory.put("bank.BankTellerRole", "Bank");
		//Market Roles
		roleDirectory.put("market.MarketRole1" , "Market1");
		roleDirectory.put("market.MarketRole2", "Market2");
		//LandLord Roles
		roleDirectory.put("home.LandlordRole1", "AppartmentComplex1");
		roleDirectory.put("home.LandlordRole2", "AppartmentComplex2");
		//Restaurant Roles Stack
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");
		
		
	}
	//RESTAURANT Instantiations
	private Restaurant stackRestaurant = new StackRestaurant("StackRestaurant");
	Coordinate stackRestaurantLocation = new Coordinate(105,275);
	
	private static Map<String, Coordinate> locationDirectory = new HashMap<String, Coordinate>(); {
		//Bank
		
		//Markets
		
		//Apartments
		
		//Homes
		locationDirectory.put("House1", new Coordinate(350, 275));
		
		//Restaurants
		locationDirectory.put(stackRestaurant.getName(), stackRestaurantLocation);	
		
	}
	
	
	
	public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public Map<String, Coordinate> getDirectory() {
		return locationDirectory;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	
//HOUSES
	
//MARKETS
	
//BANKS
}
