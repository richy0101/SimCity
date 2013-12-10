package restaurant;

import market.interfaces.MarketWorker;

public interface CookInterface {
	public void msgMarketDeliveringOrder(int supply, String choice);

	public void msgInventoryOut(MarketWorker market, String choice);

	public void msgCanFillOrder(MarketWorker market, String choice);
}
