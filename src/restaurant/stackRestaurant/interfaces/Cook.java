package restaurant.stackRestaurant.interfaces;


public interface Cook {
	
	public void msgCookOrder(Waiter waiter, String choice, int table, int seat);
	
	public void msgInventoryOut(Market market, String choice);
	
	public void msgMarketDeliveringOrder(int inventory, String choice);
	
	public void msgAddMarket(Market market);
	
	public void msgAtCooktop();

	public void msgAtPlating();
	
	public void msgAtFridge();

}
