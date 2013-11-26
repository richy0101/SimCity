package restaurant.stackRestaurant;

import agent.Role;
import gui.Building;

import java.util.*;
import java.util.concurrent.Semaphore;

import city.helpers.Directory;
import restaurant.stackRestaurant.helpers.Menu;
import restaurant.stackRestaurant.interfaces.Cook;
import restaurant.stackRestaurant.interfaces.Host;
import restaurant.stackRestaurant.interfaces.Market;
import restaurant.stackRestaurant.interfaces.Waiter;
import restaurant.stackRestaurant.gui.CookGui;

public class StackCookRole extends Role implements Cook {
	
	private List<MyOrder> orders = Collections.synchronizedList(new ArrayList<MyOrder>());
	private Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
	private List<MyMarket> markets = Collections.synchronizedList(new ArrayList<MyMarket>());
	private CookGui cookGui;
	String myLocation;
	Timer timer = new Timer();
	Host host;
	
	private Semaphore doneAnimation = new Semaphore(0,true);
	private enum AgentState 
	{Arrived, Working};
	AgentState state;
	
	private enum OrderState
	{Pending, Cooking, Done, Notified};
	
	private enum SharedOrderState
	{Checked, NeedsChecking};
	SharedOrderState sharedState = SharedOrderState.NeedsChecking;
	
	private enum FoodState
	{Empty, Ordered, Stocked, PermanentlyEmpty};
	
	
	public StackCookRole(String location) {
		super();
		foods.put("Steak", new Food(100));
		foods.put("Chicken", new Food(140));
		foods.put("Salad", new Food(70));
		foods.put("Pizza", new Food(120));
		cookGui = new CookGui(this);
		state = AgentState.Arrived;
		
		host = (Host) Directory.sharedInstance().getAgents().get("StackRestaurantHost");
		
		myLocation = location;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(cookGui);
			}
		}
	}
	
	public String getName() {
		return getPersonAgent().getName();
	}
	
	public void setGui(CookGui g) {
		cookGui = g;
	}
	
	public CookGui getGui() {
		return cookGui;
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		if(state == AgentState.Arrived) {
			tellHostAtWork();
			return true;
		}
		synchronized(orders) {
			for(MyOrder order : orders) {
				if(order.state == OrderState.Done) {
					print("plate it");
					plateIt(order);
					return true;
				}
			}
		}
		synchronized(orders) {
			for(MyOrder order : orders) {
				if(order.state == OrderState.Pending) {
					print("cook it");
					cookIt(order);
					return true;
				}
			}
		}
		
		synchronized(foods) {
			for(Map.Entry<String, Food> food : foods.entrySet()) {
				if(food.getValue().state == FoodState.Empty) {
					orderIt(food.getKey());
				}
			}
		}
		if(sharedState == SharedOrderState.NeedsChecking) {
			timer.schedule(new TimerTask() {
				public void run() {
					addSharedOrders();
					sharedState = SharedOrderState.NeedsChecking;
					stateChanged();
				}
			},
			5000);
			sharedState = SharedOrderState.Checked;
			return true;
		}
		return false;
	}
	
	//actions
	private void addSharedOrders() {
		Order order = Directory.sharedInstance().getRestaurants().get(0).getMonitor().remove();
		if(order != null) {
			orders.add(new MyOrder(order, OrderState.Pending));
		}
	}
	
	private void cookIt(final MyOrder order) {
		int cookingTime = foods.get(order.choice).cookingTime;
		int inventory = foods.get(order.choice).inventory;
		if(inventory == 0) {
			Menu.sharedInstance().setInventoryStock(order.choice, false);
			order.waiter.msgFoodEmpty(order.choice, order.table, order.seat);
			foods.get(order.choice).state = FoodState.Empty;
			order.state = OrderState.Notified;
			return;
		}
		else {
			foods.get(order.choice).inventory--;
		}
		cookGui.DoGoToFridge();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cookGui.DoGoToCookTop();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				cookGui.DoGoToPlatingArea();
				try {
					doneAnimation.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				print("Done cooking, cookie=" + cookie);
				order.state = OrderState.Done;
				stateChanged();
			}
		},
		cookingTime);
		order.state = OrderState.Cooking;
		stateChanged();
	}
	
	private void plateIt(MyOrder order) {
		order.state = OrderState.Notified;
		order.waiter.msgOrderDone(order.choice, order.table, order.seat);	
	}
	
	private void orderIt(String choice) {
		for(MyMarket market : markets) {
			if(market.foodStock.get(choice)) {
				print("finding food from market: " + market.market);
				market.market.msgOrderFood(this, choice);
				foods.get(choice).state = FoodState.Ordered;
				return;
			}
		}
		foods.get(choice).state = FoodState.PermanentlyEmpty;
		
	}
	
	private void tellHostAtWork() {
		host.msgAddCook(this);
		cookGui.DoGoHome();
		state = AgentState.Working;
	}

		
	//messages
	public void msgCheckOrders() {
		sharedState = SharedOrderState.NeedsChecking;
		stateChanged();
	}
	public void msgCookOrder(Waiter waiter, String choice, int table, int seat) {
		orders.add(new MyOrder(waiter, choice, table, seat, OrderState.Pending));
		print("cook order");
		stateChanged();
	}
	
	public void msgInventoryOut(Market market, String choice) {
		foods.get(choice).state = FoodState.Empty;
		for(MyMarket mMarket : markets) {
			if(market.equals(mMarket.market)) {
				mMarket.foodStock.put(choice, false);
			}
		}
		print("There's no more of " + choice);
		stateChanged();
	}
	
	public void msgMarketDeliveringOrder(int inventory, String choice) {
		foods.get(choice).inventory = inventory;
		foods.get(choice).state = FoodState.Stocked;
		Menu.sharedInstance().setInventoryStock(choice, true);
		print("Food " + choice + " arrived");
	}
	
	public void msgAddMarket(Market market) {
		markets.add(new MyMarket(market));
		stateChanged();
	}
	
	public void msgAtCooktop() {
		doneAnimation.release();
	}

	public void msgAtPlating() {
		doneAnimation.release();
	}
	
	public void msgAtFridge() {
		doneAnimation.release();
	}
	
	private class MyOrder {
		MyOrder(Waiter waiter, String choice, int table, int seat, OrderState state) {
			this.waiter = waiter;
			this.choice = choice;
			this.table = table;
			this.seat = seat;
			this.state = state;
		}
		MyOrder(Order order, OrderState state) {
			waiter = order.waiter;
			choice = order.choice;
			table = order.table;
			seat = order.seat;
			this.state = state;
		}
		Waiter waiter;
		String choice;
		int table;
		int seat;
		OrderState state;
	}
	
	private class Food {
		public Food(int cookingTime) {
			this.cookingTime = cookingTime;
		}
		int cookingTime;
		int inventory = 1000;
		FoodState state;
	}
	
	private class MyMarket {
		public MyMarket(Market market) {
			this.market = market;
		}
		Market market;
		@SuppressWarnings("serial")
		Map<String, Boolean> foodStock = new HashMap<String, Boolean>() {
			{
				put("Steak", true);
				put("Chicken", true);
				put("Salad", true);
				put("Pizza", true);
			}
		};
	}
}
