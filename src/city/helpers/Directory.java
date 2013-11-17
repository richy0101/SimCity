package city.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;

public class Directory {
	public static final Directory sharedInstance = new Directory();
	
	Directory() {
		restaurants.add(stackRestaurant);
		
	}
	
	public static Directory sharedInstance() {
		return sharedInstance;
	}
	
//RESTAURANTS	
	private Restaurant stackRestaurant = new StackRestaurant("StackRestaurant");
	Coordinates stackRestaurantLocation = new Coordinates(0,0);
	
	Map<Restaurant, Coordinates> restaurantDirectory = new HashMap<Restaurant, Coordinates>(); {
		restaurantDirectory.put(stackRestaurant, stackRestaurantLocation);	
	}
	
	public List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
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