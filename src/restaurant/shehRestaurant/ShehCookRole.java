package restaurant.shehRestaurant;

import agent.Agent;
import agent.Role;
import restaurant.CookRole;
import restaurant.shehRestaurant.gui.CookGui;
import restaurant.shehRestaurant.helpers.FoodData;
import restaurant.shehRestaurant.helpers.Menu;
import restaurant.shehRestaurant.helpers.Order;
import restaurant.shehRestaurant.gui.WaiterGui;
import restaurant.shehRestaurant.helpers.Order.OrderCookState;
import restaurant.shehRestaurant.helpers.Bill;
import restaurant.shehRestaurant.helpers.Order.OrderMarketState;
import restaurant.shehRestaurant.interfaces.Cashier;
import restaurant.shehRestaurant.interfaces.Cook;
import gui.Building;
import gui.Gui;

import java.util.*;
import java.util.concurrent.Semaphore;

import market.Market;
import city.helpers.Directory;

/**
 * Restaurant Cook Agent
 */
public class ShehCookRole extends CookRole implements Cook {
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	Timer timer = new Timer();
	Menu menu = new Menu();
	private String name;
	Bill bill;
	private Semaphore atPlating = new Semaphore(0, true);
	private Semaphore atCooking = new Semaphore(0, true);
	
	public CookGui cookGui = null;
	public ShehHostAgent host;
	private Market market1, market2; 
	private Cashier cashier;
	
	private enum AgentState 
	{NeedsToWork, Arrived, Working, GettingPaycheck, Leaving, WaitingForPaycheck};
	AgentState state = AgentState.NeedsToWork;

	
	//FOODDATA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//CHANGE QUANTITY OF COOK AGENT
	FoodData steak = new FoodData("Steak", 30, 5000, 10); //CHANGE THE THIRD PARAMETER (QUANTITY): FoodData(Price, CookTime, QUANTITY)
	FoodData chicken = new FoodData("Chicken", 20, 5000, 10); 
	FoodData pizza = new FoodData("Pizza", 25, 5000, 10);
	FoodData salad = new FoodData("Salad", 20, 5000, 10);

	private Map<String, FoodData> restaurantInventory = new HashMap<String, FoodData>(); {
		restaurantInventory.put("Steak", steak);
		restaurantInventory.put("Chicken", chicken);
		restaurantInventory.put("Pizza", pizza);
		restaurantInventory.put("Salad", salad);
		

	}


	public ShehCookRole(String location) {
		super();
		host = (ShehHostAgent) Directory.sharedInstance().getAgents().get("ShehRestaurantHost");
		cashier = (Cashier) Directory.sharedInstance().getAgents().get("ShehRestaurantCashier");
		//instantiate markets
		
		cookGui = new CookGui(this);
		market1 = Directory.sharedInstance().getMarkets().get(0);
		market2 = Directory.sharedInstance().getMarkets().get(1);
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		
		for(Building b : buildings) {
			if (b.getName() == location) {
				b.addGui(cookGui);
			}
		}
		
		state = AgentState.Arrived;
	}
		
		
	public ShehCookRole(String n, Market m1, Market m2) {
		super();

		name = n;
		market1 = m1;
		market2 = m2;
	}

	// tMessages
	public void msgCookThisOrder(ShehWaiterRole w, String o, int t, Cashier ca) {
		cashier = ca;
		
		print("Received order");
		orders.add(new Order(w, o, t, OrderCookState.Pending));
		stateChanged();
	}
	
	public void msgfoodDone(Order o) {
		o.cs = OrderCookState.Done;
		stateChanged();
	}
	
	public void msgMarketDeliveringOrder(Order o, int quantity) {
		//update inventory
		
		for(int i = 0; i < o.list.size(); i++) {
			restaurantInventory.get(o.list.get(i)).quantity = restaurantInventory.get(o.list.get(i).toString()).quantity + 4;
			print("Item: " + restaurantInventory.get(o.list.get(i)).name + "| Quantity: " + restaurantInventory.get(o.list.get(i)).quantity);
		}
		
		print("Shipment from market received!");
	}
	
