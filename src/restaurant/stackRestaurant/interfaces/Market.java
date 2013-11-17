package restaurant.stackRestaurant.interfaces;

public interface Market {
	
	public abstract void msgOrderFood(Cook cook, String choice);
	
	public abstract void msgPayForOrder(double funds);

}
