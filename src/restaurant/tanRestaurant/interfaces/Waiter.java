package restaurant.tanRestaurant.interfaces;
import restaurant.tanRestaurant.TanCashierAgent.Bill;
import restaurant.tanRestaurant.test.mock.EventLog;

public interface Waiter {

	public EventLog log = new EventLog();
	
	public abstract void msgHereIsBill(Bill b);
}
