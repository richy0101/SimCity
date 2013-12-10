package restaurant.stackRestaurant;

import gui.Building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import market.interfaces.Market;

import restaurant.CookRole;
import restaurant.stackRestaurant.gui.CookGui;
import restaurant.Restaurant;
import restaurant.FoodInformation;
import restaurant.stackRestaurant.helpers.Menu;
import restaurant.stackRestaurant.interfaces.Cashier;
import restaurant.stackRestaurant.interfaces.Cook;
import restaurant.stackRestaurant.interfaces.Host;
import restaurant.stackRestaurant.interfaces.Waiter;
import city.helpers.Directory;

public class StackCookRole extends CookRole implements Cook {
	
	private List<MyOrder> orders = Collections.synchronizedList(new ArrayList<MyOrder>());
	private Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
	private List<MyMarket> markets = Collections.synchronizedList(new ArrayList<MyMarket>());
	private CookGui cookGui;
	String myLocation;
	Timer timer = new Timer();
	Host host;
	Market market1;
	Cashier cashier;
	Restaurant restaurant = Directory.sharedInstance().getRestaurants().get(0);
	
	private Semaphore doneAnimation = new Semaphore(0,true);
	private enum AgentState 
	{Arrived, Working, GettingPaycheck, Leaving, WaitingForPaycheck};
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
		FoodInformation steak = new FoodInformation(6000, 100);
		foods.put("Steak", new Food(steak));
		restaurant.getFoodInventory().put("Steak", steak);
		
		FoodInformation chicken = new FoodInformation(4000, 100);
		foods.put("Chicken", new Food(chicken));
		restaurant.getFoodInventory().put("Chicken", chicken);
		
		FoodInformation salad = new FoodInformation(7000, 100);
		foods.put("Salad", new Food(salad));
		restaurant.getFoodInventory().put("Salad", salad);
		
		FoodInformation pizza = new FoodInformation(12000, 100);
		foods.put("Pizza", new Food(pizza));
		restaurant.getFoodInventory().put("Pizza", pizza);
		
		cookGui = new CookGui(this);
		state = AgentState.Arrived;
		
		host = (Host) Directory.sharedInstance().getAgents().get("StackRestaurantHost");
		cashier = (Cashier) Directory.sharedInstance().getRestaurants().get(0).getCashier();
		market1 = (Market) Directory.sharedInstance().marketDirectory.get("Market").getWorker();
		markets.add(new MyMarket(market1));
		
		
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
		if(state == AgentState.GettingPaycheck) {
			goGetPaycheck();
			return true;
		}
		if(state == AgentState.Leaving) {
			leaveRestaurant();
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
					return true;
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
		int cookingTime = foods.get(order.choice).information.getCookTime();
		int inventory = foods.get(order.choice).information.getQuantity();
		if(inventory == 0) {
			Menu.sharedInstance().setInventoryStock(order.choice, false);
			order.waiter.msgFoodEmpty(order.choice, order.table, order.seat);
			foods.get(order.choice).state = FoodState.Empty;
			order.state = OrderState.Notified;
			return;
		}
		else {
			int quantity = restaurant.getFoodInventory().get(order.choice).getQuantity();
			restaurant.getFoodInventory().get(order.choice).setQuantity(quantity--);;
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
			if(market.market != null) {
				if(market.foodStock.get(choice)) {
					print("finding food from market: " + market.market);
					market.market.msgOrderFood(this, cashier, choice);
					foods.get(choice).state = FoodState.Ordered;
					return;
				}
			}
		}
		foods.get(choice).state = FoodState.PermanentlyEmpty;
		
	}
	
	private void tellHostAtWork() {
		host.msgAddCook(this);
		cookGui.DoGoHome();
		state = AgentState.Working;
		
		//TEST REMOVE ME
		orderIt("Steak");
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
		cookGui.DoGoToPaycheck();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cashier.msgNeedPaycheck(this);
		state = AgentState.WaitingForPaycheck;
	}

	private void DoLeaveRestaurant() {
		host.msgCookLeaving(this);
		cookGui.DoExitRestaurant();
	}
		
	//messages
	public void msgHereIsPaycheck(double funds) {
		getPersonAgent().setFunds(getPersonAgent().getFunds() + funds);
		state = AgentState.Leaving;
		stateChanged();
	}
	
	public void msgJobDone() {
		state = AgentState.GettingPaycheck;
		stateChanged();
		
	}
	
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
		foods.get(choice).information.setQuantity(inventory);
		restaurant.msgChangeFoodInventory(choice, inventory);
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
	
	public void msgAtCashier() {
		doneAnimation.release();
	}
	public void msgAnimationFinishedLeavingRestaurant() {
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
	
	public class Food {
		FoodInformation information;
		public Food(FoodInformation information) {
			this.information = information;
		}
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
