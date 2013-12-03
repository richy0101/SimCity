package restaurant;

import agent.Agent;
import restaurant.gui.CookGui;
import restaurant.gui.FoodData;
import restaurant.gui.Menu;
import restaurant.gui.Order;
import restaurant.gui.WaiterGui;
import restaurant.gui.Order.OrderCookState;
import restaurant.gui.Bill;
import restaurant.gui.Order.OrderMarketState;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Cook;
import restaurant.interfaces.Market;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Cook Agent
 */
public class CookAgent extends Agent implements Cook {
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	Timer timer = new Timer();
	Menu menu = new Menu();
	private String name;
	Bill bill;
	private Semaphore atPlating = new Semaphore(0, true);
	private Semaphore atCooking = new Semaphore(0, true);
	
	public CookGui cookGui = null;

	
	//FOODDATA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//CHANGE QUANTITY OF COOK AGENT
	FoodData steak = new FoodData("Steak", 30, 5000, 1); //CHANGE THE THIRD PARAMETER (QUANTITY): FoodData(Price, CookTime, QUANTITY)
	FoodData chicken = new FoodData("Chicken", 20, 5000, 1); 
	FoodData fish = new FoodData("Fish", 25, 5000, 1);
	FoodData vegetarian = new FoodData("Vegetarian", 20, 5000, 1);

	private Map<String, FoodData> restaurantInventory = new HashMap<String, FoodData>(); {
		restaurantInventory.put("Steak", steak);
		restaurantInventory.put("Chicken", chicken);
		restaurantInventory.put("Fish", fish);
		restaurantInventory.put("Vegetarian", vegetarian);
	}
	
	private Market market1, market2; 
	private Cashier cashier;

	public CookAgent(String n, Market m1, Market m2) {
		super();

		name = n;
		market1 = m1;
		market2 = m2;
	}

	// tMessages
	public void msgCookThisOrder(WaiterAgent w, String o, int t, Cashier ca) {
		cashier = ca;
		
		print("Received order");
		orders.add(new Order(w, o, t, OrderCookState.Pending));
		stateChanged();
	}
	
	public void msgfoodDone(Order o) {
		o.cs = OrderCookState.Done;
		stateChanged();
	}
	
	public void msgHereIsReplenishment(Order o, int quantity) {
		//update inventory
		
		for(int i = 0; i < o.list.size(); i++) {
			restaurantInventory.get(o.list.get(i)).quantity = restaurantInventory.get(o.list.get(i).toString()).quantity + 4;
			print("Item: " + restaurantInventory.get(o.list.get(i)).name + "| Quantity: " + restaurantInventory.get(o.list.get(i)).quantity);
		}
		
		print("Shipment from market received!");
	}
	
	public void msgCannotFulfillOrder(String o) {
		print("Need to order " + o + " from another market.");
		orders.add(new Order(o, OrderCookState.Ordering));
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
	protected boolean pickAndExecuteAnAction() {
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
		Vector<String> lowItems = new Vector<String>();
		
		if(restaurantInventory.get("Steak").quantity <= 1) {
			lowItems.add("Steak");
		}
		if(restaurantInventory.get("Chicken").quantity <= 1) {
			lowItems.add("Chicken");
		}
		if(restaurantInventory.get("Fish").quantity <= 1) {
			lowItems.add("Fish");
		}
		if(restaurantInventory.get("Vegetarian").quantity <= 1) {
			lowItems.add("Vegetarian");
		}

		
		//send order
		if(lowItems.size() > 0) {
			print("We have low inventory, must order from market.");
			market1.msgOrderForReplenishment(lowItems, this, cashier);
		}
		else
			print("We have plenty of inventory.");
		
		
	}
	
	private void ReOrder(Order o) {
		print("Reordering from another market.");
		Vector<String> neededItem = new Vector<String>();
		neededItem.add(o.o);
		market2.msgOrderForReplenishment(neededItem, this, cashier);
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
