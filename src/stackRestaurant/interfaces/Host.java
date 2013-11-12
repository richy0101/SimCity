package stackRestaurant.interfaces;

public interface Host {
	
	public void msgIWantFood(Customer cust);
	
	public void msgWaiterFree(Waiter waiter);
	
	public void msgWaiterBusy(Waiter waiter);
	
	public void msgWaiterWantsToGoOnBreak(Waiter waiter);
	
	public void msgWaiterComingOffBreak(Waiter waiter);
	
	public void msgLeavingTable(Customer cust);
	
	public void msgNotWaiting(Customer cust);
	
	public void msgAddWaiter(Waiter waiter);

}
