package market;

import gui.Building;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

import city.PersonAgent;
import city.helpers.Directory;
import agent.Role;
import market.test.mock.EventLog;
import market.test.mock.LoggedEvent;
import market.gui.MarketGui;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MarketRole extends Role implements Market {

	//data--------------------------------------------------------------------------------
	public enum orderState {Ordered, CantFill, Filled, Billed, Paid, CantPay};
	
	List<Order> MyOrders;
	boolean jobDone;
	Map<String, Food> inventory = new HashMap<String, Food>();
	

	private Semaphore actionComplete = new Semaphore(0,true);
	private MarketGui gui;
	private String myLocation;
	
	public EventLog log;
	
	public class Order {
	    MarketCustomer customer;
	    Map<String, Integer> groceryList = new HashMap<String, Integer>();
	    Map<String, Integer> retrievedGroceries = new HashMap<String, Integer>();
	    orderState state;
	    double price;
	    
	    Order(MarketCustomer c, Map<String, Integer> gl) {
	    	customer = c;
	    	groceryList = gl;
	    	state = orderState.Ordered;
	    }
	    
	    public MarketCustomer getCustomer() {
	    	return customer;
	    }
	    
	    public Map<String, Integer> getGroceryList() {
	    	return groceryList;
	    }

		public Map<String, Integer> getRetrievedGroceries() {
			return retrievedGroceries;
		}
	    
	    public orderState getState() {
	    	return state;
	    }

		public double getPrice() {
			return price;
		}
	}

	public class Food {
	    String name;
	    int supply;
	    double price;
	    
	    Food(String n, int s, double p) {
	    	name = n;
	    	supply = s;
	    	price = p;
	    }
	}
	
	public MarketRole(String location) {
		inventory.put("Chicken", new Food("Chicken", 10, 1.00));
		inventory.put("Steak", new Food("Steak", 10, 2.00));
		inventory.put("Pizza", new Food("Pizza", 10, 3.00));
		inventory.put("Salad", new Food("Salad", 10, 4.00));
		
		MyOrders = Collections.synchronizedList(new ArrayList<Order>());
		jobDone = false;
		log = new EventLog();
		
		gui = new MarketGui(this);

		myLocation = location;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(gui);
			}
		}
	}
	
	public List<Order> getMyOrders() {
		return MyOrders;
	}
	public Map<String, Food> getInventory() {
		return inventory;
	}
	
	//messages----------------------------------------------------------------------------
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList) {
		print("Received msgGetGroceries");
		
	    MyOrders.add(new Order(customer, groceryList));
	    
	    log.add(new LoggedEvent("Received msgGetGroceries from MarketCustomer."));
	    
	    stateChanged();
	}
	
	public void msgHereIsMoney(MarketCustomer customer, double money) {
		print("Received msgHereIsMoney");
		
		synchronized(MyOrders){
		    for(Order o : MyOrders) {
		    	if(o.customer == customer)
		    		o.state = orderState.Paid;  
		    }
		}
	    
	    log.add(new LoggedEvent("Received msgHereIsMoney from MarketCustomer. Amount = $" + money));
	    stateChanged();
	}
	
	public void msgCantAffordGroceries(MarketCustomer customer) {
		print("Received msgCantAffordGroceries");
		
		synchronized(MyOrders) {
			for(Order o : MyOrders) {
				if(o.customer == customer)
					o.state = orderState.CantPay;
			}
		}
		
	    log.add(new LoggedEvent("Received msgCantAffordGroceries from MarketCustomer."));
	    stateChanged();
	}
	
	public void msgJobDone() {
		print("Received msgJobDone");
		
		jobDone = true;
		stateChanged();
	}
	
	public void msgActionComplete() {
		actionComplete.release();
		stateChanged();
	}
	
	//scheduler---------------------------------------------------------------------------
	public boolean pickAndExecuteAnAction() {
		if(MyOrders.isEmpty() && jobDone == true) {
			LeaveJob();
		}		
		synchronized(MyOrders) {
			for(Order o : MyOrders) {
				if(o.state == orderState.Ordered) {
					if(jobDone == false)
						FillOrder(o);
					else
						TurnAwayCustomer(o);
					return true;
				}
			}
			
			for(Order o : MyOrders) {
				if(o.state == orderState.CantFill) {
					TurnAwayCustomer(o);
					return true;
				}
			}
			
			for(Order o : MyOrders) {
				if(o.state == orderState.Filled) {
					BillCustomer(o);
					return true;
				}
			}
			
			for(Order o : MyOrders) {
				if(o.state == orderState.Paid) {
						GiveGroceries(o);
					return true;
				}
			}
			
			for(Order o : MyOrders) {
				if(o.state == orderState.CantPay) {
						MyOrders.remove(o);
					return true;
				}
			}
		}
		
		return false;
	}
	
	//actions-----------------------------------------------------------------------------
	private void FillOrder(Order o) {
		Iterator<String> i = o.groceryList.keySet().iterator();
		String choice;
		int amount;
		
	    while(i.hasNext()) {
	    	choice = (String) i.next();
	    	amount = o.groceryList.get(choice);
	    	
	    	if(inventory.get(choice).supply >= amount) {
	    		o.price += inventory.get(choice).price * amount;
	    		o.retrievedGroceries.put(choice, amount);
	    		
	    		DoGetItem(choice); //GUI
	    		try {
	    			actionComplete.acquire();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		
	    		inventory.get(choice).supply -= amount;
	    	}
	    }
	    
	    DoGoToCounter();
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	    if(o.retrievedGroceries.isEmpty())
	        o.state = orderState.CantFill;
	    else
	        o.state = orderState.Filled;
	    
	    log.add(new LoggedEvent("Got MarketCustomer order."));
	}
	private void TurnAwayCustomer(Order o) {
	    o.customer.msgCantFillOrder(o.groceryList);
	    MyOrders.remove(o);
	    
	    log.add(new LoggedEvent("Couldn't fill MarketCustomer's order."));
	}
	private void BillCustomer(Order o) {
	    o.customer.msgHereIsBill(o.price);
	    o.state = orderState.Billed;

	    log.add(new LoggedEvent("Sent MarketCustomer the bill."));
	}
	private void GiveGroceries(Order o) {
	    o.customer.msgHereAreYourGroceries(o.retrievedGroceries);
	    MyOrders.remove(o);
	    
	    log.add(new LoggedEvent("Gave MarketCustomer the groceries."));
	}
	private void LeaveJob() {
		getPersonAgent().msgRoleFinished();
	}

	//GUI Actions-------------------------------------------------------------------------
	private void DoGetItem(String s) {
		gui.DoGetFood();		
	}
	
	private void DoGoToCounter() {
		gui.DoGoToCounter();
	}
}
