package restaurant.huangRestaurant;

/**
 * Restaurant Waiter Agent
 */
public class HuangWaiterNormalRole extends HuangWaiterRole {

	public HuangWaiterNormalRole(String name, HuangHostAgent host,
			HuangCookRole cook) {
		super(name, host, cook);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void deliverOrderToCook(HuangWaiterRole w, int table, String choice){
		gui.DoGoToCook(choice);
		atCook.acquireUninterruptibly();
		cook.msgHereIsOrder(this, choice, table);
		gui.DoLeaveCustomer();
	}
}