package market;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MarketRole implements Market {

	//data--------------------------------------------------------------------------------
	enum orderState {Ordered, CantFill, Filled, Billed, Paid};
	
	List<Order> MyOrders;
	Map<String, Food> inventory = new HashMap<String, Food>();
	
	class Order {
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
	}

	class Food {
	    String name;
	    int supply;
	    double price;
	    
	    Food(String n, int s, double p) {
	    	name = n;
	    	supply = s;
	    	price = p;
	    }
	}
	
	public MarketRole() {
		inventory.put("Chicken", new Food("Chicken", 10, 1.00));
		inventory.put("Steak", new Food("Steak", 10, 2.00));
		inventory.put("Pizza", new Food("Pizza", 10, 3.00));
		inventory.put("Salad", new Food("Salad", 10, 4.00));
	}
	
	public List<Order> getMyOrders() {
		return MyOrders;
	}
	public Map<String, Food> getInventory() {
		return inventory;
	}
	
	//messages----------------------------------------------------------------------------
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList) {
	    MyOrders.add(new Order(customer, groceryList));
	}
	public void msgHereIsMoney(MarketCustomer customer, double money) {
	    for(Order o : MyOrders) {
	    	if(o.customer == customer)
	    		o.state = orderState.Paid;  
	    }
	}
	public void msgCantAffordGroceries(MarketCustomer customer) {
		for(Order o : MyOrders) {
			if(o.customer == customer)
				MyOrders.remove(o);   
		}
	}
	
	//scheduler---------------------------------------------------------------------------
	public boolean pickAndExecuteAnAction() {
		for(Order o : MyOrders) {
			if(o.state == orderState.Ordered)
				FillOrder(o);
		}
		
		for(Order o : MyOrders) {
			if(o.state == orderState.CantFill)
				TurnAwayCustomer(o);
		}
		
		for(Order o : MyOrders) {
			if(o.state == orderState.Filled)
				BillCustomer(o);
		}
		
		for(Order o : MyOrders) {
			if(o.state == orderState.Paid)
				GiveGroceries(o);
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
	    		o.price += inventory.get(choice).price;
	    		o.retrievedGroceries.put(choice, amount);
	    		
	    		DoGetItem(choice); //GUI
	    		inventory.get(choice).supply -= amount;

	    		i.remove();
	    	}
	    }
	    if(o.retrievedGroceries.isEmpty())
	        o.state = orderState.CantFill;
	    else
	        o.state = orderState.Filled;
	}
	private void TurnAwayCustomer(Order o) {
	    o.customer.msgCantFillOrder(o.groceryList);
	    MyOrders.remove(o);
	}
	private void BillCustomer(Order o) {
	    o.customer.msgHereIsBill(o.price);
	    o.state = orderState.Billed;
	}
	private void GiveGroceries(Order o) {
	    o.customer.msgHereAreYourGroceries(o.retrievedGroceries);
	    MyOrders.remove(o);
	}

	//GUI Actions-------------------------------------------------------------------------
	private void DoGetItem(String s) {
		
	}
}
