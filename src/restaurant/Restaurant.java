package restaurant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import restaurant.stackRestaurant.ProducerConsumerMonitor;

public class Restaurant {
	
	private boolean isOpen = true;
	private Map<String, FoodInformation> foodInventory = Collections.synchronizedMap(new HashMap<String, FoodInformation>());

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
	
	public void msgChangeFoodInventory(String type, int quantity) {
		foodInventory.get(type).setQuantity(quantity);
	}
	
	public void msgSetOpen() {
		isOpen = true;
	}
	
	public void msgSetClosed() {
		isOpen = false;
	}
	
	public Map<String, FoodInformation> getFoodInventory() {
		return foodInventory;
	}

}
