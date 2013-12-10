package market;

import market.interfaces.MarketWorker;

public class MarketCheck {
	private double cost;
	private String choice;
	private MarketWorker market;


	public MarketCheck(double cost, String choice, MarketWorker market) {
		this.cost = cost;
		this.choice = choice;
		this.market = market;
	}


	public MarketWorker getMarket() {
		return market;
	}

	public double getCost() {
		return cost;
	}

	public String getChoice() {
		return choice;
	}
}
