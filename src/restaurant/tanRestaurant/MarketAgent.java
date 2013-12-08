package restaurant.tanRestaurant;

import agent.Agent;
import restaurant.tanRestaurant.TanCustomerRole.Order;
import restaurant.tanRestaurant.TanCashierAgent.Bill.billState;
import restaurant.tanRestaurant.TanCookRole.MyOrder.orderState;
import restaurant.tanRestaurant.TanCustomerRole.AgentEvent;
import restaurant.tanRestaurant.MarketAgent.Shipment.shipmentState;
import restaurant.tanRestaurant.TanWaiterRole.MyCustomer;
import restaurant.tanRestaurant.TanWaiterRole.MyCustomer.state;
import restaurant.tanRestaurant.gui.HostGui;
import restaurant.tanRestaurant.interfaces.Cashier;
import restaurant.tanRestaurant.test.mock.EventLog;
import restaurant.tanRestaurant.interfaces.*;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Market Agent
 **/

public class MarketAgent extends Agent implements Market{
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<Shipment> Shipments = Collections.synchronizedList(new ArrayList<Shipment>());
	private String name;
	//private Semaphore atTable = new Semaphore(0,true);
	Timer timer = new Timer();
	public EventLog log = new EventLog();
	
	public HostGui hostGui = null;
	public TanCookRole cook;
	public TanCashierAgent cashier;
	
	double marketCash;
	int amtChicken;
	int amtSteak;
	int amtSalad;
	int amtPizza;

	public static double restaurantdebt=0;
	
	public MarketAgent(String name) {
		super();

		this.name = name;
		if (name.equals("Market X")){
		amtChicken=10;
		amtSteak= 10;
		amtSalad= 10;
		amtPizza= 10;
		marketCash= 0;
		}
		else if (name.equals("Market Y")){
			amtChicken=10;
			amtSteak= 10;
			amtSalad= 10;
			amtPizza= 10;
			marketCash=0;	
		}
		else //(name.equals("Market X")){
			{amtChicken=10;
			amtSteak= 10;
			amtSalad= 10;
			amtPizza= 10;
			marketCash=0;
		}
	}

	public static class Shipment{
		Shipment(Order o, int amt){
			order= o;
			amount= amt;
			ss= shipmentState.receivedOrder;
		}
		
		Order order;
		int amount;
		shipmentState ss;
		enum shipmentState{receivedOrder, processingOrder, preparingOrder, failedOrder, preparedOrder, deliveringOrder};
	}
	
	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List<Shipment> getShipments() {
		return Shipments;
	}


	// Messages
	
	public void msgOrderFromMarket(Order o, int amt){
		print("Received shipment request of "+ amt + " " + o.getName());
		Shipments.add(new Shipment(o, amt));
		stateChanged();
	}

	public void msgAlertCook(){
		synchronized(Shipments){
			for(Shipment s: Shipments){
				if(s.ss==shipmentState.preparingOrder)
					s.ss= shipmentState.preparedOrder;
				stateChanged();
			}
		}
	}
	
