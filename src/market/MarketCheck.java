package market;

import market.interfaces.Market;

public class MarketCheck {
	private double cost;
	private String choice;
	private Market market;


	public MarketCheck(double cost, String choice, Market market) {
		this.cost = cost;
		this.choice = choice;
		this.market = market;
	}


	public Market getMarket() {
		return market;
	}

	public double getCost() {
		return cost;
	}

	public String getChoice() {
		return choice;
	}
}
