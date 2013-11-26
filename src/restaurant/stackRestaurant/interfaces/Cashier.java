package restaurant.stackRestaurant.interfaces;

import agent.Role;
import restaurant.stackRestaurant.helpers.Check;
import market.interfaces.*;

public interface Cashier {
	
	public abstract void msgComputeCheck(Waiter waiter, Customer cust, String choice);
	
	public abstract void msgPayCheck(Customer cust, Check check, double money);
	
	public abstract void msgGiveBill(Check check, Market market);
	
	public abstract void msgNeedPaycheck(Role role);

}
