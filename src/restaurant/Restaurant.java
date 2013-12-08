package restaurant;

import java.util.HashMap;

import restaurant.stackRestaurant.ProducerConsumerMonitor;

public class Restaurant {
	
	private boolean isOpen = true;
	private HashMap<String, FoodData> foodInventory = new HashMap<String, FoodData>();

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
		
	}
	
	public void msgChangeOpen() {

	}
	
	public HashMap<String, FoodData> getFoodInventory() {
		return foodInventory;
	}
	
	private class FoodData {
		
	}

}
