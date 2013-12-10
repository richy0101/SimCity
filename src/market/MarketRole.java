package market;

import gui.Building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import market.gui.MarketGui;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;
import market.test.mock.EventLog;
import market.test.mock.LoggedEvent;
import restaurant.CashierInterface;
import restaurant.CookInterface;
import restaurant.Restaurant;
import agent.Role;
import city.TransportationRole;
import city.helpers.Directory;

public class MarketRole extends Role implements Market {

	//data--------------------------------------------------------------------------------
	public enum orderState {Ordered, CantFill, Filled, Billed, ReadyToDeliver, Paid, CantPay, Cancelled, InTransit};
	
	List<Order> MyOrders;
	List<RestaurantOrder> MyRestaurantOrders;
	boolean atWork;
	boolean deliverOrders;
	boolean jobDone;
	Map<String, Food> inventory = new HashMap<String, Food>();
	double funds;
	Timer timer = new Timer();


	private Semaphore actionComplete = new Semaphore(0,true);
	private MarketGui gui;
	private String myLocation;
	
	public EventLog log;
	
	public class RestaurantOrder {
		CookInterface cook;
		CashierInterface cashier;
		String choice;
		int amount;
		double price;
		orderState state;
		
		RestaurantOrder(CookInterface cook, CashierInterface cashier, String choice, int amount) {
			this.cook = cook;
			this.cashier = cashier;
			this.choice = choice;
			this.amount = amount;
			state = orderState.Ordered;
		}

		public orderState getState() {
			return state;
		}

		public CookInterface getCook() {
			return cook;
		}

		public String getChoice() {
			return choice;
		}

		public double getPrice() {
			return price;
		}
	}
	
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
	    private int supply;
	    double price;
	    
	    Food(String n, int s, double p) {
	    	name = n;
	    	setSupply(s);
	    	price = p;
	    }

		public int getSupply() {
			return supply;
		}

