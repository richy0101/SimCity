package restaurant.shehRestaurant;

import java.util.Vector;

import restaurant.Restaurant;


public class ShehRestaurant extends Restaurant {

	private String name;
	ShehHostAgent host;
	ShehCashierAgent cashier;
	double till = 10000;
	Vector<ShehWaiterRole> waiters = new Vector<ShehWaiterRole>();
	
	

	public ShehRestaurant(String name) {
		super();
		this.name = name;
		host = new ShehHostAgent("Host", waiters);
		cashier = new ShehCashierAgent("Cashier");
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
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}

}
