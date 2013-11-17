package city.helpers;

import java.util.List;
import restaurant.Restaurant;

public class Directory {
	public static final Directory sharedInstance = new Directory();
	
	List<Restaurant> restaurants;
	//List<Bank> banks;
	//List<Market> markets;
	//List<Houses> houses;

	public static Directory sharedInstance() {
		return sharedInstance;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
		
	}
}
