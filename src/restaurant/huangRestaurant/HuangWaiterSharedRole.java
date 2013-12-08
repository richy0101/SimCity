package restaurant.huangRestaurant;

import restaurant.stackRestaurant.Order;
import city.helpers.Directory;


/**
 * Restaurant Waiter Agent
 */
public class HuangWaiterSharedRole extends HuangWaiterRole {

	public HuangWaiterSharedRole(String name, HuangHostAgent host,
			HuangCookRole cook) {
		super(name, host, cook);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void deliverOrderToCook(HuangWaiterRole w, int table, String choice){
		//do go to stuff;
			host.msgWaiterBusy(this);
			print("Adding " + customer.customer + "'s order to shared data for cook");
			Directory.sharedInstance().getRestaurants().get(0).getMonitor().insert(new Order(this, customer.choice, customer.table, customer.seatNum));;
	}
}
