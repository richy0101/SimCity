package market.interfaces;

import java.util.Map;

import restaurant.stackRestaurant.interfaces.Cashier;
import restaurant.stackRestaurant.interfaces.Cook;

public interface Market {
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList);
	
	public void msgHereIsMoney(MarketCustomer customer, double money);
	
	public void msgCantAffordGroceries(MarketCustomer customer);
	
	public void msgOrderFood(Cook cook, Cashier cashier, String choice);
	
	public void msgPayForOrder(Cashier cashier, double funds);
}
