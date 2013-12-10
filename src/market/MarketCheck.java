package market;

import market.interfaces.Market;

public class MarketCheck {
	private double cost;
	private String choice;
	private int amount;
	private Market market;


	public MarketCheck(double cost, String choice, int amount, Market market) {
		this.cost = cost;
		this.choice = choice;
		this.amount = amount;
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
	
	public int getAmount() {
		return amount;
	}
}
