package restaurant.stackRestaurant.test.mock;

import restaurant.Restaurant;
import restaurant.stackRestaurant.interfaces.Cook;
import market.interfaces.Market;
import restaurant.stackRestaurant.interfaces.Waiter;

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

	@Override
	public void msgCanFillOrder(Market market, String choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRestaurant(Restaurant restaurant) {
		// TODO Auto-generated method stub
		
	}

}
