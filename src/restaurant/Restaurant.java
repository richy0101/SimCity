package restaurant;

import restaurant.stackRestaurant.ProducerConsumerMonitor;

public class Restaurant {

	private ProducerConsumerMonitor monitor;
	public Restaurant() {
		monitor = new ProducerConsumerMonitor();
	}
	
	public Object getHost() {
		return null;
	}
	
	public Object getCashier() {
		return null;
	}
	
	public String getName() {
		return "";
	}
	
	public ProducerConsumerMonitor getMonitor() {
		return monitor;
	}

}
