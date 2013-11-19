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
	private Restaurant stackRestaurant = new StackRestaurant("StackRestaurant"); //restaurant 1
	Coordinate stackRestaurantLocation = new Coordinate(105,275);
	/*
	private Restaurant huangRestaurant = new HuangRestaurant("HuangRestaurant"); //restaurant 2
	Coordinate huangRestaurantLocation = new Coordinate(255,78);
	
	private Restaurant nakamuraRestaurant = new NakamuraRestaurant("NakamuraRestaurant"); //restaurant 3
	Coordinate nakamuraRestaurantLocation = new Coordinate(383,69);
	
	private Restaurant phillipsRestaurant = new PhillipsRestaurant("PhillipsRestaurant"); //restaurant 4
	Coordinate phillipsRestaurantLocation = new Coordinate(779,340);
	
	private Restaurant shehRestaurant = new ShehRestaurant("ShehRestaurant"); //restaurant 5
	Coordinate shehRestaurantLocation = new Coordinate(576,310);
	
	private Restaurant tanRestaurant = new TanRestaurant("TanRestaurant"); //restaurant 6
	Coordinate tanRestaurantLocation = new Coordinate(258,307);
	*/
	
	//HOUSES
		
	//MARKETS
		
	//BANKS
	
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
	

}
