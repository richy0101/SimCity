package market.test.mock;

import java.util.Map;

import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MockMarketRole extends Mock implements Market {

	public MarketCustomer customer;
	public EventLog log;
	public Map<String, Integer> groceries;

	public MockMarketRole(String name) {
		super(name);
		log = new EventLog();
	}
	
	@Override
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList) {
		log.add(new LoggedEvent("Received msgGetGroceries from MarketCustomer"));
		groceries = groceryList;
	}

	@Override
	public void msgHereIsMoney(MarketCustomer customer, double money) {
		log.add(new LoggedEvent("Received msgHereIsMoney from MarketCustomer. Money = $" + money));
	}

	@Override
	public void msgCantAffordGroceries(MarketCustomer customer) {
		log.add(new LoggedEvent("Received msgCantAffordGroceries from MarketCustomer"));
	}
}
