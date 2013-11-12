package stackRestaurant.test.mock;

import stackRestaurant.interfaces.Cook;
import stackRestaurant.interfaces.Market;
import stackRestaurant.interfaces.Waiter;

public class MockCook extends Mock implements Cook {
	

	public MockCook(String name) {
		super(name);
	}
	
	public void msgCookOrder(Waiter waiter, String choice, int table, int seat) {
		
	}
	
	public void msgInventoryOut(Market market, String choice) {
		
	}
	
	public void msgMarketDeliveringOrder(int inventory, String choice) {
		
	}
	
	public void msgAddMarket(Market market) {
		
	}
	
	public void msgAtCooktop() {
		
	}

	public void msgAtPlating() {
		
	}
	
	public void msgAtFridge() {
		
	}

}
