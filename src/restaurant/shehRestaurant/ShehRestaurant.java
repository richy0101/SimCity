package restaurant.shehRestaurant;

import restaurant.Restaurant;


public class ShehRestaurant extends Restaurant {

	private String name;
	ShehHostAgent host;
	ShehCashierAgent cashier;

	
	public ShehRestaurant(String name) {
		super();
		this.name = name;
		
		//FOODDATA
		
		host = new ShehHostAgent();
		cashier = new ShehCashierAgent();
		
		host.setRestaurant(this);
		cashier.setRestaurant(this);
		
		host.startThread();
		cashier.startThread();	
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public ShehHostAgent getHost() {
		return host;
	}
	
	public ShehCashierAgent getCashier() {
		return cashier;
	}
}
