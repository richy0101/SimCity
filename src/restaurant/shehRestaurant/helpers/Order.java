package restaurant.shehRestaurant.helpers;

//import restaurant.WaiterAgent.OrderState;
import java.util.Vector;

import restaurant.shehRestaurant.ShehWaiterRole;
import restaurant.shehRestaurant.ShehCustomerRole;
//import restaurant.gui.Order.OrderBillState;
import restaurant.shehRestaurant.interfaces.Customer;

public class Order {
	public ShehWaiterRole w;
	public Customer c;
	public String o;
	public Vector<String> list;
	public int t;
	public OrderCookState cs;
	public OrderMarketState ms;

	public Order(ShehWaiterRole waiter, String choice, int table, OrderCookState state) {
		w = waiter;
		o = choice;
		t = table;
		cs = state;
	}
	
	public Order(String choice, OrderCookState state) {
		o = choice;
		cs = state;
	}
	
	public Order(String choice, OrderMarketState state) {
		o = choice;
		ms = state;
	}

	public Order(Vector<String> orders, OrderMarketState state) {
		list = orders;
		ms = state;
	}
	
	public enum OrderCookState
	{Pending, Nothing, Ordering, Done};
	
	public enum OrderMarketState
	{Pending, Replenishing, Done};	
}