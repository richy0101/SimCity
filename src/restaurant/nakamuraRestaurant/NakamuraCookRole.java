package restaurant.nakamuraRestaurant;

import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurant.nakamuraRestaurant.gui.CookGui;

/**
 * Restaurant Cook Agent
 */
public class NakamuraCookRole extends Role {
	public List<Order> Orders
	= Collections.synchronizedList(new ArrayList<Order>());
	
	private Map<String, Food> food = Collections.synchronizedMap(new HashMap<String, Food>());
	
	public List<MarketAgent> markets = Collections.synchronizedList(new ArrayList<MarketAgent>());
	private Semaphore actionComplete = new Semaphore(0,true);

	public enum orderState {pending, cooking, done};
	private NakamuraCashierAgent cashier;
	private String name;
	Timer timer = new Timer();
	boolean checkInventory;

	public CookGui cookGui;

	public NakamuraCookRole(String name) {
		super();

		this.name = name;
		synchronized(food) {
			food.put("Steak", new Food("Steak", 15000, 5));
			food.put("Chicken", new Food("Chicken", 10000, 5));
			food.put("Salad", new Food("Salad", 5000, 5));
			food.put("Pizza", new Food("Pizza", 15000, 5));
		}
		
	}

	public String getCookName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getOrders() {
		return Orders;
	}
	
	public void addMarket(MarketAgent m) {
		markets.add(m);
	}
	
	public void setCashier(NakamuraCashierAgent c) {
		cashier = c;
	}
	
//	public void addWaiter(WaiterAgent w) {
//		waiters.add(w);
//	}

	// Messages

	public void msgCookOrder(NakamuraWaiterRole w, String choice, int tableNumber) {
		print("Received msgCookOrder");
		Orders.add(new Order(w, choice, tableNumber));
		stateChanged();
	}

	public void msgFoodDone(Order o) {
		print("Received msgFoodDone");
		o.s = orderState.done;
		stateChanged();
	}
	
	public void msgCantFillOrder(MarketAgent m, List<String> choice, int amount) {
		print("Received msgCantFillOrder");
		synchronized(food) {
			for(String c : choice) {
				food.get(c).markets.add(m);
				food.get(c).ordered = false;
			}
		}
		stateChanged();
	}

	public void msgOrderReady (List<String> choice, int amount) {
		synchronized(food) {
			for(String c: choice) {
				food.get(c).increaseSupply(amount);
				food.get(c).ordered = false;
			}
		}
		stateChanged();
	}
	
	public void msgCheckInventory() {
		print("Received msgCheckInventory");
		checkInventory = true;
		stateChanged();
	}
	
	public void msgActionComplete() {
		print("msgActionComplete called");
		actionComplete.release();
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
		if(checkInventory) {
			CheckInventory();
			checkInventory = false;
		}
		
		synchronized(Orders) {
			for (Order o : Orders) {
				if(o.getState() == orderState.done) {
					PlateOrder(o);
				}
			}
			for (Order o : Orders) {
				if (o.getState() == orderState.pending) {
					CookOrder(o);
				}
			}
		}
		

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void CookOrder(final Order o) {
		if (food.get(o.choice).getSupply() <= 2 && food.get(o.choice).ordered == false) {
			List<String> order = new ArrayList<String>();
			order.add(o.choice);
			OrderFood(order);
		}
		
		if(food.get(o.choice).getSupply() == 0) {
			o.w.msgOutofFood(o.choice, o.tableNumber);
			Orders.remove(o);
		}
		
		else {
			
			o.s = orderState.cooking;
			DoCookOrder(o);
			food.get(o.choice).decreaseSupply();
			print(o.choice + " remaining: " + food.get(o.choice).getSupply());
			timer.schedule(new TimerTask() {
				public void run() {
					msgFoodDone(o);
					stateChanged();
				}
			},
			food.get(o.choice).getCookingTime());
		}
	}

	private void PlateOrder(Order o) {
		DoPlateOrder(o); //animation
		print("Plating food");
		o.w.msgFoodReady(o.choice, o.tableNumber);
		Orders.remove(o);
	}
	
	private void OrderFood(List<String> choices) {	
		synchronized(markets) {
			for(MarketAgent m : markets) {
				List<String> orders = new ArrayList<String>();
				for(String c : choices) {
					if(!food.get(c).markets.contains(m)) {
						orders.add(c);
					}
				}
				if(!orders.isEmpty()){
					m.msgNewOrder(this, cashier, orders, 5);
					for(String o : orders)
						choices.remove(o);
				}
			}
		}
	}
	
	private void CheckInventory() {
		List<String> order = new ArrayList<String>();
		synchronized(food){
			print(food.get("Steak").supply + " Steaks remaning");
			if(food.get("Steak").supply <= 5) {
				order.add("Steak");
				food.get("Steak").ordered = true;
			}
			print(food.get("Pizza").supply + " Pizzas remaning");
			if(food.get("Pizza").supply <= 5) {
				order.add("Pizza");
				food.get("Pizza").ordered = true;
			}
			print(food.get("Chicken").supply + " Chickens remaning");
			if(food.get("Chicken").supply <= 5) { 
				order.add("Chicken");
				food.get("Chicken").ordered = true;
			}
			print(food.get("Salad").supply + " Salads remaning");
			if(food.get("Salad").supply <= 5) {
				order.add("Salad");
				food.get("Salad").ordered = true;
			}
		}
		
		OrderFood(order);
	}

	// The animation DoXYZ() routines
	private void DoCookOrder(Order o) {
		cookGui.DoGoToCooking();
		cookGui.AddCooking(o.choice);
	}
	
	private void DoPlateOrder(Order o) {
		cookGui.DoGoToPlating();
		cookGui.AddPlating(o.choice);
	}

	//utilities

	public void setGui(CookGui gui) {
		cookGui = gui;
	}

	public CookGui getGui() {
		return cookGui;
	}

	private class Order {
		NakamuraWaiterRole w;
		int tableNumber;
		orderState s;
		String choice;

		Order(NakamuraWaiterRole w, String choice, int tableNumber) {
			this.tableNumber = tableNumber;
			this.w = w;
			this.choice = choice;
			s = orderState.pending;
		}

		void setState(orderState s) {
			this.s = s;
		}

		orderState getState() {
			return s;
		}
		
		NakamuraWaiterRole getWaiter() {
			return w;
		}
		
		String getChoice() {
			return choice;
		}
	}
	
	private class Food {
		String name;
		int cookingTime;
		int supply;
		boolean ordered;
		List<MarketAgent> markets;
		
		Food(String n, int c, int s) {
			this.name = n;
			this.cookingTime = c;
			this.supply = s;
			this.ordered = false;
			markets = new ArrayList<MarketAgent>();
		}
		
		int getSupply() {
			return supply;
		}
		
		int getCookingTime() {
			return cookingTime;
		}
		
		void decreaseSupply() {
			supply--;
		}
		
		void increaseSupply(int amount) {
			supply +=amount;
		}
	}
}