	public void msgInventoryOut(List<String> order) {
		print("Need to order " + order + " from another market.");
		orders.add(new Order(order, OrderCookState.Ordering));
		stateChanged();
	}
	
	public void msgCooking() {
		atCooking.release();
		stateChanged();
	}
	
	public void msgPlating() {
		atPlating.release();
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		if(state == AgentState.Arrived) {
			ClockInWithHost();
		}
		
		for (Order o : orders) {
			if (o.cs == OrderCookState.Pending) {
				CookingOrder(o);
				return true;
			}
		}
		for (Order o : orders) {
			if (o.cs == OrderCookState.Done) {
				PlaceOrder(o);
				return true;
			}
		}
		for (Order o : orders) {
			if (o.cs == OrderCookState.Ordering) {
				ReOrder(o);
				return true;
			}
		}
		
		//StandBy();
		cookGui.DoStandby();
		return false;
	}

	// Actions
	private void ClockInWithHost() {
		host.msgCookIsPresent(this); 
		cookGui.DoStandby();
		state = AgentState.Working;
	}
	
	private void CookingOrder(final Order o)	{
		if(restaurantInventory.get(o.o).quantity == 0) {
			print("Out of this order");
			o.w.msgOutOfFood(o.t, o.o);
			orders.remove(o);
		}
		else if(restaurantInventory.get(o.o).quantity == 1){
			CheckInventory();
			
			//standard cooking procedure
			cookGui.DoCooking();
			try {
				atCooking.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			o.cs = OrderCookState.Nothing;
			restaurantInventory.get(o.o).quantity--;
			stateChanged();
			 (timer).schedule(new TimerTask() {
				public void run() {
					msgfoodDone(o);
				}
			}, restaurantInventory.get(o.o).cookTime);
			 
		}
		else {
			cookGui.DoCooking();
			try {
				atCooking.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			o.cs = OrderCookState.Nothing;
			restaurantInventory.get(o.o).quantity--;
			stateChanged();
			 (timer).schedule(new TimerTask() {
				public void run() {
					msgfoodDone(o);
				}
			}, restaurantInventory.get(o.o).cookTime);
		}
		 //atCooking.release();
		 //stateChanged();
	}
	
	private void StandBy() {
		CheckInventory();
		cookGui.DoStandby();
	}
	
	private void CheckInventory() {
		print("Checking inventory.");
		
		//search inventory for low items
		List<String> lowItems = new ArrayList<String>();
		
		if(restaurantInventory.get("Steak").quantity <= 1) {
			lowItems.add("Steak");
		}
		if(restaurantInventory.get("Chicken").quantity <= 1) {
			lowItems.add("Chicken");
		}
		if(restaurantInventory.get("Pizza").quantity <= 1) {
			lowItems.add("Fish");
		}
		if(restaurantInventory.get("Salad").quantity <= 1) {
			lowItems.add("Vegetarian");
		}

		
		//send order
		if(lowItems.size() > 0) {
			print("We have low inventory, must order from market.");
			if(market1.isOpen())
				market1.getWorker().msgOrderFood(this, cashier, lowItems, 5);
			else if(market2.isOpen())
				market1.getWorker().msgOrderFood(this, cashier, lowItems, 5);
			else
				print("All markets closed.");
		}
		else
			print("We have plenty of inventory.");
		
		
	}
	
	private void ReOrder(Order o) {
		print("Reordering from another market.");
		List<String> neededItem = new ArrayList<String>();
		neededItem.add(o.o);
		if(market2.isOpen())
			market2.getWorker().msgOrderFood(this, cashier, neededItem, 5);
		else
			print("market2 closed");
		o.cs = OrderCookState.Nothing;
	}
	
	private void PlaceOrder(Order o) {
		//DoPlacement(order); //animation
		cookGui.DoPlating();		
		try {
			atPlating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		print("Order is cooked");
		o.w.msgOrderIsCooked(o.t, o.o);
		orders.remove(o);
	}

	public void setGui(CookGui gui) {
		cookGui = gui;
	}

	public CookGui getGui() {
		return cookGui;
	}

}
