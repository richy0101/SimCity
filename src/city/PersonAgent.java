package city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import restaurant.Restaurant;
import restaurant.stackRestaurant.StackCustomerRole;
import market.MarketCustomerRole;
import market.MarketRole;
import market.Market;
import bank.Bank;
import bank.BankCustomerRole;
import bank.BankManagerRole;
import bank.BankTellerRole;
import city.gui.PersonGui;
import city.helpers.Clock;
import city.helpers.Directory;
import city.interfaces.Person;
import agent.Agent;
import agent.Role;

public class PersonAgent extends Agent implements Person {
	/**
	 * Data---------------------------------------------------------------------------------------------------------------
	 */
	public Stack<Role> roles = new Stack<Role>();
	RoleFactory factory = new RoleFactory();
	WorkDetails workDetails;
	//LandLordRole landLord;
	double funds;
	boolean hasWorked;
	boolean rentDue;
	public String name;
	String homeName;
	public enum TransportationMethod {OwnsACar, TakesTheBus, Walks};
	public enum PersonPosition {AtHome, AtMarket, AtRestaurant, AtBank, City};
	public enum HouseState {OwnsAHouse, OwnsAnApartment, Homeless, RentsAnApartment};
	public enum PersonState {
		//Norm Scenario Constants
		Idle, InTransit, WantsToGoHome, WantFood, CookHome, WaitingForCooking, GoOutEat, StartEating, Eating, NeedsToWork, Cooking, OutToEat,
		//Bank Scenario Constants
		OutToBank, WantsToWithdraw, WantsToGetLoan, WantsToDeposit, WantsToRob, 
		//Market Scenario Constants
		NeedsToGoMarket, OutToMarket
		};
	PersonPosition personPosition;
	HouseState houseState;
	PersonState personState;
	TransportationMethod transMethod;
	int hungerLevel;
	int aggressivenessLevel;
	int dirtynessLevel;
	PersonGui personGui;
	Map<String, Integer> groceryList = new HashMap<String, Integer>();
	Timer personTimer = new Timer();
	
	public class PersonTimerTask extends TimerTask {
		PersonAgent p;
		PersonTimerTask(PersonAgent p) {
			this.p = p;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}	
	};
	
	private class WorkDetails {
		Role workRole;
		String workLocation;
		WorkDetails(Role job, String location) {
			this.workRole = job;
			this.workLocation = location;
		}
	};
	private class RoleFactory {
		Role newRole;
		RoleFactory() {
			newRole = null;
		}
		Role createRole(String order, PersonAgent p) {
			if(order == "StackRestaurant") {
				this.newRole = new StackCustomerRole(p);
			}
			if(order == "Market1" || order == "Market2") {
				this.newRole = new MarketCustomerRole(p.groceryList);
			}
			//print("Set role complete.");
			return newRole;
		}
	};
	private class Food {
		public String type;
		public int preparationTime;
		public int stock;
		public Food(String type) {
			this.type = type;
			if (this.type == "Chicken") {
				preparationTime = 5000;
			}
			else if (this.type == "Steak") {
				preparationTime = 9000;
			}
			else if (this.type == "Salad") {
				preparationTime = 4000;
			}
			else if (this.type == "Pizza") {
				preparationTime = 7000;
			}
			stock = 3;
		}
	}
	private List<Food> inventory = Collections.synchronizedList(new ArrayList<Food>());
	public PersonAgent(Role job, String job_location, String home, String name) {
		this.name = name;
		workDetails = new WorkDetails(job, job_location);
		homeName = home;
		houseState = HouseState.OwnsAHouse;
		personPosition = PersonPosition.AtHome;
		personState = PersonState.Idle;
		hungerLevel = 0;
		dirtynessLevel = 0;
		funds = 10000.00;
		rentDue = false;
		hasWorked = false;
		aggressivenessLevel = 1;
		
		//Set up inventory
		Food initialFood = new Food("Chicken");
		inventory.add(initialFood);
		initialFood = new Food ("Steak");
		inventory.add(initialFood);
		initialFood = new Food ("Salad");
		inventory.add(initialFood);
		initialFood = new Food ("Pizza");
		inventory.add(initialFood);
		//Set up gui
		personGui = new PersonGui(this);
		startThread();
	}
	
