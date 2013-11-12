package stackRestaurant;

import agent.Agent;
import java.util.*;
import stackRestaurant.interfaces.Cook;
import stackRestaurant.interfaces.Market;
import stackRestaurant.interfaces.Cashier;
import stackRestaurant.helpers.Check;

public class StackMarketRole extends Agent implements Market {
	
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	private Map<String, Food> food = Collections.synchronizedMap(new HashMap<String, Food>());
	Timer timer = new Timer();
	double funds;
	Cashier cashier;
	
	public enum OrderState
	{Pending, GatheringOrder, OrderFullfilled, OrderDenied};
	
	private String name;
	
	public StackMarketRole(String name, int steak, int chicken, int salad, int pizza) {
		this.name = name;
		food.put("Steak", new Food(steak, 5));
		food.put("Chicken", new Food(chicken, 4));
		food.put("Salad", new Food(salad, 1));
		food.put("Pizza", new Food(pizza, 3));
	}
	
	public String toString() {
		return name;
	}
	
	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	
	
	@Override
	protected boolean pickAndExecuteAnAction() {
		synchronized(orders) {
			for(Order order : orders) {
				if(order.state == OrderState.Pending) {
					deliverIt(order);
					return true;
				}
			}
		}
		return false;
	}
	
	//actions
	private void deliverIt(final Order order) {
		print("Delivering Food");
		int inventory = food.get(order.choice).inventory;
		if(inventory == 0) {
			order.state = OrderState.OrderDenied;
			print("Telling cook that food is out");
			order.cook.msgInventoryOut(this, order.choice);
			return;
		}
		timer.schedule(new TimerTask() {
			public void run() {
				print("Done delivering food");
				order.state = OrderState.OrderFullfilled;
				deliverFoodAndCheck(order.cook, order.choice);
			}
		},
		6000);
		order.state = OrderState.GatheringOrder;
		stateChanged();
	}
	
	private void deliverFoodAndCheck(Cook cook, String choice) {
		cook.msgMarketDeliveringOrder(food.get(choice).inventory, choice);
		cashier.msgGiveBill(new Check(food.get(choice).inventory*food.get(choice).cost, choice), this);
		food.get(choice).inventory = 0;
		
	}
	
	//messages
	public void msgOrderFood(Cook cook, String choice) {
		orders.add(new Order(cook, choice, OrderState.Pending));
		print("order food");
		stateChanged();
	}
	
	public void msgPayForOrder(double funds) {
		print("Here is the money for the order");
		this.funds += funds;
	}
	
	private class Order {
		Order(Cook cook, String choice, OrderState state) {
			this.cook = cook;
			this.choice = choice;
			this.state = state;
		}
		Cook cook;
		String choice;
		OrderState state;
	}
	
	private class Food {
		public Food(int inventory, double cost) {
			this.inventory = inventory;
			this.cost = cost;
		}
		int inventory;
		double cost;
	}
}
