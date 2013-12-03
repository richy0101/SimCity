package restaurant.test.mock;

import restaurant.gui.Bill;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Market;
import restaurant.interfaces.Waiter;

public class MockCashier extends Mock implements Cashier {
	
	public MockCashier(String name) {
		super(name);
	}
	
	public EventLog log = new EventLog();

	public void msgProcessThisBill(String o, Customer c, Waiter w) {
		log.add(new LoggedEvent("Received msgProcessThisBill from waiter."));
		
	}

	public void msgHereToPay(Customer customer, double total) {
		log.add(new LoggedEvent("Received msgHereToPay from customer."));
		
	}

	@Override
	public void msgHereIsMarketBill(Bill bill, Market marketAgent) {
		log.add(new LoggedEvent("Received msgHereIsMarketBill from customer."));
		
	}
}
