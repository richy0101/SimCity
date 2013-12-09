package restaurant.nakamuraRestaurant;

import agent.Agent;
import restaurant.CustomerAgent.AgentEvent;
import restaurant.gui.HostGui;
import restaurant.interfaces.Market;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Market Agent
 */
public class MarketAgent extends Agent implements Market{
	public List<Order> Orders
	= Collections.synchronizedList(new ArrayList<Order>());
	
	private Map<String, Food> food = new HashMap<String, Food>();

	public enum state {pending, filling, done};
	private String name;
	Timer timer = new Timer();

//	public HostGui hostGui = null;
//  private List<WaiterAgent> waiters = new ArrayList<WaiterAgent>();

	public MarketAgent(String name, int st, int ch, int sa, int pz) {
		super();

		this.name = name;
		food.put("Steak", new Food("Steak", st, 15.00));
		food.put("Chicken", new Food("Chicken", ch, 10.00));
		food.put("Salad", new Food("Salad", sa, 10.00));
		food.put("Pizza", new Food("Pizza", pz, 5.00));
	}

	public String getMarketName() {
		return name;
	}

	public String getName() {
		return name;
	}
	
	public void emptyInventory() {
		food.get("Steak").empty();
		food.get("Chicken").empty();
		food.get("Salad").empty();
		food.get("Pizza").empty();
	}
//	public void addWaiter(WaiterAgent w) {
//		waiters.add(w);
//	}

	// Messages

	public void msgNewOrder(NakamuraCookRole cook, NakamuraCashierAgent cashier, List<String> choice, int amount) {
		print("Received msgNewOrder");
		Orders.add(new Order(cook, cashier, choice, amount));
		stateChanged();
	}

	public void msgOrderDone(Order o) {
		print("Received msgOrderDone");
		o.s = state.done;
		stateChanged();
	}
	
	public void msgHeresPayment(double payment) {
		print("Received msgHeresPayment");
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		synchronized(Orders) {
			for (Order o : Orders) {
				if(o.getState() == state.done) {
					DeliverOrder(o);
					return true;
				}
			}
			for (Order o : Orders) {
				if (o.getState() == state.pending) {
					FillOrder(o);
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

	private void FillOrder(final Order o) {
		
		o.s = state.filling;
		for(String choice : o.choice) {
			if(food.get(choice).getSupply() < o.amount) {
				o.cantFill.add(choice);
			}
			
			else {
				food.get(choice).decreaseSupply(o.amount);
				print(choice + " remaining: " + food.get(choice).getSupply());
				o.filled.add(choice);
			}
		}
		
		if(!o.cantFill.isEmpty()) {
			o.cook.msgCantFillOrder(this, o.cantFill, o.amount);
		}
		
		timer.schedule(new TimerTask() {
			public void run() {
				msgOrderDone(o);
				stateChanged();
			}
		},
		10000);
	}

	private void DeliverOrder(Order o) {
		print("Delivering food");
		o.cook.msgOrderReady(o.filled, o.amount);
		double total = 0;
		for(String s : o.filled) {
			total += food.get(s).price * o.amount;
		}
		o.cashier.msgMarketBill(this, total);
		Orders.remove(o);
	}
	
	private class Order {
		int amount;
		state s;
		List<String> choice;
		NakamuraCookRole cook;
		NakamuraCashierAgent cashier;

		List<String> cantFill = new ArrayList<String>();
		List<String> filled = new ArrayList<String>();

		Order(NakamuraCookRole cook, NakamuraCashierAgent cashier, List<String> choice, int amt) {
			this.amount = amt;
			this.choice = choice;
			this.cook = cook;
			this.cashier = cashier;
			s = state.pending;
		}

		void setState(state s) {
			this.s = s;
		}

		state getState() {
			return s;
		}
		
		List<String> getChoice() {
			return choice;
		}
	}
	
	private class Food {
		String name;
		int supply;
		double price;
		
		Food(String n, int s, double p) {
			this.name = n;
			this.supply = s;
			this.price = p;
		}
		
		int getSupply() {
			return supply;
		}
		
		void decreaseSupply(int amount) {
			supply -= amount;
		}
		
		void empty() {
			supply = 0;
		}
	}
}

