package restaurant.huangRestaurant;

import gui.Building;
import gui.Gui;

import java.util.List;

import restaurant.huangRestaurant.HuangWaiterRole.WaiterState;
import restaurant.huangRestaurant.gui.WaiterGui;
import restaurant.stackRestaurant.Order;
import city.helpers.Directory;


/**
 * Restaurant Waiter Agent
 */
public class HuangWaiterSharedRole extends HuangWaiterRole {

	public HuangWaiterSharedRole(String location) {
		super(location);
		host = (HuangHostAgent) Directory.sharedInstance().getAgents().get("HuangRestaurantHost");
		ca = host.getCashier();
		gui = new WaiterGui(this);
		myLocation = location;
		state = WaiterState.Arrived;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui((Gui) gui);
			}
		}
	}
	@Override
	protected void deliverOrderToCook(HuangWaiterRole w, int table, String choice){
		//do go to stuff;
//			host.msgWaiterBusy(this);
//			print("Adding " + customer.customer + "'s order to shared data for cook");
//			Directory.sharedInstance().getRestaurants().get(0).getMonitor().insert(new Order(this, customer.choice, customer.table, customer.seatNum));;
	}
}
