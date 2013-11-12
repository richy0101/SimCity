package stackRestaurant.test.mock;

import stackRestaurant.helpers.Check;

import stackRestaurant.interfaces.Cashier;
import stackRestaurant.interfaces.Customer;
import stackRestaurant.interfaces.Waiter;
import stackRestaurant.interfaces.Market;

public class MockCashier extends Mock implements Cashier {
	

	public MockCashier(String name) {
		super(name);
	}
	
	public void msgComputeCheck(Waiter waiter, Customer cust, String choice) {
		
	}
	
	public void msgPayCheck(Customer cust, Check check, double money) {
		
	}
	
	public void msgGiveBill(Check check, Market market) {
		
	}

}
