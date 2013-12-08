package restaurant.huangRestaurant;

import agent.Agent;
import restaurant.huangRestaurant.gui.WaiterGui;
import restaurant.huangRestaurant.interfaces.Cashier;
import restaurant.huangRestaurant.interfaces.Customer;
import restaurant.huangRestaurant.interfaces.Waiter;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Waiter Agent
 */
public class WaiterAgent extends Agent implements Waiter {
	
	public enum CustomerState{Waiting, Seated, readyToOrder, Asked, Ordered, WaitingForOrder, orderOut, orderCooked, gotFood, Eating, checkReady, checkDelivered, Leaving, toldOrderOut, waiterGettingCheck};
	private class MyCustomer {
		Customer c;
		int table;
		CustomerState state;
		String choice;
		Check cx;
		public boolean doneEating = false;
		public boolean waiterHasCheck = false;
		MyCustomer(Customer c, int table) {
			this.c = c;
			this.table = table;
			this.state = CustomerState.Waiting;
		}
	}
	public List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	

	private String name;
	Timer timer = new Timer();
	private class WaiterTimerTask extends TimerTask {
		WaiterAgent w;
		public WaiterTimerTask(WaiterAgent w) {
			this.w = w;
		}
		@Override
		public void run() {
			
		}
	}
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atCook = new Semaphore(0, true);
	private Semaphore seatNew = new Semaphore(0, true);
	private Semaphore atCashier = new Semaphore(0, true);
	private Semaphore grabbingCheck = new Semaphore(0, true);
	public WaiterGui gui;
	private HostAgent host;
	private CookAgent cook;
	private Cashier ca;
	private boolean canBeFree = false;
	private boolean wantsBreak = false;
	private boolean askedBreak = false;
	private boolean onBreak = false;
	private MyCustomer currentCustomer;

	public WaiterAgent(String name, HostAgent host, CookAgent cook) {
		this.name = name;
		this.host = host;
		this.cook = cook;
		this.ca = host.getCashier();
	}

	public String getName() {
		return name;
	}

	public List<MyCustomer> getCustomers() {
		return customers;
	}

	// Messages
	public void msgWantsBreak() {
		System.out.println(name + ": msgWantsBreak received: Waiter: I want to go on Break");
		wantsBreak = true;
		stateChanged();
	}
	public void msgGoOnBreak() {
		System.out.println(name + ": msgGoOnBreak received: Waiter: Going on break when possible");
		onBreak = true;
		stateChanged();
	}
	public void msgNoBreak() {
		System.out.println(name + ": msgNoBreak received: Waiter: Break denied.");
		wantsBreak = false;
		stateChanged();
	}
	public void msgPleaseSeatCustomer(Customer c, int table) {
		customers.add(new MyCustomer(c, table));
		System.out.println(name + ": msgPleaseSeatCustomer received: Adding new customer to Waiter");
		stateChanged();
		//System.out.println("State Changed called");
	}
	
