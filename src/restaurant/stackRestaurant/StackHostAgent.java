package restaurant.stackRestaurant;

import agent.Agent;
import restaurant.stackRestaurant.gui.WaiterGui;
import restaurant.stackRestaurant.helpers.TableList;
import restaurant.stackRestaurant.interfaces.Cook;
import restaurant.stackRestaurant.interfaces.Customer;
import restaurant.stackRestaurant.interfaces.Host;
import restaurant.stackRestaurant.interfaces.Waiter;

import java.util.*;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class StackHostAgent extends Agent implements Host {
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	public List<MyWaiter> waiters = Collections.synchronizedList(new ArrayList<MyWaiter>());
	public Collection<Table> tables;
	private TableList tableList = new TableList();
	Cook cook;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
	private boolean needToNotifyWaiters = false;
	private boolean needToNotifyNewWaiter = false;
	
	public enum CustomerState 
	{WaitingInRestaurant, NotifiedRestaurantFull, Eating, Done};
	
	public enum WaiterState
	{Idle, Busy};


	public StackHostAgent() {
		super();
		// make some tables
		tables = new ArrayList<Table>(tableList.getTables().size());
		for (int ix = 1; ix <= tableList.getTables().size(); ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public List<MyCustomer> getCustomers() {
		return customers;
	}

	public Collection<Table> getTables() {
		return tables;
	}
	
	public String getName() {
		return "Garcon";
	}
	
	// Messages

	public void msgIWantFood(Customer cust) {
		boolean doesContainCustomer = false;
		for(MyCustomer mCustomer : customers) {
			if(mCustomer.customer.equals(cust)) {
				mCustomer.state = CustomerState.WaitingInRestaurant;
				doesContainCustomer = true;
				break;
			}
		}
		if(!doesContainCustomer) {
			customers.add(new MyCustomer(cust, CustomerState.WaitingInRestaurant));
		}
		stateChanged();
	}
	
	public void msgWaiterFree(Waiter waiter) {
		for(MyWaiter mWaiter : waiters) {
			if(mWaiter.waiter.equals(waiter)) {
				mWaiter.state = WaiterState.Idle;
				stateChanged();
			}
		}
	}
	
	public void msgWaiterBusy(Waiter waiter) {
		for(MyWaiter mWaiter : waiters) {
			if(mWaiter.waiter.equals(waiter)) {
				mWaiter.state = WaiterState.Busy;
				stateChanged();
			}
		}
	}
	
	public void msgWaiterWantsToGoOnBreak(Waiter waiter) {
		for(MyWaiter mWaiter : waiters) {
			if(mWaiter.waiter.equals(waiter)) {
				mWaiter.askingForBreak = true;
				stateChanged();
			}
		}
	}
	
	public void msgWaiterComingOffBreak(Waiter waiter) {
		for(MyWaiter mWaiter : waiters) {
			if(mWaiter.waiter.equals(waiter)) {
				mWaiter.onBreak = false;
				stateChanged();
			}
		}
	}
	
	public void msgLeavingTable(Customer cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				print(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	public void msgNotWaiting(Customer cust) {
		for(MyCustomer customer : customers) {
			if(customer.customer.equals(cust)) {
				customer.state = CustomerState.Done;
			}
		}
	}
	
	public void msgAddWaiter(Waiter waiter) {
		print("adding " +  waiter);
		waiters.add(new MyWaiter(waiter, WaiterState.Idle));
		needToNotifyNewWaiter  = true;
		stateChanged();
	}
	
	public void msgAddCook(Cook cook) {
		print("adding " +  cook);
		this.cook = cook;
		needToNotifyWaiters = true;
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		if(needToNotifyWaiters) {
			notifyWaitersOfCook();
			return true;
		}
		if(needToNotifyNewWaiter && cook != null) {
			notifyWaitersOfCook();
			return true;
		}
		synchronized(waiters) {
			for(MyWaiter waiter : waiters) {
				if(waiter.askingForBreak) {
					int workingWaiters = 0;
					synchronized(waiters) {
						for(MyWaiter w : waiters) {
							if(!w.onBreak) {
								workingWaiters++;
							}
						}
					}
					if (waiters.size() > 1 && workingWaiters > 1) {
						tellWaiterAllowedOnBreak(waiter, true);
						return true;
					}
					else {
						tellWaiterAllowedOnBreak(waiter, false);
						return true;
					}
				}
			}
		}
		synchronized(customers) {
			for(MyCustomer customer : customers) {
				if(cook == null && customer.state != CustomerState.Done) {
					tellCustomerRestaurantClosed(customer);
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.WaitingInRestaurant 
						|| customer.state == CustomerState.NotifiedRestaurantFull) {
					synchronized(waiters) {
						for(MyWaiter waiter : waiters) {
							if(waiter.state == WaiterState.Idle) {
								if(!waiter.onBreak) {
									synchronized(tables) {
										for(Table table : tables) {
											if(!table.isOccupied()) {
												assignCustomerToWaiter(customer, waiter, table);
												return true;
											}
										}
									}
									if(waiters.size() > 0 && customer.state != CustomerState.NotifiedRestaurantFull) {
										customer.customer.msgRestaurantFull();
										customer.state = CustomerState.NotifiedRestaurantFull;
										return true;
									}
								}
							}
						}
					}	
				}
			}
		}
		return false;
	}

	

	

	// Actions
	private void tellCustomerRestaurantClosed(MyCustomer customer) {
		customer.customer.msgRestaurantClosed();
		customer.state = CustomerState.Done;
		
	}
	private void notifyWaitersOfCook() {
		print("we have a cook!");
		for(MyWaiter waiter : waiters) {
			waiter.waiter.msgCookHere(cook);
		}
		needToNotifyWaiters = false;
		needToNotifyNewWaiter = false;
	}
	private void assignCustomerToWaiter(MyCustomer customer, MyWaiter waiter, Table table) {
		print("assigning customer to waiter: " + waiter.waiter);
		waiter.waiter.msgSeatCustomer(customer.customer, table.tableNumber, 0);
		waiter.state = WaiterState.Busy;
		customer.state = CustomerState.Eating;
		table.setOccupant(customer.customer);
	}

	private void tellWaiterAllowedOnBreak(MyWaiter waiter, boolean canGoOnBreak) {
		if(canGoOnBreak) {
			waiter.onBreak = true;
			waiter.waiter.msgYouCanGoOnBreak(true);
		}
		else {
			waiter.waiter.msgYouCanGoOnBreak(false);
		}
		waiter.askingForBreak = false;
	}
	
	private class Table {
		Customer occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(Customer cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		Customer getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}
	
	private class MyCustomer {
		MyCustomer(Customer customer, CustomerState state) {
			this.customer = customer;
			this.state = state;
		}
		Customer customer;
		CustomerState state;
	}
	
	private class MyWaiter {
		MyWaiter(Waiter waiter, WaiterState state) {
			this.waiter = waiter;
			this.state = state;
		}
		Waiter waiter;
		WaiterState state;
		boolean askingForBreak = false;
		boolean onBreak = false;
	}
	
	public Cook getCook() {
		return cook;
	}
}

