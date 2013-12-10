package restaurant;

import java.util.ArrayList;
import java.util.List;

import market.interfaces.Market;

public class FoodInformation {
	protected int quantity;
	protected String name;
	protected int cookTime;
	
	public enum FoodState
	{Empty, Ordered, Stocked, PermanentlyEmpty};
	public FoodState state;
	
	protected List<Market> markets;
	
	public FoodInformation() {
	}
	
	public FoodInformation(int cookingTime, int inventory) {
		quantity = inventory;
		cookTime = cookingTime;
		markets = new ArrayList<Market>();
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setCookingTime(int cookTime) {
		this.cookTime = cookTime;
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	public List<Market> getMarkets() {
		return markets;
	}
}
