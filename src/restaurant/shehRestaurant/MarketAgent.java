package restaurant.shehRestaurant;

import agent.Agent;
import restaurant.shehRestaurant.CashierAgent;
import restaurant.shehRestaurant.gui.Bill.OrderBillState;
import restaurant.shehRestaurant.gui.Menu;
import restaurant.shehRestaurant.gui.Order;
import restaurant.shehRestaurant.gui.Order.OrderMarketState;
import restaurant.shehRestaurant.gui.Bill;
import restaurant.shehRestaurant.gui.FoodData;
import restaurant.shehRestaurant.interfaces.Cashier;
import restaurant.shehRestaurant.interfaces.Cook;
import restaurant.shehRestaurant.interfaces.Market;

import java.util.*;
/**
 * Restaurant Cook Agent
 */
public class MarketAgent extends Agent implements Market {
	Cook cook;
	Cashier cashier;
	Menu menu = new Menu();
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	private int shipment = 0;
	Timer timer = new Timer();
	
	private String name;
	private Map<String, FoodData> inventory;

	
	public MarketAgent(String name, Map<String, FoodData> inv) {
		super();

		this.name = name;
		inventory = inv;	
	}

	// Messages
	public void msgOrderForReplenishment(Vector<String> o, Cook c, Cashier ca) {
		cook = c;
		cashier = ca;
		orders.add(new Order(o, OrderMarketState.Pending)); 
		stateChanged();
	}
	
	public void msgHereIsPayment(Bill b) {
		print("Received bill from cashier!");
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	
	protected boolean pickAndExecuteAnAction() {
		synchronized(orders) {
			for (Order o : orders) {
				if (o.ms == OrderMarketState.Pending) {
					ProcessOrder(o);
					return true;
				}
			}
		}
		
		synchronized(orders) {
			for (Order o : orders) {
				if (o.ms == OrderMarketState.Replenishing) {
					SendShipment(o);
					return true;
				}
			}
		}
		
		return false;
	}

	// Actions
	private void ProcessOrder(Order o) {
		print("Searching for items in inventory now.");
		int requestedamount = 4;
		int price = 0;
		
		//parsing market inventory to fulfill order
		for(int i = 0; i < o.list.size(); i++) {
			shipment = inventory.get(o.list.get(i)).quantity;
			
			//if enough market inventory
			if(shipment > requestedamount) {
				shipment = requestedamount;
				inventory.get(o.list.get(i)).quantity = inventory.get(o.list.get(i)).quantity - requestedamount;
				price = price + (shipment * inventory.get(o.list.get(i)).price);
				//o.list.get(i).quantity = o.list.get(i).quantity + requestedamount;
			}
			
			//if not enough market inventory
			if(shipment < requestedamount) {
				shipment = 0;
				print("Cannot fulfill order of " + inventory.get(o.list.get(i)).name + ", you'll have to order from another market.");
				
				price = price + (shipment * inventory.get(o.list.get(i)).price);
				cook.msgCannotFulfillOrder(inventory.get(o.list.get(i)).name);
				o.list.remove(i);
			}
			//price = price + (shipment * inventory.get(o.list.get(i)).price);
		}
		
		Bill bill = new Bill(price, OrderBillState.PayingMarketOrder);
		
		cashier.msgHereIsMarketBill(bill, this);
		
		o.ms = OrderMarketState.Replenishing;
		stateChanged();
	}
	
	private void SendShipment(final Order o) {
		print("Sending shipment.");
		
		timer.schedule(new TimerTask() {
			public void run() {
				cook.msgHereIsReplenishment(o, shipment);
			}
		},
		1000);
		
		orders.remove(o);
		o.ms = OrderMarketState.Done;
		stateChanged();
	}
	
}

