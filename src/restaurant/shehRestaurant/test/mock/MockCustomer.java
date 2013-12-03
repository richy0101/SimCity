package restaurant.test.mock;

import restaurant.gui.Bill;
//import restaurant.CashierAgent.OrderBillState;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Customer;

public class MockCustomer extends Mock implements Customer {
	public Cashier cashier;

	public MockCustomer(String name) {
		super(name);

	}
	
	public EventLog log = new EventLog();

	public void msgHereIsYourBill(Bill bill, Cashier cashier) {
		log.add(new LoggedEvent("Received msgHereIsYourBill from cashier. Total = "+ bill.m));
		

		cashier.msgHereToPay(this, bill.m);
	}

	@Override
	public void msgHereIsYourChange(Bill b) {
		log.add(new LoggedEvent("Received msgHereIsYourChange from cashier. Change = " + b.m));
	}
}
