package restaurant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import restaurant.stackRestaurant.ProducerConsumerMonitor;

public class Restaurant {
	
	private boolean isOpen = true;
	private Map<String, FoodData> foodInventory = Collections.synchronizedMap(new HashMap<String, FoodData>());

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
	
	public void msgChangeOpen() {

	}
	
	public Map<String, FoodData> getFoodInventory() {
		return foodInventory;
	}
	
	private class FoodData {
		int quantity;
		
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	}

}
