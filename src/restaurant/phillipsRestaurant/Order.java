package restaurant.phillipsRestaurant;

import restaurant.phillipsRestaurant.interfaces.Waiter;

public class Order {
	
	Waiter waiter;
	String choice;
	int table;
	int seat;
	
	public Order(Waiter waiter, String choice, int table, int seat) {
			this.waiter = waiter;
			this.choice = choice;
			this.table = table;
			this.seat = seat;
	}
}

