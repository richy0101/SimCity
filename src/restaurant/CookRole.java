package restaurant;

import java.util.List;

import market.interfaces.Market;
import agent.Role;

public abstract class CookRole extends Role implements CookInterface{

	public void msgMarketDeliveringOrder(int supply, String choice) {		
	}
	
	public void msgMarketDeliveringOrder(int supply, List<String> choices) {		
	}

	public void msgInventoryOut(Market market, String choice) {
	}
	
	public void msgInventoryOut(Market market, List<String> choices, int amount) {
	}

	public void msgCanFillOrder(Market market, String choice) {
	}

}
