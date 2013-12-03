package restaurant.interfaces;

import restaurant.gui.Order;

public interface Cook {

	void msgHereIsReplenishment(Order o, int shipment);

	void msgCannotFulfillOrder(String name);
	
	
	
	
}