		public void setSupply(int supply) {
			this.supply = supply;
		}
	}
	
	public MarketRole(String location) {
		inventory.put("Chicken", new Food("Chicken", 10, 1.00));
		inventory.put("Steak", new Food("Steak", 10, 2.00));
		inventory.put("Pizza", new Food("Pizza", 10, 3.00));
		inventory.put("Salad", new Food("Salad", 10, 4.00));
		
		MyOrders = Collections.synchronizedList(new ArrayList<Order>());
		MyRestaurantOrders = Collections.synchronizedList(new ArrayList<RestaurantOrder>());
		
		jobDone = false;
		atWork = false;
		funds = 0.00;
		
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
	
	public MarketRole() {
		inventory.put("Chicken", new Food("Chicken", 10, 1.00));
		inventory.put("Steak", new Food("Steak", 10, 2.00));
		inventory.put("Pizza", new Food("Pizza", 10, 3.00));
		inventory.put("Salad", new Food("Salad", 10, 4.00));
		
		MyOrders = Collections.synchronizedList(new ArrayList<Order>());
		MyRestaurantOrders = Collections.synchronizedList(new ArrayList<RestaurantOrder>());
		jobDone = false;
		atWork = false;
		funds = 0.00;
		log = new EventLog();
		
		gui = new MarketGui(this);
	}
		
	
	public List<Order> getMyOrders() {
		return MyOrders;
	}
	public List<RestaurantOrder> getMyRestaurantOrders() {
		return MyRestaurantOrders;
	}
	public Map<String, Food> getInventory() {
		return inventory;
	}
	public boolean getJobDone() {
		return jobDone;
	}
	public double getFunds() {
		return funds;
	}
	
	//MarketCustomer messages-------------------------------------------------------------
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
		
		funds += money;
	    
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
	
	//Restaurant messages-------------------------------------------------------------
	public void msgOrderFood(CookInterface cook, CashierInterface cashier, String choice, int amount) {
		MyRestaurantOrders.add(new RestaurantOrder(cook, cashier, choice, amount));
		
		log.add(new LoggedEvent("Received msgOrderFood from Cook. Choice = " + choice));
		stateChanged();
	}
	
		/*No amount given*/
	public void msgOrderFood(CookInterface cook, CashierInterface cashier, String choice) {
		MyRestaurantOrders.add(new RestaurantOrder(cook, cashier, choice, 5));
		
		log.add(new LoggedEvent("Received msgOrderFood from Cook. Choice = " + choice));
		stateChanged();
	}
	
	public void msgDeliverOrder(RestaurantOrder o) {
		o.state = orderState.ReadyToDeliver;
		
		log.add(new LoggedEvent("Received msgDeliverOrder from Timer."));
		stateChanged();
	}
	
	public void msgPayForOrder(CashierInterface cashier, double funds) {
		this.funds += funds;
		
		synchronized(MyRestaurantOrders) {
			for(RestaurantOrder o : MyRestaurantOrders) {
				if(o.cashier == cashier)
					o.state = orderState.Paid;
			}
		}
		
		log.add(new LoggedEvent("Receieved msgPayFor Order from Cashier. Amount = $" + funds));
		stateChanged();
	}
	
	public void msgCannotPay(CashierInterface cashier, double funds) {
		
		synchronized(MyRestaurantOrders) {
			for(RestaurantOrder o : MyRestaurantOrders) {
				if(o.cashier == cashier)
					o.state = orderState.CantPay;
			}
		}
		
		log.add(new LoggedEvent("Receieved msgPayFor Order from Cashier. Amount = $" + funds));
		stateChanged();		
	}
	
	//Huang Restaurant messages----------------------------------------------------------
	public void msgCancelOrder(CookInterface cook) {
		synchronized(MyRestaurantOrders) {
			for (RestaurantOrder o : MyRestaurantOrders) {
				if(o.cook == cook) {
					o.state = orderState.Cancelled;
				}
			}
		}
		stateChanged();
	}	

	//Person/GUI messages-------------------------------------------------------------
	public void msgJobDone() {
		print("Received msgJobDone");
		jobDone = true;
		deliverOrders = true;
	    log.add(new LoggedEvent("Received msgJobDone from Person."));
		stateChanged();
	}
	
	public void msgActionComplete() {
		actionComplete.release();
		stateChanged();
	}
	
	//scheduler---------------------------------------------------------------------------
	public boolean pickAndExecuteAnAction() {
		if(atWork == false) {
			ArriveAtJob();
			return true;
		}
		
		if(MyOrders.isEmpty() && MyRestaurantOrders.isEmpty() && jobDone == true) {
			LeaveJob();
			return true;
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
					
					log.add(new LoggedEvent("Deleting order."));
					return true;
					
				}
			}
		}
		if (deliverOrders == true) {
			synchronized(MyRestaurantOrders) {
				for(RestaurantOrder o : MyRestaurantOrders) {
					if(o.state == orderState.Paid || o.state == orderState.Cancelled) {
						MyRestaurantOrders.remove(o);
						
						log.add(new LoggedEvent("Removing RestaurantOrder."));
						return true;
					}
				}
				for(RestaurantOrder o : MyRestaurantOrders) {
					if (o.state == orderState.InTransit) {
						DeliverOrder(o);
						return true;
					}
				}	
				for(RestaurantOrder o : MyRestaurantOrders) {
					if(o.state == orderState.ReadyToDeliver) {
						DriveToOrder(o);
						return true;
					}
				}
				for(RestaurantOrder o : MyRestaurantOrders) {
					if(o.state == orderState.Ordered) {
						FillRestaurantOrder(o);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//Customer Order actions--------------------------------------------------------------
	private void FillOrder(Order o) {
		Iterator<String> i = o.groceryList.keySet().iterator();
		String choice;
		int amount;
		
	    while(i.hasNext()) {
	    	choice = (String) i.next();
	    	amount = o.groceryList.get(choice);
	    	
	    	if(inventory.get(choice).getSupply() >= amount) {
	    		o.price += inventory.get(choice).price * amount;
	    		o.retrievedGroceries.put(choice, amount);
	    		
	    		DoGetItem(choice); //GUI
	    		try {
	    			actionComplete.acquire();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		
	    		inventory.get(choice).setSupply(inventory.get(choice).getSupply() - amount);
	    	}
	    }
		
	    if(o.retrievedGroceries.isEmpty())
	        o.state = orderState.CantFill;
	    else {
	        o.state = orderState.Filled;
	        
	        DoGoToCounter();
			try {
				actionComplete.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
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

	//Restaurant Order actions--------------------------------------------------------------
	private void FillRestaurantOrder(final RestaurantOrder o) {
		if(inventory.get(o.choice).getSupply() >= o.amount) {
			o.state = orderState.Filled;
			o.cook.msgCanFillOrder(this, o.choice);
			log.add(new LoggedEvent("Filling Restaurant Order."));
			timer.schedule(new TimerTask() {
				public void run() {
					msgDeliverOrder(o);
				}
			},
			3000);
		}
		else {
			log.add(new LoggedEvent("Can't fill RestaurantOrder."));
			o.cook.msgInventoryOut(this, o.choice);
			MyRestaurantOrders.remove(o);
		}
	}
	
	private void DriveToOrder(RestaurantOrder o) {
		o.state = orderState.InTransit;
		inventory.get(o.choice).setSupply(inventory.get(o.choice).getSupply() - o.amount);
		o.price = o.amount * inventory.get(o.choice).price;
		
		String orderLocation = null;
		List<Restaurant> restaurants = Directory.sharedInstance().getRestaurants();
		for (Restaurant r : restaurants) {
			if (r.getCashier() == o.cashier) {
				orderLocation = r.getName();
				break;
			}
		}
		Role t = new TransportationRole(orderLocation, getPersonAgent().getCurrentLocation());
		getPersonAgent().addRole(t);
	}
	private void DeliverOrder(RestaurantOrder o) {
		o.state = orderState.Billed;
		o.cook.msgMarketDeliveringOrder(inventory.get(o.choice).getSupply(), o.choice);
		o.cashier.msgGiveBill(new MarketCheck(o.price, o.choice, this));
		
		log.add(new LoggedEvent("Delivered order."));
	}
	
	//PersonAgent actions----------------------------------------------------------------
	private void ArriveAtJob() {
		DoEnterMarket();
		
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Directory.sharedInstance().marketDirectory.get(myLocation).setOpen();
		atWork = true;
	}
	private void LeaveJob() {
		log.add(new LoggedEvent("MarketRole leaving job"));
		getPersonAgent().setFunds(getPersonAgent().getFunds() + funds);
		Directory.sharedInstance().marketDirectory.get(myLocation).setClosed();
		
		getPersonAgent().msgRoleFinished();
	}

	//GUI Actions-------------------------------------------------------------------------
	private void DoEnterMarket() {
		gui.setPresent();
		gui.DoEnterMarket();
	}
	private void DoGetItem(String s) {
		gui.DoGetFood();		
	}
	
	private void DoGoToCounter() {
		gui.DoGoToCounter();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
