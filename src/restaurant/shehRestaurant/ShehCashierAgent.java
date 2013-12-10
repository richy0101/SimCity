package restaurant.shehRestaurant;

import agent.Agent;

import restaurant.shehRestaurant.helpers.Bill;
import restaurant.shehRestaurant.helpers.Menu;
import restaurant.shehRestaurant.helpers.Table;
import restaurant.shehRestaurant.interfaces.Cashier;
import restaurant.shehRestaurant.interfaces.Customer;
import restaurant.shehRestaurant.interfaces.Market;
import restaurant.shehRestaurant.interfaces.Waiter;
import restaurant.shehRestaurant.helpers.Bill.OrderBillState;

import restaurant.shehRestaurant.test.mock.EventLog;

import java.util.*;

/**
 * Restaurant Cashier Agent
 */
public class ShehCashierAgent extends Agent implements Cashier {
	public List<myCustomer> myCustomers = Collections.synchronizedList(new ArrayList<myCustomer>());
	public List<Bill> bills = Collections.synchronizedList(new ArrayList<Bill>());
	
	Timer timer = new Timer();
	Menu menu = new Menu();
	Table table;
	Bill bill;
	
	public EventLog log = new EventLog();

	private double money = 0;
	private String name;
	private Market market;
	
	private class myCustomer {
		Customer c;
		double debt;
		
		myCustomer(Customer c2, double change) {
			c = c2;
			debt = change;
		}
		
		private double getMoney() {
			return debt;
		}
	}
	
	public class FoodData {
		public int price;
		public int cookTime;
		public int quantity;
		
		FoodData(int p, int t, int q) {
			price = p;
			cookTime = t;
			quantity = q;
		}
	}
	
	FoodData steak = new FoodData(20, 5000, 1);
	FoodData chicken = new FoodData(15, 5000, 1);
	FoodData fish = new FoodData(20, 5000, 1);
	FoodData vegetarian = new FoodData(20, 5000, 1);
	
	private Map<String, FoodData> inventory = new HashMap<String, FoodData>(); {
		inventory.put("Steak", steak);
		inventory.put("Chicken", chicken);
		inventory.put("Fish", fish);
		inventory.put("Vegetarian", vegetarian);
	}
	
	public ShehCashierAgent(String n) {
		super();
		this.name = "Cashier";
		name = n;
	}

	// Messages
	public void msgProcessThisBill(String o, Customer c, Waiter w) {
		bills.add(new Bill(0, o, c, w, OrderBillState.Pending));
		stateChanged();
	}
	
	public void msgHereIsMarketBill(Bill cost, Market supplier) {
		print("Received bill from market of $" + cost.m + ".");
		double price = cost.m;
		bills.add(new Bill(price, OrderBillState.PayingMarketOrder));
		market = supplier;
		
		stateChanged(); 
	}
	
	public void msgHereToPay(Customer c, double money2) {
		money = money2;
		for(Bill b : bills) {
			if(b.c == c) {
				b.s = OrderBillState.Paying;
				stateChanged();
			}
		}
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		synchronized(bills) {
			for (Bill b : bills) {
				if (b.s == OrderBillState.Pending) {
					ProcessBill(b);
					return true;
				}
			}
		}
			
		synchronized(bills) {
			for (Bill b : bills) {
				if (b.s == OrderBillState.Calculating) {
					BillIsProcessed(b);
					return true;
				}
			}
		}
		
		synchronized(bills) {
			for (Bill b : bills) {
				if (b.s == OrderBillState.BillSent) {
					return true;
				}
			}
		}
			
		synchronized(bills) {
			for (Bill b : bills) {
				if(b.s == OrderBillState.Paying) {
					ReturnChange(b);
					return true;
				}
			}
		}
		
		synchronized(bills) {
			for (Bill b : bills) { 
				if(b.s == OrderBillState.PayingMarketOrder) {
					PayMarketOrder(b);
					return true;
				}
			}
		}
		
		synchronized(bills) {
			for (Bill b : bills) {
				if(b.s == OrderBillState.Complete) {
					return true;
				}
			}
		}
	
		return false;
	}

	// Actions
	private void ProcessBill(Bill b) {
		//DoPlacement(order); //animation
		print("Bill is processing.");
		//b.m = inventory.get(b.o).price; 
		b.setMoney(inventory.get(b.o).price);
		
		for(myCustomer mc : myCustomers) {
			if(mc.c == b.c) {
				print("They owe us money from last time.");
				b.m = b.m + mc.debt;
			}
		}
		
		b.s = OrderBillState.Calculating;
		stateChanged();
	}
	
	private void BillIsProcessed(Bill b) {
		print("Bill is processed.");
		b.w.msgCollectBill(b);
		b.s = OrderBillState.BillSent;
		stateChanged();
	}
	
	private void ReturnChange(Bill b) {
		double change = 0; 
		
		if(money > b.m) {
			change = money - b.m;
			print("$" + change + " is your change. Come again!");
			
		}
		else if(money == b.m) {
			print("Perfect amount, no change. Come again!");
		}
		else if(money < b.m) {
			change = b.m - money;
			print("You owe us $" + change + " next time you come in.");
			myCustomers.add(new myCustomer(b.c, change));
		}
		
		b.m = change;
		b.c.msgHereIsYourChange(b); //can change this to bill possibly
		b.s = OrderBillState.Complete;
	}
	
	private void PayMarketOrder(Bill b) {
		print("Paying market order now.");
		
		market.msgHereIsPayment(b);
		b.s = OrderBillState.Complete;
		
	}

}