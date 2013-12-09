package restaurant;

import market.interfaces.Market;
import agent.Role;

public abstract class CookRole extends Role implements CookInterface{

	public void msgMarketDeliveringOrder(int supply, String choice) {		
	}

	public void msgInventoryOut(Market market, String choice) {
	}

	public void msgCanFillOrder(Market market, String choice) {
	}

}