	public void msgHereIsPayment(double payment, double debt){
		restaurantdebt= debt;
		marketCash= marketCash+ payment;
		print("Market now has $"+marketCash+" & Restaurant now owes me $"+ restaurantdebt);
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		
		if(!Shipments.isEmpty()){
			synchronized(Shipments){
			for(int i=0; i<Shipments.size(); i++){
				if (Shipments.get(i).ss== shipmentState.receivedOrder){
					Shipments.get(i).ss= shipmentState.processingOrder;
					print("processing order...");
					processOrder(Shipments.get(i));
				}
			}
			}
			/*
			for(Shipment s: Shipments){
				if (s.ss== shipmentState.receivedOrder){
					s.ss= shipmentState.processingOrder;
					print(s.order.getName() + s.amount);
					processOrder(s);
				}
			}*/
			for(int i=0; i<Shipments.size(); i++){
				if (Shipments.get(i).ss== shipmentState.preparedOrder){
					Shipments.get(i).ss= shipmentState.deliveringOrder;
					deliverShipment(Shipments.get(i));
				}
			}
			
			/*for(Shipment s: Shipments){
				if (s.ss== shipmentState.preparedOrder){
					s.ss= shipmentState.deliveringOrder;
					deliverShipment(s);
				}
			}*/
			/*
			for(MyOrder o: Orders){
				if (o.s== orderState.Cooked){
					plateIt(o);
				}
			}*/
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

	public static class MarketBill{
		public MarketBill(String food, int amount, Market m){
			
			market=m;
			
			if(food.equals("Chicken"))
				bill=(5*amount + restaurantdebt*1.2); //pays extra for interest
			else if(food.equals("Steak"))
				bill=(6*amount + restaurantdebt*1.2);
			else if(food.equals("Salad"))
				bill=(3*amount + restaurantdebt*1.2);
			else if(food.equals("Pizza"))
				bill=(4*amount + restaurantdebt*1.2);
			}
		/*
		Cashier cashier;
		WaiterAgent waiter;
		CustomerAgent customer;
		CustomerAgent customer;
		public int tableNum;*/
		Market market;
		public double bill;
		public double change;
		public double debt;
		public billState bs;
		public enum billState{Pending, sentOut, Paid, Settled};
	}
	
	private void deliverShipment(Shipment s) {
		print ("delivering shipment of "+ s.amount + " " + s.order.getName());
		cook.msgHereIsShipment(s.order, s.amount);
		cashier.msgHereIsMarketBill(new MarketBill(s.order.getName(),s.amount,this));
		print("reached here");
		/*
		if (s.order.getName().equals("Chicken")){
			amtChicken -= s.amount;
		}
		else if (s.order.getName().equals("Salad")){
			amtSalad -= s.amount;
		}
		else if (s.order.getName().equals("Steak")){
			amtSteak = amtSteak- s.amount;
			print("inventory left " + amtSteak + " " + s.order.getName());
		}
		else if (s.order.getName().equals("Pizza")){
			amtPizza -= s.amount;
		}*/
		
	}

	private void processOrder(Shipment s){
		//print("in processOrder");
		
		if (s.order.getName().equals("Chicken") && s.amount<= amtChicken){
			s.ss= shipmentState.preparingOrder;
			prepareOrder(s);
		}
		else if (s.order.getName().equals("Salad") && s.amount<= amtSalad){
			s.ss= shipmentState.preparingOrder;
			prepareOrder(s);
		}
		else if (s.order.getName().equals("Steak") && s.amount<= amtSteak){
			print("processing steak shipment");
			s.ss= shipmentState.preparingOrder;
			prepareOrder(s);
		}
		else if (s.order.getName().equals("Pizza") && s.amount<= amtPizza){
			s.ss= shipmentState.preparingOrder;
			prepareOrder(s);
		}
		else{
			s.ss= shipmentState.failedOrder;
			failedOrder(s);
		}
	}
	
	private void failedOrder(Shipment s) {
		// notify cook order failed
		//if have 0 of requested order remove from list, else send what we have
		int amt;
		if (s.order.getName().equals("Steak")){
			print("Only can supply "+ amtSteak + " " + s.order.getName() +". Sending to restaurant.");
			cook.msgFailedOrder(this, s.order, amtSteak);
			//print("Only can supply "+ amtSteak + " " + s.order.getName() +". Sending to restaurant.");
			amt=amtSteak;
			amtSteak=0;
		}
		else if (s.order.getName().equals("Chicken")){
			print("Only can supply "+ amtChicken + " " + s.order.getName()+". Sending to restaurant.");
			cook.msgFailedOrder(this, s.order, amtChicken);
			//print("Only can supply "+ amtChicken + " " + s.order.getName()+". Sending to restaurant.");
			amt=amtChicken;
			amtChicken= 0;
		}
		else if (s.order.getName().equals("Salad")){
			print("Only can supply "+ amtSalad + " " + s.order.getName()+". Sending to restaurant.");
			cook.msgFailedOrder(this, s.order, amtSalad);		
			//print("Only can supply "+ amtSalad + " " + s.order.getName()+". Sending to restaurant.");
			amt=amtSalad;
			amtSalad= 0;
		}
		else{ //if (s.order.getName().equals("Pizza")){
			print("Only can supply "+ amtPizza + " " + s.order.getName()+". Sending to restaurant.");
			cook.msgFailedOrder(this, s.order, amtPizza);
			//print("Only can supply "+ amtPizza + " " + s.order.getName()+". Sending to restaurant.");
			amt=amtPizza;
			amtPizza= 0;
		}
		cashier.msgHereIsMarketBill(new MarketBill(s.order.getName(),amt,this));
		Shipments.remove(s);
		
	}

	private void prepareOrder(Shipment s){
		timer.schedule(new TimerTask() {
			public void run() {
				print("Done preparing shipment.");
				msgAlertCook();
			}
		},
		8000);
	}
	/*
	private void cookIt(MyOrder o){
		if (getAmount(o)==0)
			o.waiter.msgOutOfFood(o.order);
		
		else{
			if (getAmount(o)==3){ //after this it becomes 2, so it functionally orders as order reaches 2.
				orderFromMarket(o);
			}
		
		
			print("Start cooking "+o.order.getName());
			o.s=orderState.Cooking;
			subtractAmount(o);
			print(o.order.getName()+ "REMAINING = " + getAmount(o));
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
	*/
	

	//utilities

	public void setGui(HostGui gui) {
		hostGui = gui;
	}
	
	public void setCook(TanCookRole c){
		cook=c;
	}

	public void setCashier(TanCashierAgent c){
		cashier=c;
	}
	
	public HostGui getGui() {
		return hostGui;
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

