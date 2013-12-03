package restaurant.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import restaurant.WaiterAgent;

public class FoodData {
	public String name;
	public int price;
	public int cookTime;
	public int quantity;
	
	public FoodData(String n, int p, int t, int q) {
		this.name = n;
		this.price = p;
		this.cookTime = t;
		this.quantity = q;
	}
}

