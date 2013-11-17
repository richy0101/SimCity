package market.interfaces;

import java.util.Map;

public interface Market {
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList);
	
	public void msgHereIsMoney(MarketCustomer customer, double money);
	
	public void msgCantAffordGroceries(MarketCustomer customer);
}
