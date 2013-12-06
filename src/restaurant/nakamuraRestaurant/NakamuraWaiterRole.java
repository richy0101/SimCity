package restaurant.nakamuraRestaurant;

import agent.Agent;
import agent.Role;
import restaurant.nakamuraRestaurant.gui.WaiterGui;
import restaurant.nakamuraRestaurant.helpers.Check;
import restaurant.nakamuraRestaurant.helpers.Menu;
import restaurant.nakamuraRestaurant.interfaces.Waiter;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Waiter Agent
 */

public class NakamuraWaiterRole extends Role implements Waiter{
	public List<Cust> MyCustomers
	= new ArrayList<Cust>();
	public List<Check> Checks = new ArrayList<Check>();

	public enum state {waiting, gettingcust, goingtoseat, seated, wanttoorder, tookorder, ordered, waitingforfood, reorder, foodready, eating, askedforcheck, waitingforcheck, gotcheck, paying, leaving, done};
	private String name;
	
	private Semaphore actionComplete = new Semaphore(0,true);

	public WaiterGui waiterGui = null;
	private NakamuraCookRole cook;
	private NakamuraHostRole host;
	private NakamuraCashierRole cashier;
	
	public enum WorkState {working, tired, waitingforbreak, goingonbreak, onbreak, backtowork};
	private WorkState status;

	public NakamuraWaiterRole(String name) {
		super();

		this.name = name;
		this.status = WorkState.working;
		print("New Waiter");
	}

	public String getWaiterName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getMyCustomers() {
		return MyCustomers;
	}
	
	public void setHost(NakamuraHostRole h) {
		host = h;
	}
	
	public void setCook(NakamuraCookRole c) {
		cook = c;
	}
	
	public void setCashier(NakamuraCashierRole c) {
		cashier = c;
	}

	// Messages
	public void msgWantToGoOnBreak() {
		print("Received msgWantToGoOnBreak");
		status = WorkState.tired;
		stateChanged();
	}
	
	public void msgNewCustomer(NakamuraCustomerRole c, int tablenum) {
		print("Received msgNewCustomer");
		MyCustomers.add(new Cust(c, tablenum));
		stateChanged();
	}
	
	public void msgHereIAm(NakamuraCustomerRole c) {
		print("Received msgHereIAm");
		for (Cust cust : MyCustomers) {
			if(cust.getCustomer() == c) {
				cust.s = state.goingtoseat;
				stateChanged();
			}
		}
	}
	
	public void msgReadyToOrder(NakamuraCustomerRole c) {
		print("Received msgReadyToOrder");
		for (Cust cust : MyCustomers) {
			if(cust.getCustomer() == c) {
				cust.s = state.wanttoorder;
				stateChanged();
			}
		}
	}
	
	public void msgHeresOrder (NakamuraCustomerRole c, String choice) {
		print("Received msgHeresOrder");
		for (Cust cust : MyCustomers) {
			if(cust.getCustomer() == c) {
				cust.choice = choice;
				cust.s = state.ordered;
			}
		}
	}
	
	public void msgOutofFood(String choice, int tableNum) {
		print("Received msgOutofFood");
		for (Cust cust : MyCustomers) {
			if(cust.getTableNumber() == tableNum) {
				cust.s = state.reorder;
			}
		}
		
	}
	
	public void msgFoodReady(String choice, int tableNum) {
		print("Received msgFoodReady");
		for (Cust cust : MyCustomers) {
			if(cust.getTableNumber() == tableNum) {
				cust.s = state.foodready;
			}
		}
	}
	
	public void msgCheckPlease(NakamuraCustomerRole c) {
		print("Received msgCheckPlease");
		for (Cust cust : MyCustomers) {
			if (cust.getCustomer() == c) {
				cust.s = state.askedforcheck;
				stateChanged();
			}
		}
	}
	
	public void msgCheckReady(Check check) {
		print("Received msgCheckReady");
		Checks.add(check);
		for (Cust cust : MyCustomers) {
			if (cust.getCustomer() == check.getCustomer()) {
				cust.s = state.gotcheck;
				stateChanged();
			}
		}
	}

	public void msgLeavingTable(NakamuraCustomerRole c) {
		print("Received msgLeavingTable");
		for (Cust cust : MyCustomers) {
			if (cust.getCustomer() == c) {
				cust.s = state.leaving;
				stateChanged();
			}
		}
	}
	
	public void msgGoOnBreak() {
		print("Received msgGoOnBreak");
		status = WorkState.goingonbreak;
		stateChanged();		
	}
	
	public void msgBreakOver() {
		print("Received msgBreakOver");
		status = WorkState.backtowork;
		stateChanged();
	}
	
	public void msgBreakDenied() {
		print("Received msgBreakDenied");
		status = WorkState.working;
		stateChanged();
	}