	public void msgReadyToOrder(Customer c) {
		for (MyCustomer mc: customers) {
			if (mc.c.equals(c)) {
				mc.state = CustomerState.readyToOrder;
				System.out.println(name + ": msgReadyToOrder received: changing customer state to readyToOrder");
				stateChanged();
				break;
			}
		}
	}
	public void msgHereIsMyChoice(Customer c, String choice) {
		for (MyCustomer mc: customers) {
			if (mc.c.equals(c)) {
				mc.state = CustomerState.Ordered;
				mc.choice = choice;
				System.out.println(name + ": msgHereIsMyChoice received: Logging customer Choice");
				stateChanged();
				break;
			}
		}
	}
	public void msgOrderDone(String choice, int table) {
		for (MyCustomer mc: customers) {
			if (mc.table == table) {
				mc.state = CustomerState.orderCooked;
				System.out.println(name + ": msgOrderDone received: Setting customerstate to orderCooked");
				stateChanged();
				break;
			}
		}
		
	}
	public void msgLeavingTable(Customer c) {
		System.out.println(name + ": msgLeavingTable received: Setting customerstate to Leaving");
		for (MyCustomer mc: customers) {
			if (mc.c.equals(c)) {
				mc.state = CustomerState.Leaving;
				stateChanged();
				break;
			}
		}
		
	}
	public void msgOutOfChoice(String choice, int table) {
		for (MyCustomer mc: customers) {
			if (mc.table == table) {
				System.out.println(name + ": msgOutOfChoice recieved: Will tell customer his choice is out");
				mc.state = CustomerState.orderOut;
				stateChanged();
				break;
			}
		}	
	}
	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true;
		gui.DoLeaveCustomer();
	}
	public void msgDoneEating(Customer c) {
		synchronized(customers) {
			for (MyCustomer mc : customers) {
				if (mc.c.equals(c)) {
					System.out.println(name + ": msgDoneEating recieved: Customer is doneEating");
					mc.doneEating = true;
					stateChanged();
				}
			}
		}
	}
	public void msgCanSeatNew() {
		//print("Ready to seat new customer");
		seatNew.release();
		stateChanged();
	}
	public void msgAtCook() {
		atCook.release();
		stateChanged();
	}
	public void msgAtCashier() {
		atCashier.release();
		stateChanged();
	}
	public void msgGetCheck(Check cx) {
		for (MyCustomer mc: customers) {
			if (mc.c.equals(cx.c)) {
				System.out.println(name + ": msgGetCheck recieved: customer state set to checkReady");
				mc.state = CustomerState.checkReady;
				stateChanged();
				break;
			}
		}
		//host.getCashier().msgComingForCheck(cx);
	}
	public void msgHereIsCheck(Check cx) {
		grabbingCheck.release();
		for (MyCustomer mc: customers) {
			if (mc.c.equals(cx.c)) {
				System.out.println(name + ": msgHereIsCheck recieved: Waiter has the check for Customer" + cx.c.toString());
				mc.waiterHasCheck = true;
				mc.cx = cx;
				stateChanged();
				break;
			}
		}
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		if (onBreak == true && customers.isEmpty()) {
			tellHostOnBreak();
			breakTimer(this);
			return false;
		}
		if (wantsBreak == false && askedBreak == true) {
			enableGuiBreak();
			askedBreak = false;
		}
		if (wantsBreak == true && askedBreak == false) {
			tellHostWantBreak();
			askedBreak = true; 
		}
		if(!customers.isEmpty()) {
			for (MyCustomer mc : customers) {
				if(mc.state.equals(CustomerState.Leaving)) {
					freeTable(mc);
					canBeFree = true;
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc : customers) {
				if(mc.state.equals(CustomerState.gotFood)) {
					mc.state = CustomerState.Eating;
					deliverOrderToCashier(mc);
					tellHostBusy();
					canBeFree = true;
					return true;
				}
			}
		}
		if (currentCustomer != null) {
			if (currentCustomer.state.equals(CustomerState.Asked)) {
				canBeFree = true;
				return true;
			}
		}
		synchronized(customers) {
			for (MyCustomer mc: customers) {
				if(mc.state.equals(CustomerState.Ordered)) {
					tellHostBusy();
					sendOrderToCook(this, mc.table, mc.choice);
					mc.state = CustomerState.WaitingForOrder;//customer has ordered and is waiting now. No rule to address these customers
					canBeFree = true;
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc: customers) {
				if(mc.state.equals(CustomerState.orderOut)) {
					tellHostBusy();
					mc.state = CustomerState.toldOrderOut;
					tellCustomerChoiceBad(mc);
					canBeFree = true;
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc: customers) {
				if(mc.state.equals(CustomerState.Waiting)) {
					gui.DoGoSeatNew();
					seatNew.acquireUninterruptibly();
					seatCustomer(mc);
					tellHostBusy();
					canBeFree = true;
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc : customers) {
				if(mc.state.equals(CustomerState.readyToOrder)) {
					takeOrder(mc);
					currentCustomer = mc;
					currentCustomer.state = CustomerState.Asked;
					tellHostBusy();
					canBeFree = true;
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc : customers) {
				if(mc.doneEating == true && mc.waiterHasCheck == true && !mc.state.equals(CustomerState.checkDelivered)) {
					System.out.println("Waiter is Delivering Check to customer");
					deliverCheckToCustomer(mc);
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc: customers) {
				if (mc.state.equals(CustomerState.checkReady)) {
					getCheckFromCashier(mc);
					return true;
				}
			}
		}
		synchronized(customers) {
			for (MyCustomer mc : customers) {
				if(mc.state.equals(CustomerState.orderCooked)) {
					currentCustomer = mc;
					deliverOrder(mc);
					tellHostBusy();
					canBeFree = true;
					return true;
				}
			}
		}	
		if(canBeFree == true && onBreak == false) {
			tellHostFree();
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	

	// Actions
	private void enableGuiBreak() {
		gui.endBreakSequence();
	}
	private void breakTimer(WaiterAgent waiter) {
		onBreak = false; //Break started
		timer.schedule(new WaiterTimerTask(waiter) {
			public void run() {
				this.w.tellHostBack();
			}
		},
		50000);//time for Break
	}
	private void freeTable(MyCustomer mc) {
		host.msgTableIsFree(mc.table);
		System.out.println(name + ": msgLeavingTable received: Removing customer");
		gui.DoCleanTable(mc.table);
		customers.remove(mc);
	}
	private void tellCustomerChoiceBad(MyCustomer mc) {
		gui.DoGoToCustomer(mc.table);
		atTable.acquireUninterruptibly();
		mc.c.msgOutOfChoice();
	}
	private void deliverOrder(MyCustomer mc) {
		gui.DoGoToCook();
		atCook.acquireUninterruptibly();
		cook.gui.DoRemovePlatedDish(mc.table);
		gui.drawOrder(mc.choice);
		gui.DoGoToCustomer(mc.table);
		atTable.acquireUninterruptibly();
		mc.state = CustomerState.gotFood;
		mc.c.msgHereIsYourFood();
	}
	private void deliverOrderToCashier(MyCustomer mc) {
		//gui.DoGoToCashier();
		//atCashier.acquireUninterruptibly();
		ca.msgHereIsCustomerDish(this, mc.choice, mc.table, mc.c);
	}
	private void deliverCheckToCustomer(MyCustomer mc) {
		gui.DoGoToCustomer(mc.table);
		atTable.acquireUninterruptibly();
		mc.state = CustomerState.checkDelivered;
		mc.c.msgHereIsYourCheck(host.getCashier(), mc.cx);
	}
	private void getCheckFromCashier(MyCustomer mc) {
		mc.state = CustomerState.waiterGettingCheck;
		gui.DoGoToCashier();
		atCashier.acquireUninterruptibly();
		ca.msgAskForCheck(mc.c);
		grabbingCheck.acquireUninterruptibly();
	}
	private void seatCustomer(MyCustomer mc) {
		mc.c.msgFollowMe(this, new Menu(), mc.table);
		DoSeatCustomer(mc.c, mc.table);
		mc.state = CustomerState.Seated;
		
	}
	private void takeOrder(MyCustomer mc) {
		System.out.println(name + ": Taking Order of Table: " + mc.table);
		gui.DoGoToCustomer(mc.table);
		atTable.acquireUninterruptibly();
		mc.c.msgWhatDoYouWant();
		mc.state = CustomerState.Asked;
	}
	private void sendOrderToCook(WaiterAgent w, int table, String choice){
		gui.DoGoToCook(choice);
		atCook.acquireUninterruptibly();
		cook.msgHereIsOrder(this, choice, table);
		gui.DoLeaveCustomer();
	}
	private void tellHostFree() {
		canBeFree = false;
		host.msgIamFree(this);
	}
	private void tellHostBusy() {
		host.msgIamBusy(this);
	}
	private void tellHostWantBreak() {
		host.msgIWantBreak(this);
	}
	private void tellHostOnBreak() {
		host.msgOnBreak(this);
	}
	private void tellHostBack() {
		host.msgBackFromBreak(this);
		wantsBreak = false;
		onBreak = false;
		askedBreak = false;
		enableGuiBreak();
		stateChanged();
	}
	// The animation DoXYZ() routines
	private void DoSeatCustomer(Customer customer, int table) {
		print(name + ": Seating " + customer + " at " + table);
		gui.DoBringToTable(customer, table); 
		atTable.acquireUninterruptibly();
	}

	//utilities

	public void setGui(WaiterGui gui) {
		//System.out.println("GUI SET");
		this.gui = gui;
	}

	public WaiterGui getGui() {
		return gui;
	}
}

