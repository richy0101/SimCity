package city.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;
import restaurant.stackRestaurant.helpers.Menu;

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
	
//RESTAURANTS	
	private Restaurant stackRestaurant = new StackRestaurant("StackRestaurant");
	Coordinate stackRestaurantLocation = new Coordinate(0,0);
	
	private static Map<Restaurant, Coordinate> restaurantDirectory = new HashMap<Restaurant, Coordinate>(); {
		restaurantDirectory.put(stackRestaurant, stackRestaurantLocation);	
	}
	
	
	
	public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public Map<Restaurant, Coordinate> getDirectory() {
		return restaurantDirectory;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	
//HOUSES
	
//MARKETS
	
//BANKS
}

class Coordinate {
	public int xCoordinate;
	public int yCoordinate;
	
	public Coordinate(int x, int y) {
		this.xCoordinate = x;
		this.yCoordinate = y;
	}
}