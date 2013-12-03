package restaurant.test.mock;


import java.util.Vector;

import restaurant.CashierAgent;
import restaurant.gui.Bill;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Cook;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Market;
import restaurant.interfaces.Waiter;

public class MockMarket extends Mock implements Market {
	
	public MockMarket(String name) {
		super(name);
	}
	
	public EventLog log = new EventLog();
	public CashierAgent cashier;

	@Override
	public void msgHereIsPayment(Bill b) {
		log.add(new LoggedEvent("Received msgHereIsPayment from cashier. Payment = " + b.m));
		
	}

	@Override
	public void msgOrderForReplenishment(Vector<String> lowItems,
			Cook cookAgent, Cashier cashier) {
		//not related to cashier test
		
	}

}
