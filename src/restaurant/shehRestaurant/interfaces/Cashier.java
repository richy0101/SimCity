package restaurant.interfaces;

import restaurant.gui.Bill;
import restaurant.test.mock.EventLog;
//import restaurant.test.mock.MockCustomer;
//import restaurant.test.mock.MockWaiter;


public interface Cashier {

	void msgProcessThisBill(String o, Customer c, Waiter w);
	
	void msgHereToPay(Customer customer, double total);

	void msgHereIsMarketBill(Bill bill, Market marketAgent);

}