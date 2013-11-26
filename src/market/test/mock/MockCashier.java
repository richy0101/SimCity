package market.test.mock;

import restaurant.stackRestaurant.helpers.Check;
import restaurant.stackRestaurant.interfaces.Cashier;
import restaurant.stackRestaurant.interfaces.Customer;
import restaurant.stackRestaurant.interfaces.Waiter;
import market.interfaces.Market;

public class MockCashier extends Mock implements Cashier {

	public EventLog log;
	public Check check;
	public Market market;

	public MockCashier(String name) {
		super(name);
		log = new EventLog();
	}
	
	public void msgComputeCheck(Waiter waiter, Customer cust, String choice) {
		
	}
	
	public void msgPayCheck(Customer cust, Check check, double money) {
		
	}

	public void msgGiveBill(Check check, Market market) {
		log.add(new LoggedEvent("Received msgGiveBill from Market"));
		
		this.check = check;
		this.market = market;		
	}

}
