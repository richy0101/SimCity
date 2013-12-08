package market;

import city.PersonAgent;
import market.MarketRole;

public class Market {

	private String name;
	private MarketRole worker;
	private boolean open;

	
	public Market() {
		
	}
	
	public Market(String buildingName) {
		name = buildingName;
		open = false;

		
//
//		System.out.println("market");
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
	
	public boolean getOpen() {
		return open;
	}

}