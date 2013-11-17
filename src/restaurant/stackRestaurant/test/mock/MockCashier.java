package restaurant.stackRestaurant.test.mock;

import restaurant.stackRestaurant.helpers.Check;

import restaurant.stackRestaurant.interfaces.Cashier;
import restaurant.stackRestaurant.interfaces.Customer;
import restaurant.stackRestaurant.interfaces.Waiter;
import restaurant.stackRestaurant.interfaces.Market;

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