package market.interfaces;

import java.util.Map;

public interface MarketCustomer {
	public void msgWantGroceries(Map<String, Integer> groceries);
	
	public void msgHereIsBill(double price);
	
	public void msgHereAreYourGroceries(Map<String, Integer> groceries);
	
	public void msgCantFillOrder(Map<String, Integer> groceries);
}
