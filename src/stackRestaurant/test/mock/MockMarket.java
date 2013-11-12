package stackRestaurant.test.mock;

import stackRestaurant.interfaces.Cook;
import stackRestaurant.interfaces.Market;

public class MockMarket extends Mock implements Market {

	public MockMarket(String name) {
		super(name);
	}
	
	public void msgOrderFood(Cook cook, String choice) {
		log.add(new LoggedEvent("Ordered " + choice + " from " + cook.toString()));
	}
	
	public void msgPayForOrder(double order) {
		log.add(new LoggedEvent("Received payment for order for " + order));
	}

}
