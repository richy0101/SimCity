package market.interfaces;

import java.util.Map;

import restaurant.CashierInterface;
import restaurant.CookInterface;

public interface Market {
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList);
	
	public void msgHereIsMoney(MarketCustomer customer, double money);
	
	public void msgCantAffordGroceries(MarketCustomer customer);
	
	public void msgOrderFood(CookInterface cook, CashierInterface cashier, String choice, int amount);

	public void msgOrderFood(CookInterface cook, CashierInterface cashier, String choice);
	
	public void msgPayForOrder(CashierInterface cashier, double funds);
	
	public void msgCannotPay(CashierInterface cashier, double funds);

	public void msgCancelOrder(CookInterface cook);
}
