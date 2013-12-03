package restaurant.shehRestaurant.interfaces;

import restaurant.shehRestaurant.gui.Bill;
import restaurant.shehRestaurant.test.mock.EventLog;



public interface Cashier {

	void msgProcessThisBill(String o, Customer c, Waiter w);
	
	void msgHereToPay(Customer customer, double total);

	void msgHereIsMarketBill(Bill bill, Market marketAgent);

}