package restaurant.shehRestaurant.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import restaurant.shehRestaurant.WaiterAgent;

public class Menu {
	
	public Object[] choices;
	public int size, lowestprice, secondlowestprice, cookTime;

	public Menu() {
		FoodData steak = new FoodData("Steak", 20, 2000, 1);
		FoodData chicken = new FoodData("Chicken", 15, 2000, 1);
		FoodData fish = new FoodData("Fish", 20, 2000, 1);
		FoodData vegetarian = new FoodData("Vegetarian", 20, 2000, 1);
		
		Map<String, FoodData> inventory = new HashMap<String, FoodData>(); {
			inventory.put("Steak", steak);
			inventory.put("Chicken", chicken);
			inventory.put("Fish", fish);
			inventory.put("Vegetarian", vegetarian);
			
		this.choices = inventory.keySet().toArray();
		this.size = inventory.size();
		this.lowestprice = 15;
		this.secondlowestprice = 20;
		/*for(int i = 0; i < inventory.keySet().size(); i++) {
			this.lowestprice = inventory.keySet().toArray();
			
		}*/
		} //change this so that lowest price is adjustable
			//requires unifying the menus
	}
}

