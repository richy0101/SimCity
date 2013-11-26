package restaurant.stackRestaurant;

import restaurant.stackRestaurant.gui.CustomerGui;
import restaurant.stackRestaurant.helpers.Menu;
import restaurant.stackRestaurant.helpers.Check;
import agent.Role;
import gui.Building;

import java.util.List;
import java.util.Timer;
import java.util.Random;
import java.util.TimerTask;

import city.PersonAgent;
import city.helpers.Directory;
import restaurant.stackRestaurant.interfaces.*;

/**
 * Restaurant customer agent.
 */
public class StackCustomerRole extends Role implements Customer {
	private int hungerLevel = 5;        // determines length of meal
	private int tableNumber;
	private String choice;
	Timer timer = new Timer();
	Random rand = new Random(); 
	private CustomerGui customerGui;
	String myLocation;
	Check check;
	
	/**
	 * change this values for non normative cases
	 */
	private double availableFunds = 20.00;
	private boolean cheapSkate = false;
	private boolean willingToWait = true;
	

	// agent correspondents
	private Host host;
	private Waiter waiter;
	private Cashier cashier;

	//    private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, WaitingForOpening, WaitingForWaiter, BeingSeated, Seated, Ordering, WaitingForFood, Eating, DoneEating, Paying, Paid, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, gotHungry, doneEntering, waitingForSeating, followHost, seated, ordered, foodArrived, doneEating, waitingForCheck, gotCheck, gotToCashier, donePaying, doneLeaving};
	AgentEvent event = AgentEvent.none;

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public StackCustomerRole(String location){
		super();
		customerGui = new CustomerGui(this);
		
		myLocation = location;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(customerGui);
			}
		}
	}

	/**
	 * hack to establish connection to Host agent.
	 */
	public void setHost(Host host) {
		this.host = host;
	}
	
	public void setFunds(double funds) {
		availableFunds = funds;
	}
	
	public double getFunds() {
		return availableFunds;
	}
	
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	
	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}

	// Messages
	@Override
	public void msgGotHungry() {//from animation
		Do("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	public void msgRestaurantFull() {
		print("restaurant is full");
		event = AgentEvent.waitingForSeating;
		stateChanged();
	}

	public void msgSitAtTable(Waiter waiter, int table) {
		tableNumber = table;
		event = AgentEvent.followHost;
		this.waiter = waiter;
		stateChanged();
	}
	
	public void msgHereToTakeOrder() {
		event = AgentEvent.ordered;
		stateChanged();
	}
	
	public void msgHereIsCheck(Check check) {
		this.check = check;
		event = AgentEvent.gotCheck;
		stateChanged();
		
	}
	
	public void msgReorder() {
		print("I need to reorder");
		event = AgentEvent.ordered;
		state = AgentState.Ordering;
		stateChanged();
	}
	
	public void msgHereIsFood() {
		event = AgentEvent.foodArrived;
		stateChanged();
	}
	
	public void msgHereIsChange(double change) {
		availableFunds += change;
		event = AgentEvent.donePaying;
		stateChanged();
	}

	public void msgAnimationFinishedGoToSeat() {
		//from animation
		event = AgentEvent.seated;
		stateChanged();
	}
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		event = AgentEvent.doneLeaving;
		stateChanged();
	}
	public void msgAnimationFinishedGoToCashier() {
		event = AgentEvent.gotToCashier;
		stateChanged();
	}
	public void msgAnimationFinishedEnteringRestaurant() {
		event = AgentEvent.doneEntering;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ) {
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.doneEntering) {
			state = AgentState.WaitingForWaiter;
			tellHostWaiting();
			return true;
		}
		if (state == AgentState.WaitingForWaiter && event == AgentEvent.waitingForSeating) {
			if(!willingToWait) {
				notWaitingAndLeaving();
				state = AgentState.Leaving;
			}
			else {
				state = AgentState.WaitingForOpening;
			}
			return true;
		}
		if (state == AgentState.WaitingForWaiter && event == AgentEvent.followHost ){
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
		if (state == AgentState.BeingSeated && event == AgentEvent.seated) {
			state = AgentState.Ordering;
			readyToOrder();
			return true;
		}
		if (state == AgentState.Ordering && event == AgentEvent.ordered) {
			state = AgentState.WaitingForFood;
			orderFood();
			
			return true;
		}
		if (state == AgentState.WaitingForFood && event == AgentEvent.foodArrived){
			state = AgentState.Eating;
			updateGui(choice.substring(0, 2));
			EatFood();
			return true;
		}
		if (state == AgentState.Eating && event == AgentEvent.doneEating){
			state = AgentState.DoneEating;
			event = AgentEvent.waitingForCheck;
			updateGui("");
			waiter.msgCheckPlease(this);
			return true;
		}
		if(state == AgentState.DoneEating && event == AgentEvent.gotCheck) {
			state = AgentState.Paying;
			customerGui.DoGoToCashier();
		}
		if(state == AgentState.Paying && event == AgentEvent.gotToCashier) {
			payCheck();
		}
		if(state == AgentState.Paid && event == AgentEvent.donePaying) {
			state = AgentState.Leaving;
			leaveRestaurant();
		}
		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
			state = AgentState.DoingNothing;
			return true;
		}
		return false;
	}

	// Actions
	
	private void payCheck() {
		state = AgentState.Paid;
		cashier.msgPayCheck(this, check, availableFunds);
		availableFunds -= check.cost();
	}
	
	private void updateGui(String choice) {
		customerGui.updateGui(choice);
	}
	
	private void orderFood() {
		//hack for food ordering
		
		if(!cheapSkate) {
			for(Menu.Food food : Menu.sharedInstance().getMenu()) {
				if(availableFunds > food.getPrice() 
						&& Menu.sharedInstance().getInventoryStock(food.getName())) {
					Do("Ordering food");
					choice = food.getName();
					waiter.msgGiveOrder(this, choice);
					updateGui(choice.substring(0, 2) + "?");
					return;
				}
			}
			state = AgentState.Paid;
			event = AgentEvent.donePaying;
			Do("Too expensive, going home");
		}
		else {
			for(int i = 0; i <=4; i++) {
				choice = Menu.sharedInstance().getMenu().get(rand.nextInt(Menu.sharedInstance().getMenu().size())).getName();
				if(Menu.sharedInstance().getInventoryStock(choice)) {
					Do("Ordering food");
					waiter.msgGiveOrder(this, choice);
					updateGui(choice.substring(0, 2) + "?");
					return;
				}
			}
			state = AgentState.Paid;
			event = AgentEvent.donePaying;
			Do("Going home");	
		}
	}
	
	private void readyToOrder() {
		Do("Telling waiter ready to order");
		waiter.msgReadyToOrder(this);
	}

	private void goToRestaurant() {
		Do("Going to restaurant");
		customerGui.DoEnterRestaurant();
		
	}
	
	private void tellHostWaiting() {
		print("waiting");
		host.msgIWantFood(this);//send our instance, so he can respond to us
	}

	private void SitDown() {
		Do("Being seated. Going to table");
		customerGui.DoGoToSeat(1, tableNumber);
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
			Object cookie = 1;
			public void run() {
				print("Done eating, cookie=" + cookie);
				event = AgentEvent.doneEating;
				//isHungry = false;
				stateChanged();
			}
		},
		5000);//getHungerLevel() * 1000);//how long to wait before running task
		state = AgentState.Eating;
		stateChanged();
	}

	private void leaveRestaurant() {
		Do("Leaving.");
		waiter.msgDoneEating(this);
		customerGui.DoExitRestaurant();
		getPersonAgent().msgRoleFinished();
	}
	
	private void notWaitingAndLeaving() {
		Do("Leaving because it's too busy");
		host.msgNotWaiting(this);
		customerGui.DoExitRestaurant();
	}

	// Accessors, etc.

	public String getName() {
		return getPersonAgent().getName();
	}
	
	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
		//could be a state change. Maybe you don't
		//need to eat until hunger lever is > 5?
	}

	public String toString() {
		return getName();
	}

	public void setGui(CustomerGui g) {
		customerGui = g;
	}

	public CustomerGui getGui() {
		return customerGui;
	}
}

