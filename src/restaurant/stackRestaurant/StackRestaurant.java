package restaurant.stackRestaurant;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;


public class StackRestaurant implements Restaurant {

	StackHostRole host;
	String name;
	
	public StackRestaurant(String name) {
		host = new StackHostRole("Garcon");
		this.name = name;
		
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Object getHost() {
		// TODO Auto-generated method stub
		return host;
	}

}
