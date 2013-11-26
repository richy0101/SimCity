package market;

import city.PersonAgent;
import market.MarketRole;

public class Market {

	private String name;
	private MarketRole worker;

	
	public Market() {
		
	}
	
	public Market(String buildingName) {
		name = buildingName;	

		
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

}