package restaurant.stackRestaurant.test.mock;

import restaurant.stackRestaurant.interfaces.Cook;
import restaurant.stackRestaurant.interfaces.Customer;
import restaurant.stackRestaurant.interfaces.Host;
import restaurant.stackRestaurant.interfaces.Waiter;

public class MockHost extends Mock implements Host {

	public MockHost(String name) {
		super(name);
	}
	
	public void msgIWantFood(Customer cust) {
		
	}
	
	public void msgWaiterFree(Waiter waiter) {
		
	}
	
	public void msgWaiterBusy(Waiter waiter) {
		
	}
	
	public void msgWaiterWantsToGoOnBreak(Waiter waiter) {
		
	}
	
	public void msgWaiterComingOffBreak(Waiter waiter) {
		
	}
	
	public void msgLeavingTable(Customer cust) {
		
	}
	
	public void msgNotWaiting(Customer cust) {
		
	}
	
	public void msgAddWaiter(Waiter waiter) {
		
	}

	@Override
	public void msgAddCook(Cook cook) {
		// TODO Auto-generated method stub
		
	}

}
