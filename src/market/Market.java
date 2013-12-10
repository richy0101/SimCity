package market;

import city.PersonAgent;
import market.MarketRole;

public class Market {

	private String name;
	private MarketRole worker;
	private boolean open;
	double till;

	
	public Market() {
		
	}
	
	public Market(String buildingName) {
		name = buildingName;
		open = false;
		till = 1000;
		
//
//		System.out.println("market");
	}
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}
	
	public MarketRole getWorker() {
		return worker;
	}
	
	public void setWorker(MarketRole m) {
		worker = m;
	}
	
	public String getName() {
		return name;
	}
	
	public void setOpen() {
		open = true;
	}
	
	public void setClosed() {
		open = false;
	}
	
	public boolean isOpen() {
		return open;
	}

}