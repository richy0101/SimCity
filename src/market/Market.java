package market;

import city.PersonAgent;

public class Market {

	private String name;
	
	public Market() {
		
	}
	
	public Market(String buildingName) {
		name = buildingName;
//		
//		MarketRole market = new MarketRole(buildingName);
//		PersonAgent marketPerson = new PersonAgent(market);
//		market.setPerson(marketPerson);
//		marketPerson.startThread();
//		System.out.println("market");
	}
	
	public Object getOwner() {
		return null;
	}
	
	public String getName() {
		return name;
	}

}