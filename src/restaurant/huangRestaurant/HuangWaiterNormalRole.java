package restaurant.huangRestaurant;

import gui.Building;
import gui.Gui;

import java.util.List;

import restaurant.huangRestaurant.HuangWaiterRole.WaiterState;
import restaurant.huangRestaurant.gui.WaiterGui;
import city.helpers.Directory;

/**
 * Restaurant Waiter Agent
 */
public class HuangWaiterNormalRole extends HuangWaiterRole {

	public HuangWaiterNormalRole(String location) {
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
		gui.DoGoToCook(choice);
		atCook.acquireUninterruptibly();
		cook.msgHereIsOrder(this, choice, table);
		gui.DoLeaveCustomer();
	}
}