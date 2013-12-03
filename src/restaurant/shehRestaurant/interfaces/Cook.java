package restaurant.shehRestaurant.interfaces;

import restaurant.shehRestaurant.gui.Order;

public interface Cook {

	void msgHereIsReplenishment(Order o, int shipment);

	void msgCannotFulfillOrder(String name);
	
	
	
	
}