	public void msgActionComplete() {//from animation
		print("msgActionComplete() called");
		actionComplete.release();// = true;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		try {
			if(status == WorkState.tired) {
				status = WorkState.waitingforbreak;
				AskToBreak();
			}
			
			else if(status == WorkState.goingonbreak && MyCustomers.isEmpty()) {
				GoOnBreak();
			}
			
			else if(status == WorkState.backtowork) {
				ReturnToWork();
			}
			for(Cust cust: MyCustomers) {
				if(cust.s == state.gettingcust) {
					return false;
				}
			}
			
			for (Cust cust : MyCustomers) {
				if (cust.s == state.goingtoseat) {
					GoToSeat(cust);
				}
				stateChanged();
			}
			
			for (Cust cust : MyCustomers) {
				if (cust.s == state.waiting) {
					GetCust(cust);
					return false;
				}
				stateChanged();
			}
	
			for (Cust cust : MyCustomers) {
				if (cust.s == state.wanttoorder) {
					TakeOrder(cust);
				}
				stateChanged();
			}
	
			for (Cust cust : MyCustomers) {
				if (cust.s == state.ordered) {
					PlaceOrder(cust);
				}
				stateChanged();
			}
	
			for (Cust cust : MyCustomers) {
				if (cust.s == state.foodready) {
					DeliverFood(cust);
				}
				stateChanged();
			}
			
			for (Cust cust : MyCustomers) {
				if (cust.s == state.reorder) {
					Reorder(cust);
				}
				stateChanged();
			}
			
			for (Cust cust : MyCustomers) {
				if (cust.s == state.askedforcheck) {
					GetCheck(cust);
				}
				stateChanged();
			}
			
			for (Cust cust : MyCustomers) {
				if (cust.s == state.gotcheck) {
					DeliverCheck(cust);
				}
				stateChanged();
			}
			
			for (Cust cust : MyCustomers) {
				if (cust.s == state.leaving) {
					RemoveCust(cust);
				}
				stateChanged();
			}
		}
		catch (ConcurrentModificationException e){
			return false;			
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	private void AskToBreak() {
		host.msgGoingOnBreak(this);
		stateChanged();
	}
	
	private void GetCust (Cust customer) {
		DoGetCustomer(customer);
		customer.s = state.gettingcust;
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.c.msgFollowMe(this, new Menu());
		stateChanged();
	}
	
	private void GoToSeat (Cust customer) {
		DoSeatCustomer(customer);
		customer.s = state.seated;
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stateChanged();
	}

	private void TakeOrder (Cust customer) {
		DoGetOrder(customer);
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.c.msgGetOrder();
		customer.s = state.tookorder;
		print("Took Order");
		stateChanged();
	}
	
	private void PlaceOrder(Cust customer) {
		print("Placing order");
		DoPlaceOrder(customer);
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cook.msgCookOrder(this, customer.choice, customer.tableNumber);
		customer.s = state.waitingforfood;
		stateChanged();
	}
	private void Reorder (Cust customer) {
		print("Taking new order");
		DoGetOrder(customer);
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.c.msgReorder();
		customer.s = state.tookorder;
		stateChanged();
	}
	
	private void DeliverFood(Cust customer) {
		print("Delivering Food");
		DoPickUpOrder(customer);
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DoDeliverFood(customer);
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.s = state.eating;
		customer.c.msgEatFood();
		stateChanged();
	}
	
	private void GetCheck(Cust customer) {
		print("Getting Check from " + cashier);
		cashier.msgComputeCheck(this, customer.c, customer.choice);
		customer.s = state.waitingforcheck;
		stateChanged();
	}
	
	private void DeliverCheck(Cust customer) {
		print("Delivering Check");
		for(Check c : Checks) {
			if(c.getCustomer() == customer.c) {
				customer.c.msgHeresCheck(c);
				customer.s = state.paying;				
			}
		}
		stateChanged();
	}
	
	private void RemoveCust(Cust customer) {
		host.msgTableEmpty(customer.tableNumber);
		MyCustomers.remove(customer);
		stateChanged();
	}
	
	private void GoOnBreak() {
		print("Going on break");
		status = WorkState.onbreak;
		waiterGui.DoGoToHost();
	}
	
	private void ReturnToWork() {
		print("Going back to work");
		status = WorkState.working;
		host.msgBackToWork(this);
	}

	// The animation DoXYZ() routines
	private void DoGetCustomer(Cust customer) {
		print("Getting " + customer.c);
		waiterGui.DoGoToHost();
	}
	private void DoSeatCustomer(Cust customer) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		print("Seating " + customer.c + " at " + customer.tableNumber);
		waiterGui.DoBringToTable(customer.c, customer.tableNumber); 
	}
	private void DoGetOrder(Cust customer) {
		print("Taking " + customer.c + "'s order");
		waiterGui.DoGoToTable(customer.tableNumber);
	}
	
	private void DoPlaceOrder(Cust customer) {
		print("Taking " + customer.c + "'s order to cook");
		waiterGui.DoGoToCook();
	}
	
	private void DoPickUpOrder(Cust customer) {
		print("Getting " + customer.c + "'s order");
		waiterGui.DoGoToPlating();
	}
	
	private void DoDeliverFood(Cust customer) {
		print("Delivering " + customer.c + "'s food");
		waiterGui.DoDeliverFood(customer.tableNumber, customer.choice, cook);
	}

	//utilities

	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}

	public WaiterGui getGui() {
		return waiterGui;
	}

	private class Cust {
		NakamuraCustomerRole c;
		int tableNumber;
		String choice;
		state s;

		Cust(NakamuraCustomerRole c, int tableNumber) {
			this.c = c;
			this.tableNumber = tableNumber;
			this.choice = null;
			this.s = state.waiting;
		}
		int getTableNumber() {
			return tableNumber;
		}

		NakamuraCustomerRole getCustomer() {
			return c;
		}
	}
}

