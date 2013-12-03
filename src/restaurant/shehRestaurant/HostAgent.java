package restaurant;

import agent.Agent;
import restaurant.gui.HostGui;
import restaurant.gui.Table;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Host Agent
 */  

public class HostAgent extends Agent {
	static final int NTABLES = 3;
	public List<CustomerAgent> waitingCustomers = Collections.synchronizedList(new ArrayList<CustomerAgent>());
	public List<WaiterAgent> waiters;
	
	public List<myWaiter> breakWaiters = Collections.synchronizedList(new ArrayList<myWaiter>());
	private List<myCustomer> customers = Collections.synchronizedList(new ArrayList<myCustomer>());
	
	public ArrayList<Table> tables;

	private String name;
	private Semaphore atTable = new Semaphore(0,true);

	public HostGui hostGui = null;
	
	private int counter = 0;
	private int availableTables = NTABLES;
	private int numOfCustomers = 0, numOfWaiters = 0;

	public HostAgent(String name, Vector<WaiterAgent> w) {
		super();

		this.name = name;
		waiters = w;
		
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));
		}
	}
	
	public class myCustomer {
		CustomerAgent c;
		CustomerState s;

		public myCustomer(CustomerAgent customer, CustomerState state) {
			c = customer;
			s = state;
		}
	}
	
	public class myWaiter {
		WaiterAgent w;
		WaiterState s;
		
		public myWaiter(WaiterAgent waiter, WaiterState state) {
			w = waiter;
			s = state;
		}
	}

	public enum CustomerState
	{Waiting, Thinking, Leaving};
	
	public enum WaiterState
	{Waiting, WantBreak, OnBreak, Gone, Returned};

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}
	
	// Messages
	public void msgIWantFood(CustomerAgent cust) {
		waitingCustomers.add(cust);
		stateChanged();
		//numOfCustomers++;
	}
	
	public void msgIWantToGoOnBreak(WaiterAgent waiter) {
		print("Let's me check if you can go on break right now.");
		breakWaiters.add(new myWaiter(waiter, WaiterState.WantBreak));
		stateChanged();
	}
	
	public void msgImBackFromBreak(WaiterAgent waiter) {
		print("Welcome back, I'll assign customers to you.");
		breakWaiters.remove(waiter);
		waiters.add(waiter);
	}
	
	public void msgFreeOfCustomers(WaiterAgent waiter) {
		print("You may go on break");
		for(myWaiter w : breakWaiters) {
			if(w.w == waiter) {
				w.s = WaiterState.OnBreak;
				stateChanged();
			}
		}
	
	}

	public void msgLeavingTable(CustomerAgent cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				print(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
				
				availableTables++;
				numOfCustomers--;
			}
		}
	}

	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		
		if (waiters.size() == 0) {
			noWaiters();
			//return true;
		}
		
		/*
		if (waitingCustomers.size() > 0) {
			organizeCustomers();
			//return true;
		}
		*/
		
		if(waiters.size() > 0) { //if there are waiters and customers
			if(waitingCustomers.size() >= 1) {
				organizeCustomers();
				
				if(numOfCustomers <= 3) {
					synchronized(tables) {
						for (Table table : tables) {
							if(!table.isOccupied() && !waitingCustomers.isEmpty() /*waitingCustomers.size() >= 1*/) { //there are available tables
								assignWaiterToCustomer(waitingCustomers.get(0), waiters, table); //seat them
								return true;
							}
						}
					}
				}
			}
		}
		
		synchronized(breakWaiters) {
			for(myWaiter bw : breakWaiters) {
				if(bw.s == WaiterState.WantBreak) {
					checkForBreak(bw);
					return true;
				}
			}
		}
		
		synchronized(breakWaiters) {
			for(myWaiter bw : breakWaiters) {
				if(bw.s == WaiterState.OnBreak) {
					reorganizeWaiters(bw);
					return true;
				}
			}
		}
			
		synchronized(breakWaiters) {
			for(myWaiter bw : breakWaiters) { //Wait going on Break
				if(bw.s == WaiterState.Gone)
					return true;
			}
		}
			
		synchronized(breakWaiters) {
			for(myCustomer c : customers) { //Reconsidering Staying Scenario
				if(c.s == CustomerState.Thinking) {
					return true;
				}
			}
		}
		
		return false;
	}
 
	// Actions
	private void noWaiters() {
		print("We don't have any waiters.");
	}
	
	private void organizeCustomers() { //alerts customer which location they should wai
		int queue = 0;
		int num = 0;
		num = (waitingCustomers.size() + queue -1) % 10;
		queue++;
		waitingCustomers.get(waitingCustomers.size()-1).msgThisIsYourNumber(num); //causes indexing errors
	}
	
	private void assignWaiterToCustomer(CustomerAgent customer, List<WaiterAgent> w, Table table) {
		//organizeCustomers();
		Do("Welcome, I'll assign a waiter to you.");
		int waiterIndex = w.size() + counter;
		int queue = waiterIndex % w.size();
		counter++;
		numOfCustomers++;

		WaiterAgent waiter = w.get(queue);
		
		waiter.msgSeatThisCustomer(customer, table);
		table.setOccupant(customer);
		customer.setWaiter(waiter);
	
		waitingCustomers.remove(customer);
		
		for(myCustomer c : customers) {
			if(c.c == customer) {
				customers.remove(customer);
			}
		}
		
		availableTables--;
	}
	
	private void tablesAreFull(CustomerAgent customer) {
		Do("We're at capacity. Would you like to put your name down?");

		//customer.msgTablesAreFull();

		waitingCustomers.remove(customer);
	}
	
	private void checkForBreak(myWaiter w) {
		if(waiters.size() >= 2) {
			print("You may go on break, we have plenty of waiters.");
			w.w.msgBreakRequestAccepted();
		}
		if(waiters.size() == 1) {
			print("You're our only waiter, you'll have to wait.");
			w.w.msgBreakRequestDenied();
		}
		w.s = WaiterState.Waiting;
		stateChanged();
	}
	
	private void reorganizeWaiters(myWaiter w) {
		print("Updating our available waiters.");
		w.s = WaiterState.Gone;
		stateChanged();
	}
	//Utilities

	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}
}