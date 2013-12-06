package restaurant.nakamuraRestaurant;

import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.nakamuraRestaurant.gui.HostGui;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class NakamuraHostRole extends Role {
	static final int NTABLES = 4;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<NakamuraCustomerRole> waitingCustomers
	= Collections.synchronizedList(new ArrayList<NakamuraCustomerRole>());
    private List<Waiters> waiters = Collections.synchronizedList(new ArrayList<Waiters>());
	public Collection<Table> tables;
	public enum TableState {empty, occupied, dirty};
	public enum WaiterState {working, askedforbreak, onbreak};
	private Semaphore actionComplete = new Semaphore(0,true);
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;

	public HostGui hostGui = null;

	public NakamuraHostRole(String name) {
		super();

		this.name = name;
		// make some tables
		tables = Collections.synchronizedList(new ArrayList<Table>(NTABLES));
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
	}
	
	public boolean tablesFull() {
		synchronized(tables) {
			for(Table table : tables) {
				if(!table.isOccupied())
					return false;
			}
		}
		return true;
	}
	
	public void addWaiter(NakamuraWaiterRole w) {
		waiters.add(new Waiters(w));
	}
	// Messages

	public void msgIWantFood(NakamuraCustomerRole cust) {
		print("Received msgIWantFood");
		if(!tablesFull())
			waitingCustomers.add(cust);
		else
			cust.msgRestaurantFull();
		stateChanged();
	}
	
	public void msgLeaving(NakamuraCustomerRole cust) {
		print("Received msgLeaving");
		stateChanged();
	}
	
	public void msgStaying(NakamuraCustomerRole cust) {
		print("Received msgStaying");
		waitingCustomers.add(cust);
		stateChanged();
	}
	
	public void msgGoingOnBreak(NakamuraWaiterRole w) {
		print("Received msgGoingOnBreak");
		synchronized(waiters) {
			for(Waiters wait : waiters) {
				if(wait.waiter == w) 
					wait.state = WaiterState.askedforbreak;
			}
		}
		stateChanged();
	}
	
	public void msgBackToWork(NakamuraWaiterRole w) {
		print("received msgBackToWork");
		synchronized(waiters) {
			for(Waiters wait : waiters) {
				if(wait.waiter == w) {
					wait.state = WaiterState.working;
					stateChanged();
				}
			}
		}
	}

	public void msgTableEmpty(int tablenum) {
		print("Received msgTableEmpty");
		synchronized(tables) {
			for (Table table : tables) {
				if (table.tableNumber == tablenum) {
					table.s = TableState.dirty;
					stateChanged();
				}
			}
		}
	}
	
	public void msgAnimationSeatsUpdated() {
		actionComplete.release();
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		synchronized(tables) {
			for (Table table : tables) {
				if (table.s == TableState.dirty) {
					cleanTable(table);
					return true;
				}
			}
		}
		
		synchronized(waiters) {
			for (Waiters w : waiters) {
				if(w.state == WaiterState.askedforbreak) {
					if(waiters.size() > 1) {	//Only 1 Waiter
						for(Waiters wait : waiters) {
							if(wait.state != WaiterState.onbreak && wait.waiter.getName() != w.waiter.getName()) { //Another Waiter not on break
								w.waiter.msgGoOnBreak();
								w.state = WaiterState.onbreak;
								return true;
							}
						}
						w.waiter.msgBreakDenied();
						w.state = WaiterState.working;
					}
					else {
						w.waiter.msgBreakDenied();
						w.state = WaiterState.working;
					}
					return true;
				}
			}
		}

		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		
		synchronized(tables) {
			for (Table table : tables) {
				if (!table.isOccupied()) {
					if (!waitingCustomers.isEmpty()) {
						if(!waiters.isEmpty()) {
							seatCustomer(waitingCustomers.get(0), table);//the action
							return true;//return true to the abstract agent to reinvoke the scheduler.
						}
					}
				}
			}
		}
		
		if(!waitingCustomers.isEmpty())  {
			hostGui.DoUpdateSeat(waitingCustomers);
			try{
				actionComplete.acquire();
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return true;
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void seatCustomer(NakamuraCustomerRole customer, Table table) {
		int least = 0;
		synchronized(waiters) {
			for(Waiters w: waiters){ //Find waiter with least customers
				if(w.getCustomers() < waiters.get(least).getCustomers() && w.state != WaiterState.onbreak)
						least = waiters.indexOf(w);		
			}
		}
		
		waiters.get(least).waiter.msgNewCustomer(customer, table.tableNumber);
		waiters.get(least).addCustomer();
		table.setOccupant(customer, waiters.get(least));
		waitingCustomers.remove(customer);
	}

	private void cleanTable(Table table) {
		synchronized(waiters) {
			for(Waiters w : waiters) {
				if(w == table.getWaiter())
					w.removeCustomer();
			}		
		}
		table.setUnoccupied();	
	}
//	// The animation DoXYZ() routines
//	private void DoSeatCustomer(CustomerAgent customer, Table table) {
//		//Notice how we print "customer" directly. It's toString method will do it.
//		//Same with "table"
//		print("Seating " + customer + " at " + table);
//		hostGui.DoBringToTable(customer, table.tableNumber); 
//
//	}

	//utilities

	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}

	private class Table {
		NakamuraCustomerRole occupiedBy;
		Waiters servedBy;
		int tableNumber;
		TableState s;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
			s = TableState.empty;
		}

		void setOccupant(NakamuraCustomerRole cust, Waiters waiter) {
			occupiedBy = cust;
			servedBy = waiter;
			s = TableState.occupied;
		}

		void setUnoccupied() {
			occupiedBy = null;
			servedBy = null;
			s = TableState.empty;
		}

		NakamuraCustomerRole getOccupant() {
			return occupiedBy;
		}
		
		Waiters getWaiter() {
			return servedBy;
		}

		boolean isOccupied() {
			return s != s.empty;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}
	
	private class Waiters {
		NakamuraWaiterRole waiter;
		int numCustomers;
		WaiterState state;

		Waiters(NakamuraWaiterRole w) {
			this.waiter = w;
			this.state = WaiterState.working;
		}

		void addCustomer() {
			numCustomers++;
		}

		void removeCustomer() {
			numCustomers--;
		}
		
		int getCustomers() {
			return numCustomers;
		}
	}
	
	private class WaitingCustomer {
		NakamuraCustomerRole c;
		int seat;
		
		WaitingCustomer(NakamuraCustomerRole c, int seat) {
			this.c = c;
			this.seat = seat;
		}
	}
}

