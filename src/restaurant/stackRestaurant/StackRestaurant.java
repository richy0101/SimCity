package restaurant.stackRestaurant;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;


public class StackRestaurant implements Restaurant {

	StackHostRole host;
	
	public StackRestaurant() {
		host = new StackHostRole("Garcon");
		
	}
	
	@Override
	public Object getHost() {
		// TODO Auto-generated method stub
		return host;
	}

}