	public PersonAgent(Role job, 
			String name, 
			int aggressivenessLevel, 
			double startingFunds,
			String housingStatus,
			String vehicleStatus) {
		
		this.name = name;
		workDetails = new WorkDetails(job, Directory.sharedInstance().roleDirectory.get(job.toString()));
		this.aggressivenessLevel = aggressivenessLevel;
		this.funds = startingFunds;
		String vehicleStatusNoSpace = vehicleStatus.replaceAll(" ", "");
		this.transMethod = TransportationMethod.valueOf(vehicleStatusNoSpace);
		String housingStatusNoSpace = housingStatus.replaceAll(" ", "");
		this.houseState = HouseState.valueOf(housingStatusNoSpace);
		personPosition = PersonPosition.AtHome;
		personState = PersonState.Idle;
		hungerLevel = 0;
		dirtynessLevel = 0;
		rentDue = false;
		hasWorked = false;
		Directory.sharedInstance().addPerson(this);
		personGui = new PersonGui(this);
		startThread();
		print("I LIVE.");
	}
	
	//Hax for testing
	public PersonAgent (Role hardCodeJob) {
		name = "HardCoded " + hardCodeJob.toString();
		print(name);
		roles.add(hardCodeJob);
		personGui = new PersonGui(this);
	}
	/**
	 * Messages
	 */
	public void msgWakeUp() {
		print("msgWakeUp received - Setting state to WantFood.");
		hasWorked = false;
		personState = PersonState.WantFood;
		stateChanged();
	}
	public void msgCookingDone() {
		print("msgCookingDone received - Setting state to StartEating.");
		personState = PersonState.StartEating;
		stateChanged();
	}
	public void msgDoneEating() {
		print("msgDoneEating received - Setting state to Idle.");
		personState = PersonState.Idle;
		hungerLevel = 0;
		stateChanged();
	}
	public void msgGoWork() {
		print("msgGoWork received - Setting state to NeedsToWork.");
		personState = PersonState.NeedsToWork;
		stateChanged();
	}
	public void msgDoneWorking() {
		print("msgDoneWorking received - Setting state to WantFood.");
		personState = PersonState.WantFood;
		stateChanged();
	}
	public void msgGoHome() {
		print("msgGoHome received - Setting state to WantsToGoHome");
		personState = PersonState.WantsToGoHome;
		stateChanged();
	}
	public void msgRentPaid() {
		print("msgRentPaid received - Setting rentDue to false.");
		rentDue = false;
		stateChanged();
	}
	public void msgRoleFinished() {
		Role r = roles.pop();
		print("msgRoleFinished received - Popping current Role: " + r.toString() + ".");
		stateChanged();
	}
	public void msgAtHome() {
		print("msgAtHome received - Setting position to AtHome.");
		personPosition = PersonPosition.AtHome;
		stateChanged();
	}
	public void msgPayRent() {
		print("msgPayrent received - setting rentDue to true.");
		rentDue = true;
		stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it. -------------------------------------------------------
	 */
	protected boolean pickAndExecuteAnAction() {
		if(!roles.isEmpty()) {
			//print("STUB IN PERSONAGENT SCHEDULER: INROLESSTACK " + roles.peek().toString());
			boolean b = false;
			b = roles.peek().pickAndExecuteAnAction();
			return b;
		}
		/** Rules for Market and Bank visits. Should only happen if evaluate status is called. **/
		//Bank Rules
		if (personState == PersonState.WantsToWithdraw) {
			goWithdraw();
			return true;
		}
		if (personState == PersonState.WantsToGetLoan) {
			goLoan();
			return true;
		}
		if (personState == PersonState.WantsToDeposit) {
			goDeposit();
			return true;
		}
		if (personState == PersonState.WantsToRob) {
			goRob();
			return true;
		}
		//Market Rules
		if (personState == PersonState.NeedsToGoMarket) {
			goMarket();
		}
		/** Normative Scenario Rules **/
		if (personState == PersonState.WantsToGoHome) {
			goHome();
			return true;
		}
		if (personState == PersonState.CookHome && personPosition == PersonPosition.AtHome) {
			cookHomeFood();
			return true;
		}
		if (personState == PersonState.CookHome) {
			goHome();
			return true;
		}
		if (personState == PersonState.GoOutEat) {
			goRestaurant();
			return true;
		}
		if (personState == PersonState.WantFood) {
			//print("STUB IN PERSONAGENT SCHEDULER: WANTFOOD");
			decideFood();
			return true;
		}
		if (personState == PersonState.StartEating) {
			eatFood();
			return true;
		}
		if (personState == PersonState.NeedsToWork) {
			goWork();
			return true;
		}
		/*if (rentDue == true) {
			payRent();
			return true;
		}*/
		return evaluateStatus();
	}


	/**
	 * Actions --------------------------------------------------------------------------------------------------------
	 * 
	 */
	private boolean evaluateStatus() {
		// TODO Auto-generated method stub
		if(funds <= 100.00) {
			personState = PersonState.WantsToWithdraw;
			return true;
		}
		else if(checkInventory() == false) {
			personState = PersonState.NeedsToGoMarket;
		}
		else {
			personState = PersonState.Idle;
		}
		return false;
	}
	/*private void payRent() {
		roles.clear();
		if (personPosition == personPosition.AtHome) {
			roles.clear();
			roles.add(new HomePersonRole(landLord));
		}
		else {
			roles.clear();
			roles.add(new HomePersonRole(landLord));
			roles.add(new TransportationRole(homeName));
		}
		
	}*/
	private void goHome() {
		print("Action goHome - State set to InTransit. Adding new Transportation Role.");
		personState = PersonState.InTransit;
		roles.clear();
		roles.add(new TransportationRole(homeName));
		
	}	
	private void cookHomeFood() {
		print("Action cookHomeFood - State set to Cooking.");
		personState = PersonState.Cooking;
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgCookingDone();
			}
		},
		1000);//time for cooking
	}
	private void goRestaurant() {
		print("Action goRestaurant - State set to OutToEat");
		personState = PersonState.OutToEat;
		Restaurant r = Directory.sharedInstance().getRestaurants().get(0);
		roles.clear();
		roles.add(factory.createRole(r.getName(), this));//Hacked factory LOL
		//roles.add(new TransportationRole(r.getName()));
		print("Action goRestaurant - State set to OutToEat");
	}
	private void decideFood() {
		print("Action decideFood - Deciding to eat in or out.");
		//Change cook to false if want to try going to stack Restaurant scenario.
		boolean cook = false; //cooks at home at the moment
		//if Stay at home and eat. Alters Cook true or false
		if (cook == true) {
			personState = PersonState.CookHome;
		}
		else {
			personState = PersonState.GoOutEat;
		}
	}
	private void eatFood() {
		print("Action eatFood - State set to Eating at home.");
		personState = PersonState.Eating;
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgDoneEating();
			}
		},
		5000);//time for Eating
		
	}
	private void goWork() {
		print("Action goWork - hasWorked = true. Going to work.");
		hasWorked = true;
		roles.clear();
		roles.add(workDetails.workRole);
		roles.add(new TransportationRole(workDetails.workLocation));
		
	}
	private void cleanRoom() {

	}
	private boolean checkInventory() {
		for(Food f : inventory) {
			if (f.stock <=1) {
				groceryList.put(f.type, 3);
			}
		}
		return groceryList.isEmpty();
	}
	private void goMarket() {
		Market m = Directory.sharedInstance().getMarkets().get(0);
		roles.clear();
		factory.createRole(m.getName(), this);
		roles.add(new TransportationRole(m.getName()));
		print("Action goMarket - State set to OutToMarket");
		personState = PersonState.OutToMarket;
		
	}
	/** Non Norm Actions **/
	private void goRob() {
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		roles.add(new BankCustomerRole(personState.toString()));//Hacked factory LOL
		roles.add(new TransportationRole(b.getName()));
		print("Action goRob - State set to OutBank");
		personState = PersonState.OutToBank;
		
	}
	private void goDeposit() {
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		roles.add(new BankCustomerRole(personState.toString()));//Hacked factory LOL
		roles.add(new TransportationRole(b.getName()));
		print("Action goDeposit - State set to OutBank");
		personState = PersonState.OutToBank;
		
	}
	private void goLoan() {
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		roles.add(new BankCustomerRole(personState.toString()));//Hacked factory LOL
		roles.add(new TransportationRole(b.getName()));
		print("Action goLoan - State set to OutBank");
		personState = PersonState.OutToBank;
		
	}
	
	private void goWithdraw() {
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		roles.add(new BankCustomerRole(personState.toString()));//Hacked factory LOL
		roles.add(new TransportationRole(b.getName()));
		print("Action goWithraw - State set to OutBank");
		personState = PersonState.OutToBank;
	}
	public void clearGroceries() {
		groceryList.clear();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getFunds() {
		return funds;
	}
	public void setFunds(double funds) {
		this.funds = funds;
	}
}
