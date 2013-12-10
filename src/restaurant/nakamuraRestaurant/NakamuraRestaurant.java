package restaurant.nakamuraRestaurant;

import restaurant.FoodInformation;
import restaurant.Restaurant;


public class NakamuraRestaurant extends Restaurant {

	private String name;
	NakamuraHostAgent host;
	NakamuraCashierAgent cashier;
	double till = 10000;	

	public NakamuraRestaurant(String name) {
		super();

		FoodInformation steak = new FoodInformation(6000, 100);
		getFoodInventory().put("Steak", steak);
		
		FoodInformation chicken = new FoodInformation(4000, 100);
		getFoodInventory().put("Chicken", chicken);
		
		FoodInformation salad = new FoodInformation(7000, 100);
		getFoodInventory().put("Salad", salad);
		
		FoodInformation pizza = new FoodInformation(12000, 100);
		getFoodInventory().put("Pizza", pizza);
		
		this.name = name;
		cashier = new NakamuraCashierAgent("NakamuraRestaurant Cashier");
		cashier.startThread();
		host = new NakamuraHostAgent("NakamuraRestaurant Host");
		host.startThread();
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public NakamuraHostAgent getHost() {
		return host;
	}
	
	public NakamuraCashierAgent getCashier() {
		return cashier;
	}
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}

}
