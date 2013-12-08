package restaurant.huangRestaurant.interfaces;





/**
 * A sample Cashier interface built to unit test a CashierAgent.
 *
 * @author Alex Huang
 *
 */
public interface Cashier {
	public void msgHereIsCustomerDish(Waiter w, String type, int table, Customer c);
	public void msgHereIsMoney(Customer c);
	public void msgNotEnoughMoney(Customer c);
	public void msgAskForCheck(Customer c);
}