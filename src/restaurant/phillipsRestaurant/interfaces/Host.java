package restaurant.phillipsRestaurant.interfaces;

public interface Host {

	void msgIWantFood(Customer customer);

	void msgLeavingTable(Customer c);

	void msgCanWaiterTakeBreak(Waiter waiter);

	void msgAddWaiter(Waiter waiter);

	void msgAddCook(Cook cook);
	

}
