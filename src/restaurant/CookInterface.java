package restaurant;

import market.interfaces.Market;

public interface CookInterface {
	public void msgMarketDeliveringOrder(int supply, String choice);

	public void msgInventoryOut(Market market, String choice);

	public void msgCanFillOrder(Market market, String choice);
}
