package restaurant.phillipsRestaurant;

import restaurant.phillipsRestaurant.interfaces.*;
import restaurant.phillipsRestaurant.gui.*;
import agent.Agent;
import agent.Role;
import restaurant.CashierAgent;
import restaurant.phillipsRestaurant.Menu;
import agent.Agent;
import gui.Building;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import city.helpers.Directory;

/**
 * Restaurant customer agent.
 */
public class PhillipsCustomerRole extends Role implements Customer {
	private String name;
	private String location;
	private int hungerLevel = 6;        // determines length of meal
	Timer timer = new Timer();
	Timer timer2 = new Timer();
	private CustomerGui customerGui;
	public int tableNum, randChoice = (int) (Math.random()*4);
	public String order;
	Menu menu = null;
	private Semaphore atCashier = new Semaphore(0,true);
	double cashOnHand=0,moneyOwed=0;
	boolean reOrder=false;
	
	// agent correspondents
	private Host host = null;
	private Waiter waiter = null;
	private Cashier cashier = null;

	//    private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, ReadyToOrder, Ordered, Eating, DoneEating, AboutToPay, Paying, ReadyToLeave, Leaving, FinishedRole, Gone, Left};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, noWaiters, gotHungry, followWaiter, seated, ordering, eatingFood, doneEating, goingToPay, paid, doneLeaving};
	AgentEvent event = AgentEvent.none;
	
	
	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public PhillipsCustomerRole(String location){
		super();
		
		this.location = location;
		customerGui = new CustomerGui(this,1);
		
		double rand = (double) Math.random()*30+30;
		cashOnHand = rand;
		
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == location) {
				b.addGui(customerGui);
			}
		}
		
	}

	/**
	 * hack to establish connection to Host agent.
	 */
	public void setHost(Agent host) {
		this.host = (Host) host;
		System.err.println("Set up host in RICHARD restaurant");
	}
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
		System.err.println("Set up waiter in RICHARD restaurant");
	}
	public void setCashier(Agent cashier){
		this.cashier = (Cashier) cashier;
		System.err.println("Set up cashier in RICHARD restaurant");
	}
	
	public void msgAtCashier() {//from animation
		timer.schedule(new TimerTask() {
			public void run() {
				//paying cashier
				atCashier.release();// = true;
				stateChanged();
			}
		},
		5000);
	}
	
	// Messages
	public void msgGotHungry() {//from animation
		print("I'm hungry- RICHARD restaurant");
		state = AgentState.DoingNothing;
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	public void msgNoWaiters(){
		print("Can't eat, no waiters- RICHARD restaurant");
		state = AgentState.ReadyToLeave;
		event = AgentEvent.noWaiters;
		stateChanged();
	}

	public void msgFollowMe(Waiter w, int tNum, Menu menu) {
		event = AgentEvent.followWaiter;
		this.menu = menu;
		tableNum = tNum;
		stateChanged();
	}
	
	public void msgWhatDoYouWant(){
		event = AgentEvent.ordering;
		if(state == AgentState.Ordered) {
			state = AgentState.ReadyToOrder;
			reOrder = true;
		}
		stateChanged();
	}
	public void msgEatMeal(){
		event = AgentEvent.eatingFood;
		stateChanged();
	}
	public void msgYouMayPay(double money){
		event = AgentEvent.goingToPay;
		moneyOwed = money;
		stateChanged();
	}
	public void msgYouMayLeave(){
		event = AgentEvent.paid;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToSeat() {
		//from animation
		event = AgentEvent.seated;
		stateChanged();
	}
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		System.err.println("Officially left RICHARD restaurant from animation");
		event = AgentEvent.doneLeaving;
		state = AgentState.Gone;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a finite state machine
		//print("Customer state: "+state.toString() + " and Customer event: " + event.toString());
		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
	/*	if (state == AgentState.BeingSeated && event == AgentEvent.seated){
			state = AgentState.ReadyToOrder;
			readyToOrder();
			//EatFood();
			return true;
		}*/
		if (state == AgentState.ReadyToOrder && event == AgentEvent.ordering){
			state = AgentState.Ordered;
			pickFoodFromMenu();
			return true;
		}
		if (state == AgentState.Ordered && event == AgentEvent.eatingFood){
			state = AgentState.Eating;
			EatFood();
			return true;
		}
		if (state == AgentState.Eating && event == AgentEvent.doneEating){
			state = AgentState.AboutToPay;
			callWaiterToPay();
			//no action
			return true;
		}
		if (state == AgentState.AboutToPay && event == AgentEvent.goingToPay){
			state = AgentState.Paying;
			payCashier();
			//no action
			return true;
		}
		if (state == AgentState.ReadyToLeave && event == AgentEvent.paid){
			state = AgentState.Leaving;
			leaveCashier();
			//no action
			return true;
		}
		//ONLY BECAUSE NO WAITERS
		if (state == AgentState.ReadyToLeave && event == AgentEvent.noWaiters){
			state = AgentState.Leaving;
			leaveRestaurant();
			//no action
			return true;
		}
		if(state == AgentState.Gone && event == AgentEvent.doneLeaving){
			roleDone();
		}
		return false;
	}

	// Actions

	private void goToRestaurant() {
		Do("Going to restaurant");
		host.msgIWantFood(this);//send our instance, so he can respond to us
	}
	
	 private void roleDone() {
		System.err.println("Role is done for Customer");
		getPersonAgent().msgRoleFinished();
		state = AgentState.FinishedRole;
	}

	private void SitDown() {
		Do("Being seated. Going to table");
		timer2.schedule(new TimerTask() {
			public void run() {
				System.out.println("Reading Menu");
				readyToOrder();
			}
		},
		6000);
		customerGui.DoGoToSeat(tableNum);
	}	
	
	private void readyToOrder(){
		Do("Ready to order food");
		state = AgentState.ReadyToOrder;
		waiter.msgCustomerReadyToOrder(this);
	}
	
	private void pickFoodFromMenu(){
		Do("Chose order choice from menu");
		//switch statement to traverse through menu items so customer doesn't pick same item
		if(reOrder == true){
			switch(randChoice){
			case 0:
				randChoice = 1;
				break;
			case 1:
				randChoice = 2;
				break;
			case 2:
				randChoice = 3;
				break;
			case 3:
				randChoice = 0;
				break;
			}
		}
		order = menu.pickRandFood(randChoice);
		waiter.msgHereIsMyChoice(this,order);
	}
		

	private void EatFood() {
		Do("Eating Food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() {
			public void run() {
				//print("Done eating " + order);
				event = AgentEvent.doneEating;
				//isHungry = false;
				stateChanged();
			}
		},
		6000);//getHungerLevel() * 1000);//how long to wait before running task
	}
	private void callWaiterToPay() {
		Do("Calling waiter to pay");
		waiter.msgWantToPay(this);
	}
	private void payCashier() {
		Do("Paying cashier");
		customerGui.DoGoToCashier();
		try {
			atCashier.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cashier.msgPayBill(this.tableNum,moneyOwed);
		cashOnHand -= moneyOwed;
		state = AgentState.ReadyToLeave;
		event = AgentEvent.paid;
		stateChanged();
	}
	private void leaveCashier() {
		Do("Leaving restaurant");
		waiter.msgLeavingTable(this);
		state = AgentState.Left;
		customerGui.DoExitRestaurant();
	}
	private void leaveRestaurant() {
		System.err.println("Leaving RICHARD restaurant");
		state = AgentState.Left;
		customerGui.DoExitRestaurant();
	}

	
	//random food choice
	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
		//could be a state change. Maybe you don't
		//need to eat until hunger lever is > 5?
	}

	//public String toString() {
	//	return "customer " + getName();
	//}

	public void setGui(CustomerGui g) {
		customerGui = g;
	}

	public CustomerGui getGui() {
		return customerGui;
	}
	
	public int getTableNum(){
		return tableNum;
	}

	public void setTableNum(int table){
		this.tableNum = table;
	}
}

