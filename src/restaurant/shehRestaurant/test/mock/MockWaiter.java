package restaurant.test.mock;


import restaurant.gui.Bill;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Waiter;

public class MockWaiter extends Mock implements Waiter {
	
	public MockWaiter(String name) {
		super(name);
	}
	
	public EventLog log = new EventLog();

	public void msgCollectBill(Bill bill) {
		log.add(new LoggedEvent("Received msgCollectBill from cashier."));
		
	}
}
