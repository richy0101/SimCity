package restaurant.stackRestaurant.interfaces;

import restaurant.stackRestaurant.helpers.Check;

public interface Cashier {
	
	public abstract void msgComputeCheck(Waiter waiter, Customer cust, String choice);
	
	public abstract void msgPayCheck(Customer cust, Check check, double money);
	
	public abstract void msgGiveBill(Check check, Market market);

}
