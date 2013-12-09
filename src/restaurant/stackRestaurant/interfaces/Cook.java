package restaurant.stackRestaurant.interfaces;

import market.interfaces.*;
import restaurant.CookInterface;


public interface Cook extends CookInterface{
	
	public void msgCookOrder(Waiter waiter, String choice, int table, int seat);
	
	public void msgInventoryOut(Market market, String choice);
	
	public void msgMarketDeliveringOrder(int inventory, String choice);
	
	public void msgAddMarket(Market market);
	
	public void msgAtCooktop();

	public void msgAtPlating();
	
	public void msgAtFridge();

}
