package restaurant.huangRestaurant;

import agent.Agent;

import java.util.*;

import restaurant.huangRestaurant.interfaces.Market;




/**
 * Restaurant Market
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class MarketAgent extends Agent implements Market {
	
	
	/* class MarketTimerTask extends TimerTask {
		CookAgent cook;
		String foodType;
		public MarketTimerTask(String foodType, CookAgent cook) {
			this.foodType = foodType;
			this.cook = cook;
		}
		@Override
		public void run() {
			
		}
	}*/
	private enum OrderState {needsCheck, cannotFulfill, halfAss, Deliverable, Requested, Cancelled, Done};
	private class Order {
		public String type;
		public int stock;
		OrderState state;
		Order(String choice, int request) {
			this.type = choice;
			this.stock = request;
			this.state = OrderState.needsCheck;
		}
	}
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	public enum BillState {pending, paid, tabbed, told}
	public class MyBill {
		private BillState state;
		private FoodBill b;
		
		MyBill(String type, int supply) {
			state = BillState.pending;
			b = new FoodBill(type, supply);
		}
	}
	public List<MyBill> bills = Collections.synchronizedList(new ArrayList<MyBill>());
	
	public class MarketFood {
		public String type;
		//public int restockTime;
		public int stock;
		public MarketFood(String type) {
			this.type = type;
			/*if (this.type == "Chicken") {
				restockTime = 20000;
			}
			else if (this.type == "Steak") {
				restockTime = 30000;
			}
			else if (this.type == "Salad") {
				restockTime = 10000;
			}
			else if (this.type == "Pizza") {
				restockTime = 25000;
			}*/
			stock = 25;
		}
	}
	
	public List<MarketFood> inventory = Collections.synchronizedList(new ArrayList<MarketFood>());
	private CookAgent cook;
	private String name;
	public MarketAgent(CookAgent c, String name) {
		this.cook = c;
		this.name = name;
		MarketFood initialMarketFood = new MarketFood("Chicken");
		inventory.add(initialMarketFood);
		initialMarketFood = new MarketFood ("Steak");
		inventory.add(initialMarketFood);
		initialMarketFood = new MarketFood ("Salad");
		inventory.add(initialMarketFood);
		initialMarketFood = new MarketFood ("Pizza");
		inventory.add(initialMarketFood);
		this.startThread();
	}

	public String getName() {
		return name;
	}

	// Messages
	public void msgCancelOrder(String request) {
		//System.out.println("msgCancelRequest received: Market " + name + ": Order of " + request + " cancelled.");
		synchronized(orders) {
			for (Order o : orders) {
				if(o.type.equals(request)) {
					o.state = OrderState.Cancelled;
				}
			}
		}
		stateChanged();
	}
	public void msgPlsDeliverRequest(String request) {
		System.out.println("msgDeliverRequest received: Market " + name + ": Order of " + request + " moved to requested, ready to deliver.");
		synchronized(orders) {
			for (Order o : orders) {
				if(o.type.equals(request)) {
					o.state = OrderState.Requested;
				}
			}
		}
		stateChanged();
	}

	public void msgWhatIsYourStockState(String checkFood, int requirement) {
		//System.out.println("msgWhatIsYourStock received: Market " + name + ": New Order of " + checkFood + " created");
		Order o = new Order(checkFood, requirement);
		orders.add(o);
		stateChanged();
	}
	public void msgHereIsPayment(FoodBill b) {
		synchronized(bills) {
			for(MyBill mb : bills) {
				if (mb.b.equals(b) && mb.state.equals(BillState.pending)) {
					System.out.println("msgHereIsPayment received: Market " + name + ": Received payment from Cashier.");
					mb.state = BillState.paid;
					break;
				}
			}
		}
		stateChanged();
	}
	public void msgCannotPay(FoodBill b) {
		synchronized(bills) {
			for(MyBill mb : bills) {
				if (mb.b.equals(b) && mb.state.equals(BillState.pending)) {
					mb.state = BillState.tabbed;
					break;
				}
			}
		}
		stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		if(!bills.isEmpty()) {
			synchronized(bills) {
				for (MyBill mb: bills) {
					if(mb.state.equals(BillState.pending)) {
						tellCashierBill(mb, this);
						return true;
					}
				}
			}
		}
		if(!orders.isEmpty()) {
			synchronized(orders) {
				for (Order o : orders) {
					if (o.state.equals(OrderState.Cancelled)) {
						cancelOrder(o);
						return true;
					}
				}
			}
		}
		if(!orders.isEmpty()) {
			synchronized(orders) {
				for (Order o : orders) {
					if(o.state.equals(OrderState.needsCheck)) {
						checkOrder(o);
						return true;
					}
				}
			}
		}
		if(!orders.isEmpty()) {
			synchronized(orders) {
				for (Order o : orders) {
					if(o.state.equals(OrderState.Requested)) {
						deliverOrder(o);
						return true;
					}
				}
			}
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}


	private void tellCashierBill(MyBill mb, MarketAgent marketAgent) {
		mb.state = BillState.told;
		cook.host.getCashier().msgHereIsFoodDeliveryBill(mb.b, this);
		
	}

	// Actions
	private void cancelOrder(Order o) {
		orders.remove(o);
	}
	private void checkOrder(Order o) {
		synchronized(inventory) {
			for (MarketFood i: inventory) {
				if (i.type.equals(o.type)) {
					if (i.stock >= o.stock && !o.state.equals(OrderState.Deliverable)) {
						o.state = OrderState.Deliverable;
						cook.msgRequestGood(o.type, this);
						break;
					}
					else if (i.stock > 0 && !o.state.equals(OrderState.halfAss)) {
						o.state = OrderState.halfAss;
						cook.msgRequestHalf(o.type, this, i.stock);
						break;
					}
					else if (i.stock <= 0 && !o.state.equals(OrderState.cannotFulfill)){
						o.state = OrderState.cannotFulfill;
						cook.msgRequestBad(o.type, this);
						break;
					}
				}
			}
		}		
	}
	private void deliverOrder(Order o) {
		int resupply;
		synchronized(inventory) {
			for (MarketFood i: inventory) {
				if(o.type.equals(i.type)) {
					if (i.stock >= o.stock) {
						resupply = o.stock;
						i.stock = i.stock - resupply;
						cook.msgHereIsDelivery(o.type, resupply);
						MyBill mb = new MyBill(o.type, resupply);
						bills.add(mb);
						orders.remove(o);
						break;
					}
					else if (i.stock < o.stock) {
						resupply = i.stock;
						i.stock = 0;
						cook.msgHereIsDelivery(o.type, resupply);
						orders.remove(o);
						break;
					}
				}
			}
		}
	}
	//Hacks
	public void emptyMarket() {
		synchronized(inventory) {
			for (MarketFood i: inventory) {
				i.stock = 0;
			}
		}
		System.out.println("Market " + name + " Emptied.");
	}



	

	/*private void prepareOrder() {
		int resupply;
		for (MarketFood i: inventory) {
			for (Food o: currentOrder) {
				if(o.type.equals(i.type)) {
					if ((int) o.stock <= threshold) {
						resupply = threshold - o.stock;
						if (resupply <= i.stock) {
							i.stock = i.stock - resupply;
							o.stock += resupply;
						}
					}
				}
			}
		}
	}*/
	//utilities
}

