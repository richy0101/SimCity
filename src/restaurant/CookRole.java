package restaurant;

import market.interfaces.MarketWorker;
import agent.Role;

public abstract class CookRole extends Role implements CookInterface{

	public void msgMarketDeliveringOrder(int supply, String choice) {		
	}

	public void msgInventoryOut(MarketWorker market, String choice) {
	}

	public void msgCanFillOrder(MarketWorker market, String choice) {
	}

}
