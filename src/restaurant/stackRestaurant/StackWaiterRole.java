package restaurant.stackRestaurant;

import agent.Role;
import gui.Building;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.stackRestaurant.gui.WaiterGui;
import restaurant.stackRestaurant.helpers.Check;
import restaurant.stackRestaurant.interfaces.*;
import city.helpers.Directory;

public class StackWaiterRole extends Role implements Waiter {
	protected Cook cook;
	protected Host host;
	private Cashier cashier;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	
	protected Semaphore doneAnimation = new Semaphore(0,true);
	
	public WaiterGui waiterGui = null;
	private String myLocation;
	
	protected enum AgentState
	{Arrived, Working, WantToGoOnBreak, WaitingForNotice, GoingOnBreak, OnBreak, FinishingBreak, GettingPaycheck, Leaving, WaitingForPaycheck};
	AgentState state = AgentState.Working;
	
	protected enum CustomerState
	{Waiting, Seated, ReadyToOrder, Ordering, Ordered, AtCook, FoodEmpty, FoodReady, WaitingForReadyFood, Eating, DoneEating, ReadyForCheck, WaitingForCheck, HasCheck, Paying, Gone};
	
	public StackWaiterRole(String location) {
		super();
		host = (Host) Directory.sharedInstance().getAgents().get("StackRestaurantHost");
		cashier = (Cashier) Directory.sharedInstance().getAgents().get("StackRestaurantCashier");
		waiterGui = new WaiterGui(this);
		myLocation = location;
		state = AgentState.Arrived;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(waiterGui);
			}
		}
	}
	
	public String getName() {
		return getPersonAgent().getName();
	}
	
	public void setCook(Cook cook) {
		this.cook = cook;
	}
	
	public void setHost(Host host) {
		this.host = host;
	}
	
	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	
