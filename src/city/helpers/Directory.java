package city.helpers;

import java.util.List;
import restaurant.Restaurant;

public class Directory {
	public static final Directory sharedInstance = new Directory();
	
	private List<Restaurant> restaurants;
	//private List<Bank> banks;
	//private List<Market> markets;
	//private List<Houses> houses;

	public static Directory sharedInstance() {
		return sharedInstance;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
		
	}
	/*
	Map<String, double, double> directory = new HashMap<String, double, double>();
		directory.put("Restaurant1", 0, 0);
	*/
}
