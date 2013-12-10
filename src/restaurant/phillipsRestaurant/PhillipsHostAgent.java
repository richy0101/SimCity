package restaurant;

import agent.Agent;
import restaurant.CustomerAgent.AgentState;
//import restaurant.gui.HostGui;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;

/**
 * Restaurant Host Agent
 */

public class HostAgent extends Agent {
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<CustomerAgent> waitingCustomers = Collections.synchronizedList(new ArrayList<CustomerAgent>());
	public Collection<Table> tables;

	private String name;
	public enum HostState {doingNothing,sitCustomer};
	public HostState state = HostState.doingNothing;//The start state

	private ArrayList<WaiterAgent> waiters = new ArrayList<WaiterAgent>();
	private WaiterAgent waiter = null;

	public HostAgent(String name) {
		super();
		
		this.name = name;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
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
	
	public void addWaiter(WaiterAgent w){
		waiters.add(w);
	}

	public Collection getTables() {
		return tables;
	}
	
	public WaiterAgent pickWaiter(){
		WaiterAgent tempWaiter = waiters.get(0);
		for(int i=1;i<waiters.size();i++){
			if(waiters.get(i).getCustomers() < tempWaiter.getCustomers() 
					&& waiters.get(i).getCustomers() < waiters.get(i-1).getCustomers()){
				tempWaiter = waiters.get(i);
			}
		}
		return tempWaiter;
	}
	
	// Messages

	public void msgIWantFood(CustomerAgent cust) {
		state = HostState.sitCustomer;
		synchronized (waitingCustomers) {
			waitingCustomers.add(cust);
		}
		stateChanged();
	}

	public void msgLeavingTable(CustomerAgent cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				print(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}

	public void msgCanWaiterTakeBreak(WaiterAgent w){
		if(waitingCustomers.size()==0){
//DONT DO			waiter.msgTakeBreak();
		}
	}

	public void msgReadyToServe() {
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		if(state == HostState.sitCustomer){
			synchronized (waitingCustomers) {
				if (!waitingCustomers.isEmpty()){
					if(waiters.size() != 0){
					setWaiter(pickWaiter());
					for (Table table : tables) {
						if (!table.isOccupied()) {		
							waitingCustomers.get(0).tableNum = table.tableNumber;
							waitingCustomers.get(0).setWaiter(waiter);
							table.setOccupant(waitingCustomers.get(0));
							tellWaiterSeatCustomer(waitingCustomers.remove(0));
							return true;
						}
					}
					}
				}
			}
			
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	/*public boolean isBack(){
		System.out.println("XPOS: " + hostGui.xPos + "    YPOS: " + hostGui.yPos);
		if(hostGui.xPos == -20 && hostGui.yPos ==-20){
			state = HostState.doingNothing;
			System.out.println("TRUEEEEEEE");
			return true;
		}
		else
			return false;
	}*/
	
	// Actions

	private void tellWaiterSeatCustomer(CustomerAgent cust) {
		waiter.msgSeatCustomerAtTable(cust, cust.tableNum);//the action
		stateChanged();
	}


	//utilities

	/*public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}*/
	public void setWaiter(WaiterAgent waiter){
		this.waiter = waiter;
		
	}

	private class Table {
		CustomerAgent occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(CustomerAgent cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		CustomerAgent getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}
}

