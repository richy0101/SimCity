package restaurant.tanRestaurant;

import agent.Agent;
import agent.Role;
import restaurant.tanRestaurant.TanCookRole.MyMarket.shipmentState;
import restaurant.tanRestaurant.TanCustomerRole.Order;
import restaurant.tanRestaurant.TanCookRole.MyOrder.orderState;
import restaurant.tanRestaurant.MarketAgent;
import restaurant.tanRestaurant.TanCustomerRole.AgentEvent;
import restaurant.tanRestaurant.TanWaiterRole.MyCustomer;
import restaurant.tanRestaurant.TanWaiterRole.MyCustomer.state;
import restaurant.tanRestaurant.gui.HostGui;
import restaurant.tanRestaurant.gui.CookGui;
import restaurant.tanRestaurant.test.mock.EventLog;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Cook Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class TanCookRole extends Role {
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<TanCustomerRole> waitingCustomers
	= Collections.synchronizedList(new ArrayList<TanCustomerRole>());
	public List<MyMarket> Markets = Collections.synchronizedList(new ArrayList<MyMarket>());
	public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
	public List<MyOrder> Orders = Collections.synchronizedList(new ArrayList<MyOrder>());
	private String name;
	//private Semaphore atTable = new Semaphore(0,true);
	Timer timer = new Timer();
	public EventLog log = new EventLog();
	
	
	public CookGui cookGui = null;
	public TanWaiterRole waiter;
	
	int amtChicken;
	int amtSteak;
	int amtSalad;
	int amtPizza;
	int order=5;
	
	

	public TanCookRole(String name) {
		super();

		this.name = name;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
		
		amtChicken=15;
		amtSteak= 15;
		amtSalad= 15;
		amtPizza= 15;
	}

	public static class MyMarket{
		MyMarket(MarketAgent m){
			ma= m;
			//order= o;
			s=shipmentState.orderedShipment;

			
		}
		
		MarketAgent ma; //to be implemented later with multiple waiters
		//Order order;
		shipmentState s;
		enum shipmentState{orderedShipment, outOfSteak, outOfChicken, outOfSalad, outOfPizza};
	}
	
	public static class MyOrder{
		MyOrder(int t, Order o, TanWaiterRole w){
			order= o;
			s=orderState.Pending;
			tableNumber= t;
			waiter= w;
			
		}
		
		/*
		MyOrder(Order order, OrderState state) {
			   waiter = order.waiter;
			   choice = order.choice;
			   table = order.table;
			   seat = order.seat;
			   this.state = state;
			 
			}*/
		
		TanWaiterRole waiter; //to be implemented later with multiple waiters
		Order order;
		int tableNumber;
		String choice;
		//choice to be implemented later
		orderState s;
		enum orderState{Pending, Cooking, AboutDone, Cooked, Plated, Removed};
	}
	
	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
	}
	// Messages
	
	public void msgAddMarket(MarketAgent m){
		Markets.add(new MyMarket(m));
	}
	
	public void msgHereIsAnOrder(int tableNum, Order o, TanWaiterRole w){
		print("added cook order "+ o.getName());
		Orders.add(new MyOrder(tableNum,o,w));
		stateChanged(); //added
	}

	public void msgFoodDone(){ //message from timer
		//o.state=Done;
		for(MyOrder o: Orders){
			if (o.s== orderState.Cooking){
				o.s= orderState.Cooked;
				stateChanged();
				return;
			}
		}
	}
	
	public void msgLeavingTable(TanCustomerRole cust) {
		//print("here i am!");
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				print(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	public void msgHereIsShipment(Order o, int amt){
		
		if(o.getName().equals("Chicken"))
			amtChicken+= amt;
		else if(o.getName().equals("Steak"))
			amtSteak+= amt;
		else if(o.getName().equals("Salad"))
			amtSalad+= amt;
		else //if(o.getName().equals("Pizza"))
			amtPizza+= amt;

	}
	
	
	public void msgFailedOrder(MarketAgent ma, Order o, int amt){
		print(ma.getName()+" has insufficient "+ o.getName());
		if (o.getName().equals("Steak")){
			amtSteak += amt;
			print("received "+ amt+ " "+ o.getName() +" from " + ma.getName());
			for(MyMarket mym:Markets){
				if (mym.ma==ma){
					mym.s = shipmentState.outOfSteak;
				}
			}
		}
		else if (o.getName().equals("Chicken")){
			amtChicken += amt;
			print("received "+ amt+ " "+ o.getName() +" from " + ma.getName());
			synchronized(Markets){
			for(MyMarket mym:Markets){
				if (mym.ma==ma){
					mym.s = shipmentState.outOfChicken;
				}
			}
			}
		}
		else if (o.getName().equals("Salad")){
			amtSalad += amt;
			print("received "+ amt+ " "+ o.getName() +" from " + ma.getName());
			synchronized(Markets){
			for(MyMarket mym:Markets){
				if (mym.ma==ma){
					mym.s = shipmentState.outOfSalad;
				}
			}
			}
		}
		else //if (s.order.getName().equals("Pizza")){
			{amtPizza += amt;
			print("received "+ amt+ " "+ o.getName() +" from " + ma.getName());
			synchronized(Markets){
			for(MyMarket mym:Markets){
				if (mym.ma==ma){
					mym.s = shipmentState.outOfPizza;
				}
			}
			}
		}
		
		amt= 5-amt;
        if (amt !=0)
                tryNewMarket(o, amt);

	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		
		if(!Orders.isEmpty()){
			synchronized(Orders){
			for(MyOrder o: Orders){
				if (o.s== orderState.Pending){
					cookIt(o);
				}
			}
			}
			
			synchronized(Orders){
			for(MyOrder o: Orders){
				if (o.s== orderState.Cooked){
					plateIt(o);
				}
			}
			}
		}
		
		//if there exists o in orders such that o.state=pending then
		    //cookIt(o);
		//if there exists o in orders such that o.state=done then
		    //plateIt(o);
		/*
		for (Table table : tables) { //runs too quickly for Host to return so goes through all tables false
			if (!table.isOccupied()) {
				if (!waitingCustomers.isEmpty() && hostGui.isAtStart()) { //if(!waitingCustomers.isEmpty() && hostguireturned)
					print("!!==!!");
					seatCustomer(waitingCustomers.get(0), table);//the action
					return true;//return true to the abstract agent to reinvoke the scheduler.
				}
			}
		}*/

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	
	// Actions

	private void cookIt(MyOrder o){
		if (getAmount(o)==0){
			o.s=orderState.Removed;
			o.waiter.msgOutOfFood(o.order);
			orderFromMarket(o);
			}
		
		else{
			if (getAmount(o)==3){ //after this it becomes 2, so it functionally orders as order reaches 2.
				orderFromMarket(o);
			}
		
		
			print("Start cooking "+o.order.getName());
			o.s=orderState.Cooking;
			subtractAmount(o);
			print(o.order.getName()+ " remaining : " + getAmount(o));
			timer.schedule(new TimerTask() {
				public void run() {
					//print("Done cooking.");
					msgFoodDone();
				}
			},
			4000);//getHungerLevel() * 1000);//how long to wait before running task
		}	
	}
	
	private void plateIt(MyOrder o){
		//do animation
		o.s= orderState.Plated;
		print("Done cooking " + o.order.getName()+". Calling waiter "+ o.waiter.getName());
		o.waiter.msgOrderIsReady(o.tableNumber);
		//o.w.msgOrderIsReady(o.choice, o.table);
	}
	
	private void orderFromMarket(MyOrder o){
		print("Ordering from preferred market");
		Markets.get(0).ma.msgOrderFromMarket(o.order, 5);
		//preferredmarket.msgOrderFromMarket(o, getAmount(o)+2)
		/*
		 * cycles through food options and orders a certain amount of required food from market
		 */
	}

	private void tryNewMarket(Order o, int amt){
		print("Trying new market");
		// for mym, if o.getName=steak and s!=shipmentState.outOfSteak
		MyMarket preferred= Markets.get(0);
		for(MyMarket mym: Markets){
			if (o.getName().equals("Steak") && mym.s!=shipmentState.outOfSteak){
				preferred= mym;
			}
			else if (o.getName().equals("Salad") && mym.s!=shipmentState.outOfSalad){
				preferred= mym;
			}
			else if (o.getName().equals("Chicken") && mym.s!=shipmentState.outOfChicken){
				preferred= mym;
			}
			else if (o.getName().equals("Pizza") && mym.s!=shipmentState.outOfPizza){
				preferred= mym;
			}
		}
		preferred.ma.msgOrderFromMarket(o, amt);
		
	}

	//utilities

	public void setGui(CookGui gui) {
		cookGui = gui;
	}
	
	public void setWaiter(TanWaiterRole w){
		waiter=w;
	}

	public CookGui getGui() {
		return cookGui;
	}
	
	public int getAmount(MyOrder o){
		if (o.order.getName().equals("Chicken")){
			return amtChicken;
		}
		else if (o.order.getName().equals("Steak")){
			return amtSteak;
		}
		else if (o.order.getName().equals("Salad")){
			return amtSalad;
		}
		else //if (o.order.getName().equals("Pizza")){
			return amtPizza;
		//}
	}
	
	public void subtractAmount(MyOrder o){
		if (o.order.getName().equals("Chicken")){
			amtChicken--;
		}
		else if (o.order.getName().equals("Steak")){
			amtSteak--;
		}
		else if (o.order.getName().equals("Salad")){
			amtSalad--;
		}
		else //if (o.order.getName().equals("Pizza")){
			amtPizza--;
	}
	

	
	private class Table {
		TanCustomerRole occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(TanCustomerRole cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		TanCustomerRole getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}
}