//	scheduler---------------------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean pickAndExecuteAnAction() {
		try {
			if(state == AgentState.Arrived) {
				tellHostAtWork();
				return true;
			}
			if(state == AgentState.WantToGoOnBreak) {
				askHostToGoOnBreak();
				return true;
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.ReadyToOrder) {
					takeOrderFromCustomer(customer);
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.Ordered) {
					takeOrderToCook(customer);
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.FoodEmpty) {
					tellCustomerToReorder(customer);
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.FoodReady) {
					goPickUpFood(customer);
					takeFoodToTable(customer);
					return true;
				}
			}	
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.DoneEating) {
					tellHostTableEmpty(customer);
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.ReadyForCheck) {
					cashier.msgComputeCheck(this, customer.customer, customer.choice);
					customer.state = CustomerState.WaitingForCheck;
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.HasCheck) {
					giveCustomerCheck(customer);
					return true;
				}
			}
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.Waiting) {
					seatCustomer(customer, customer.table, customer.seatNum);
					return true;
				}
			}
			if(state == AgentState.GettingPaycheck) {
				goGetPaycheck();
				return true;
			}
			if(state == AgentState.Leaving) {
				leaveRestaurant();
				return true;
			}
			if(state == AgentState.GoingOnBreak) {
				goOnBreak();
				return true;
			}
			if(state == AgentState.FinishingBreak) {
				tellHostOffBreak();
				return true;
			}
			waiterFree();
		
		} catch(ConcurrentModificationException e) {
			return false;
		}

		return false;
	}
	

	//	actions---------------------------------------------------------------------------------------------------------------------------------
	private void tellHostAtWork() {
		host.msgAddWaiter(this);
		waiterGui.DoGoHome();
		state = AgentState.Working;
	}
	
	private void giveCustomerCheck(MyCustomer customer) {
		host.msgWaiterBusy(this);
		DoGoToCustomerTable(customer, customer.table);
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		customer.customer.msgHereIsCheck(customer.check);
		customer.state = CustomerState.Paying;
	}
	
	private void goOnBreak() {
		print("went on break");
		state = AgentState.OnBreak;
		DoGoOnBreak();
	}
	
	private void askHostToGoOnBreak() {
		state = AgentState.WaitingForNotice;
		host.msgWaiterWantsToGoOnBreak(this);
	}
	
	private void tellHostOffBreak() {
		host.msgWaiterComingOffBreak(this);
		state = AgentState.Working;
	}
	
	private void seatCustomer(MyCustomer customer, int table, int seat) {
		host.msgWaiterBusy(this);
		DoGoToWaitingArea();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		customer.customer.msgSitAtTable(this, table);
		DoSeatCustomer(customer.customer, table); //animation
		customer.state = CustomerState.Seated;
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void waiterFree() {
		host.msgWaiterFree(this);
	}
	
	private void takeOrderFromCustomer(MyCustomer customer) {
		host.msgWaiterBusy(this);
		DoGoToCustomerTable(customer, customer.table);
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		print("here to take order");
		customer.state = CustomerState.Ordering;
		customer.customer.msgHereToTakeOrder();
	}
	
	protected void takeOrderToCook(MyCustomer customer) {
		
	}
	
	private void goPickUpFood(MyCustomer customer) {
		host.msgWaiterBusy(this);
		print("here to get order");
		DoGoToCook();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		customer.state = CustomerState.WaitingForReadyFood;
	}
	
	private void tellCustomerToReorder(MyCustomer customer) {
		host.msgWaiterBusy(this);
		print("telling " + customer.customer + " to reorder");
		DoGoToCustomerTable(customer, customer.table);
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		customer.customer.msgReorder();	
	}
	
	
	private void takeFoodToTable(MyCustomer customer) {
		host.msgWaiterBusy(this);
		DoGoToCustomerTable(customer, customer.table); //animation
		updateGui(customer.choice.substring(0, 2));
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		customer.state = CustomerState.Eating;
		customer.customer.msgHereIsFood();
		updateGui("");
	}
	
	private void tellHostTableEmpty(MyCustomer customer) {
		customer.state = CustomerState.Gone;
		host.msgLeavingTable(customer.customer);
	}
	
	private void leaveRestaurant() {
		print("Leaving.");
		DoLeaveRestaurant();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getPersonAgent().msgRoleFinished();
	}
	
	private void goGetPaycheck() {
		print("Getting paycheck");
		waiterGui.DoGoToPaycheck();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		print("telling cashier I need my paycheck");
		cashier.msgNeedPaycheck(this);
		state = AgentState.WaitingForPaycheck;
	}

	
//	animation---------------------------------------------------------------------------------------------------------------------------------
	private void DoGoOnBreak() {
		print("Going on break");
		waiterGui.DoGoOnBreak();
	}
	
	private void DoSeatCustomer(Customer customer, int table) {
		print("Seating " + customer + " at table " + table);
		waiterGui.DoBringToTable(table); 

	}
	
	private void DoGoToCustomerTable(MyCustomer customer, int tableNumber) {
		print("Going to " + customer.customer + "'s table");
		waiterGui.DoBringToTable(tableNumber);
	}
	
	protected void DoGoToCook() {
		print("Going to cook");
		waiterGui.DoGoToCook();
	}
	
	private void DoGoToWaitingArea() {
		print("Going to customer");
		waiterGui.DoGoToCustomer();
	}
	
	private void updateGui(String choice) {
		waiterGui.updateGui(choice);
	}
	
	private void DoLeaveRestaurant() {
		host.msgWaiterLeaving(this);
		waiterGui.DoExitRestaurant();
	}
	
	
//	messages---------------------------------------------------------------------------------------------------------------------------------
	public void msgHereIsPaycheck(double funds) {
		getPersonAgent().setFunds(getPersonAgent().getFunds() + funds);
		state = AgentState.Leaving;
		stateChanged();
	}
	
	public void msgJobDone() {
		state = AgentState.GettingPaycheck;
		stateChanged();
		
	}
	
	public void msgHereIsCheck(Check check) {
		for(MyCustomer customer : customers) {
			if(customer.customer.equals(check.getCustomer())) {
				customer.state = CustomerState.HasCheck;
				customer.check = check;
				stateChanged();
			}
		}
	}
	
	public void msgCheckPlease(Customer customer) {
		for(MyCustomer mCustomer : customers) {
			if(mCustomer.customer.equals(customer)) {
				print(customer + " ready for check");
				mCustomer.state = CustomerState.ReadyForCheck;
				stateChanged();
			}
		}
	}
	
	public void	msgYouCanGoOnBreak(boolean canGoOnBreak) {
		if(canGoOnBreak) {
			print("Waiter can go on break");
			state = AgentState.GoingOnBreak;
			stateChanged();
		}
		else {
			print("Waiter cannot go on break");
			waiterGui.setWaiterCheckOff();
			state = AgentState.Working;
			stateChanged();
		}
	}
	
	public void msgReadyToOrder(Customer customer) {
		synchronized(customers) {
			for(MyCustomer mCustomer : customers) {
				if(mCustomer.customer.equals(customer)) {
					print(customer + " ready to order");
					mCustomer.state = CustomerState.ReadyToOrder;
					stateChanged();
				}
			}
		}
	}
	
	public void msgGiveOrder(Customer customer, String choice) {
		synchronized(customers) {
			for(MyCustomer mCustomer : customers) {
				if(mCustomer.customer.equals(customer)) {
					print(customer + " ordered " + choice);
					mCustomer.choice = choice;
					mCustomer.state = CustomerState.Ordered;
					stateChanged();
				}
			}
		}
	}
	
	public void msgSeatCustomer(Customer customer, int tableNumber, int seatNumber) {
		print("Seating " + customer);
		boolean doesContainCustomer = false;
		synchronized(customers) {
			for(MyCustomer mCustomer : customers) {
				if(mCustomer.customer.equals(customer)) {
					mCustomer.state = CustomerState.Waiting;
					mCustomer.table = tableNumber;
					mCustomer.seatNum = seatNumber;
					doesContainCustomer = true;
					break;
				}
			}
		}
		if(!doesContainCustomer) {
			customers.add(new MyCustomer(customer, tableNumber, seatNumber, CustomerState.Waiting));
		}
		
		stateChanged();
		
	}
	
	public void msgOrderDone(String choice, int table, int seat) {
		synchronized(customers) {
			for(MyCustomer customer : customers) {
				if(customer.choice == choice
						&& customer.table == table
						&& customer.seatNum == seat
						&& customer.state == CustomerState.AtCook) {
					print(customer.customer + "'s food is ready");
					customer.state = CustomerState.FoodReady;
					stateChanged();
				}
			}
		}
	}
	
	public void msgDoneEating(Customer customer) {
		synchronized(customers) {
			for(MyCustomer mCustomer : customers) {
				if(mCustomer.customer.equals(customer)) {
					mCustomer.state = CustomerState.DoneEating;
					print(customer + " done eating");
					stateChanged();
				}
			}
		}
	}
	
	public void msgFoodEmpty(String choice, int table, int seat) {
		synchronized(customers) {
			for(MyCustomer customer : customers) {
				if(customer.choice.equals(choice)
						&& customer.table == table
						&& customer.seatNum == seat
						&& customer.state == CustomerState.AtCook) {
					print(customer.customer + "'s food is empty");
					customer.state = CustomerState.FoodEmpty;
					stateChanged();
				}
			}
		}
	}
	
	@Override
	public void msgCookHere(Cook cook) {
		this.cook = cook;
		stateChanged();
	}
	
	public void msgIWantToGoOnBreak() {//from GUI
		print("I want to go on break");
		state = AgentState.WantToGoOnBreak;
		stateChanged();
	}
	
	public void msgImComingOffBreak() {
		if(state == AgentState.OnBreak) {
			print("I'm getting back to work");
			state = AgentState.FinishingBreak;
			stateChanged();
		}
	}
	
	public void msgAtTable() {//from animation
		doneAnimation.release();// = true;	
	}
	
	public void msgAtCook() {
		doneAnimation.release();
	}
	
	public void msgAtCustomer() {
		doneAnimation.release();
	}
	
	public void msgAtCashier() {
		doneAnimation.release();	
	}
	
	public void msgAnimationFinishedLeavingRestaurant() {
		doneAnimation.release();
	}
//	other---------------------------------------------------------------------------------------------------------------------------------
	
	/*public String toString() {
		return getName();
	}*/
	
	public boolean isOnBreak() {
		if(state == AgentState.OnBreak) {
			return true;
		}
		else return false;
	}
	
	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}

	public WaiterGui getGui() {
		return waiterGui;
	}

	protected class MyCustomer {
		MyCustomer(Customer customer, int table, int seatNum, CustomerState state) {
			this.customer = customer;
			this.table = table;
			this.seatNum = seatNum;
			this.state = state;
		}
		Customer customer;
		Check check;
		int table;
		String choice;
		int seatNum;
		CustomerState state;
	}
}
