package restaurant.nakamuraRestaurant;

import restaurant.Restaurant;


public class NakamuraRestaurant extends Restaurant {

	private String name;
	NakamuraHostAgent host;
	NakamuraCashierAgent cashier;
	double till = 10000;	

	public NakamuraRestaurant(String name) {
		super();
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
