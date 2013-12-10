package restaurant.nakamuraRestaurant;

import agent.Role;
import gui.Building;

import java.util.*;
import java.util.concurrent.Semaphore;

import market.interfaces.Market;
import city.helpers.Directory;
import restaurant.CookRole;
import restaurant.FoodInformation;
import restaurant.nakamuraRestaurant.gui.CookGui;

/**
 * Restaurant Cook Agent
 */
public class NakamuraCookRole extends CookRole {
	public List<Order> Orders
	= Collections.synchronizedList(new ArrayList<Order>());
	
	private Map<String, Food> food = Collections.synchronizedMap(new HashMap<String, Food>());

	public List<MarketOrder> marketOrders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	public List<Market> markets = Collections.synchronizedList(new ArrayList<Market>());
	private Semaphore actionComplete = new Semaphore(0,true);

	public enum orderState {pending, cooking, done};
	private enum marketOrderState {Ordered, Verifying, Done};
	private enum cookState {Arrived, Working, GettingPaycheck, Leaving, WaitingForPaycheck};
	cookState state;
	Timer timer = new Timer();
	boolean checkInventory;
	
	String myLocation;

	public CookGui cookGui;
	NakamuraHostAgent host;
	NakamuraCashierAgent cashier;

	public NakamuraCookRole(String location) {
		super();

		this.myLocation = location;
		
		synchronized(food) {
			food.put("Steak", new Food("Steak", 15000, 5));
			food.put("Chicken", new Food("Chicken", 10000, 5));
			food.put("Salad", new Food("Salad", 5000, 5));
			food.put("Pizza", new Food("Pizza", 15000, 5));
		}
		
		host = Directory.sharedInstance().getAgents().get("NakamuraRestaurantHost");
		cashier = Directory.sharedInstance().getAgents().get("NakamuraRestaurantCashier");
		
		for(int i = 0; i < Directory.sharedInstance().getMarkets().size(); i++) {
			markets.add(Directory.sharedInstance().getMarkets().get(i).getWorker());
		}		
		
		state = cookState.Arrived;
		
		myLocation = location;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(cookGui);
			}
		}
		
	}

	public List<Order> getOrders() {
		return Orders;
	}

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
	
	public void msgInventoryOut(Market m, List<String> choices, int amount) {
		print("Received msgCantFillOrder");
		synchronized(food) {
			for(String c : choices) {
				food.get(c).markets.add(m);
				food.get(c).ordered = false;
			}
		}
		stateChanged();
	}

	public void msgMarketDeliveringOrder (int amount, List<String> choices) {
		synchronized(food) {
			for(String c: choices) {
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
	
	public void msgVerifyMarketBill(List<String> choices, int amount) {
		print("Received msgVerifyMarketBill");
		synchronized(marketOrders) {
			for(MarketOrder order : marketOrders) {
				if(order.choices == choices && order.amount == amount) {
					order.state = marketOrderState.Verifying;
				}
			}
		}
	}
	
	public void msgActionComplete() {
		print("msgActionComplete called");
		actionComplete.release();
		stateChanged();
	}
	
	public void msgHereIsPaycheck(double pay){
		print("Received msgHereIsPaycheck");
		getPersonAgent().setFunds(getPersonAgent().getFunds() + pay);
		state = cookState.Leaving;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		if(state == cookState.Arrived) {
			ArriveAtWork();
			return true;
		}
		
		if(state == cookState.GettingPaycheck) {
			CollectPaycheck();
			return true;
		}
		
		if(state == cookState.Leaving) {
			LeaveRestaurant();
			return true;
		}
		
		if(checkInventory) {
			CheckInventory();
			checkInventory = false;
			return true;
		}
		
		synchronized(Orders) {
			for (Order o : Orders) {
				if(o.getState() == orderState.done) {
					PlateOrder(o);
					return true;
				}
			}
			for (Order o : Orders) {
				if (o.getState() == orderState.pending) {
					CookOrder(o);
					return true;
				}
			}
		}
		
		synchronized(marketOrders) {
			for(MarketOrder o : marketOrders) {
				if(o.state == marketOrderState.Verifying) {
					ConfirmOrder(o);
					return true;
				}
			}

			for(MarketOrder o : marketOrders) {
				if(o.state == marketOrderState.Done) {
					marketOrders.remove(o);
					return true;
				}
			}
		}
		

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void ArriveAtWork() {
		host.msgNewCook(this);
		cookGui.DoGoToCooking();
		state = cookState.Working;
	}
	
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
			for(Market m : markets) {
				List<String> orders = new ArrayList<String>();
				for(String c : choices) {
					if(!food.get(c).markets.contains(m)) {
						orders.add(c);
					}
				}
				
				if(!orders.isEmpty()){
					m.msgOrderFood(this, cashier, orders, 5);
					marketOrders.add(new MarketOrder(orders, 5));
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
	
	private void ConfirmOrder(MarketOrder o) {
		cashier.msgBillIsCorrect(o.choices, o.amount);
		o.state = marketOrderState.Done;
	}
	
	private void CollectPaycheck() {
		cookGui.DoGoToCashier();
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cashier.msgNeedPay(this);
		state = cookState.WaitingForPaycheck;
	}
	
	private void LeaveRestaurant() {
		DoLeaveRestaurant();
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getPersonAgent().msgRoleFinished();
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
	
	private void DoLeaveRestaurant() {
		host.msgCookLeaving(this);
		cookGui.DoLeaveRestaurant();		
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

		orderState getState() {
			return s;
		}
	}
	
	private class MarketOrder {
		List<String> choices;
		int amount;
		marketOrderState state;
		
		
		MarketOrder(List<String> choices, int amount) {
			this.choices = choices;
			this.amount = amount;
			this.state = marketOrderState.Ordered;
		}
	}
	
	private class Food{
		String name;
		int cookingTime;
		int supply;
		boolean ordered;
		List<Market> markets;
		
		Food(String n, int c, int s) {
			this.name = n;
			this.cookingTime = c;
			this.supply = s;
			this.ordered = false;
			markets = new ArrayList<Market>();
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

