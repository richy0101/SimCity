package home.interfaces;
import home.*;

public interface Landlord {
	public abstract void msgNeedsToPayRent(HomePersonRole person, double moneyOwed);
	public abstract void msgHereIsRent(HomePersonRole person, double money);
}