package market;

import java.util.List;

import market.interfaces.Market;

public class MarketCheck {
	private double cost;
	private String choice;
	private List<String> choices;
	private int amount;
	private Market market;


	public MarketCheck(double cost, String choice, int amount, Market market) {
		this.cost = cost;
		this.choice = choice;
		this.amount = amount;
		this.market = market;
	}


	public MarketCheck(double cost, List<String> choices, int amount, Market market) {
		this.cost = cost;
		this.choices = choices;
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
	
	public List<String> getChoices() {
		return choices;
	}
	
	public int getAmount() {
		return amount;
	}
}
