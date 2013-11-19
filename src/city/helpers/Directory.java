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
	Coordinates stackRestaurantLocation = new Coordinates(0,0);
	
	Map<Restaurant, Coordinates> restaurantDirectory = new HashMap<Restaurant, Coordinates>(); {
		restaurantDirectory.put(stackRestaurant, stackRestaurantLocation);	
	}
	
	public List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public Map<Restaurant, Coordinates> getDirectory() {
		return restaurantDirectory;
	}
	
//HOUSES
	
//MARKETS
	
//BANKS
}

class Coordinates {
	public int xCoordinates;
	public int yCoordinates;
	
	public Coordinates(int x, int y) {
		this.xCoordinates = x;
		this.yCoordinates = y;
	}
}