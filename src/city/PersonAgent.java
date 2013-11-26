package city;

import gui.Building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import restaurant.Restaurant;
import restaurant.stackRestaurant.StackCustomerRole;
import market.MarketCustomerRole;
import market.MarketRole;
import market.Market;
import bank.Bank;
import bank.BankCustomerRole;
import bank.BankManagerAgent;
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
	public boolean hasWorked;
	boolean rentDue;
	public String name;
	String homeName;
	String currentLocation;
	public enum TransportationMethod {OwnsACar, TakesTheBus, Walks};
	public enum HouseState {OwnsAHouse, OwnsAnApartment, Homeless, RentsAnApartment};
	public enum PersonState {
		//Norm Scenario Constants
		Idle, InTransit, WantsToGoHome, WantFood, CookHome, GoOutEat, StartEating, Eating, NeedsToWork, Cooking, OutToEat,
		//Bank Scenario Constants
		OutToBank, WantsToWithdraw, WantsToGetLoan, WantsToDeposit, WantsToRob, 
		//Market Scenario Constants
		NeedsToGoMarket, OutToMarket, EnterHome, OutToWork, Sleeping
		};
	HouseState houseState;
	private PersonState personState;
	public TransportationMethod transMethod;
	int hungerLevel;
	int aggressivenessLevel;
	int dirtynessLevel;
	int desiredFood;
	PersonGui personGui;
	Map<String, Integer> groceryList = new HashMap<String, Integer>();
	Timer personTimer = new Timer();
	
	//bank information
	int accountNumber;
	
	//Animation Semaphores
	private Semaphore actionComplete = new Semaphore(0,true);
	
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
				this.newRole = new StackCustomerRole("StackRestaurant");
			}
			if(order == "Market1" || order == "Market2") {
				this.newRole = new MarketCustomerRole(p.groceryList, order);
			}
			newRole.setPerson(p);
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
		currentLocation = home;
		houseState = HouseState.OwnsAHouse;
		setPersonState(PersonState.Idle);
		hungerLevel = 0;
		dirtynessLevel = 0;
		funds = 10000.00;
		rentDue = false;
		hasWorked = false;
		aggressivenessLevel = 1;
		transMethod = TransportationMethod.TakesTheBus;
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
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == homeName) {
				//print("Adding GUI");
				b.addGui(personGui);
			}
		}
		if (this.name.contains("BankD")) {
			setPersonState(PersonState.WantsToDeposit);
		}
		else if (this.name.contains("BankW")) {
			setPersonState(PersonState.WantsToWithdraw);
		}
		else if (this.name.contains("BankL")) {
			setPersonState(PersonState.WantsToGetLoan);
		}
		else if (this.name.contains("MarketG")) {
			setPersonState(PersonState.NeedsToGoMarket);
			clearInventory();
			checkInventory();
		}
		startThread();
	}
	
	public PersonAgent(Role job, 
			String job_location, 
			String name, 
			int aggressivenessLevel, 
			double initialFunds, 
			String housingStatus, 
			String vehicleStatus) {
		this.name = name;
		workDetails = new WorkDetails(job, job_location);
		this.aggressivenessLevel = aggressivenessLevel;
		this.funds = initialFunds;
		String vehicleStatusNoSpace = vehicleStatus.replaceAll(" ", "");
		this.transMethod = TransportationMethod.valueOf(vehicleStatusNoSpace);
		setPersonState(PersonState.Idle);
		hungerLevel = 0;
		dirtynessLevel = 0;
		rentDue = false;
		hasWorked = false;
		Directory.sharedInstance().addPerson(this);
		personGui = new PersonGui(this);
		homeName = housingStatus;
		currentLocation = housingStatus;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == homeName) {
				b.addGui(personGui);
			}
		}
		startThread();
		//print("I LIVE.");
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
		setPersonState(PersonState.Idle);
		hungerLevel = 0;
		dirtynessLevel = 0;
		rentDue = false;
		hasWorked = false;
		Directory.sharedInstance().addPerson(this);
		personGui = new PersonGui(this);
		
		homeName = housingStatus;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == homeName) {
				b.addGui(personGui);
			}
		}
		startThread();
		//print("I LIVE.");
	}
	
	//Hax for testing
	public PersonAgent (Role hardCodeJob) {
		name = "HardCoded " + hardCodeJob.toString();
		print(name);
		roles.add(hardCodeJob);
		transMethod = TransportationMethod.TakesTheBus;
		personGui = new PersonGui(this);
	}
	/**
	 * Messages
	 */
	public void msgTestWakeUp() {
		stateChanged();
	}
	public void msgActionComplete() {
		actionComplete.release();
	}
	public void msgWakeUp() {
		print("msgWakeUp received - Setting state to WantFood.");
		hasWorked = false;
		setPersonState(PersonState.WantFood);
		stateChanged();
	}
	public void msgCookingDone() {
		print("msgCookingDone received - Setting state to StartEating.");
		setPersonState(PersonState.StartEating);
		stateChanged();
	}
	public void msgDoneEating() {
		print("msgDoneEating received - Setting state to Idle.");
		setPersonState(PersonState.Idle);
		hungerLevel = 0;
		stateChanged();
	}
	public void msgGoWork() {
		print("msgGoWork received - Setting state to NeedsToWork.");
		setPersonState(PersonState.NeedsToWork);
		stateChanged();
	}
	public void msgDoneWorking() {
		print("msgDoneWorking received - Setting state to WantFood.");
		setPersonState(PersonState.WantFood);
		stateChanged();
	}
	public void msgGoHome() {
		print("msgGoHome received - Setting state to WantsToGoHome");
		setPersonState(PersonState.WantsToGoHome);
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
		//print ("Current state: " + personState.toString());
		personState = PersonState.InTransit;
		stateChanged();
	}
	public void msgTransportFinished(String location) {
		Role r = roles.pop();
		currentLocation = location;
		if (currentLocation == homeName) {
			setPersonState(PersonState.EnterHome);
			print("msgTransportFinished received - Popping transport role, updating current location to: " + currentLocation + ". At Home.");
			stateChanged();
		}
		else {
			print("msgTransportFinished received - Popping transport role, updating current location to: " + currentLocation + ".");
			stateChanged();
		}
	}
	public void msgAtHome() {
		print("msgAtHome received - Setting position to AtHome.");
		currentLocation = homeName;
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
		if (getPersonState() == PersonState.WantsToWithdraw) {
			goWithdraw();
			return true;
		}
		if (getPersonState() == PersonState.WantsToGetLoan) {
			goLoan();
			return true;
		}
		if (getPersonState() == PersonState.WantsToDeposit) {
			goDeposit();
			return true;
		}
		if (getPersonState() == PersonState.WantsToRob) {
			goRob();
			return true;
		}
		//Market Rules
		if (getPersonState() == PersonState.NeedsToGoMarket) {
			goMarket();
			return true;
		}
		/** Normative Scenario Rules **/
		if(getPersonState() == PersonState.EnterHome) {
			enterHome();
			return true;
		}
		if (getPersonState() == PersonState.WantsToGoHome) {
			goHome();
			return true;
		}
		if (getPersonState() == PersonState.CookHome && currentLocation == homeName) {
			cookHomeFood();
			return true;
		}
		if (getPersonState() == PersonState.CookHome) {
			goHome();
			return true;
		}
		if (getPersonState() == PersonState.GoOutEat) {
			goRestaurant();
			return true;
		}
		if (getPersonState() == PersonState.WantFood) {
			//print("STUB IN PERSONAGENT SCHEDULER: WANTFOOD");
			decideFood();
			return true;
		}
		if (getPersonState() == PersonState.StartEating) {
			eatFood();
			return true;
		}
		if (getPersonState() == PersonState.NeedsToWork) {
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
		print("In Eval: Current Location = " + currentLocation + ".");
		if (!getPersonState().toString().contains("ing") || !getPersonState().toString().contains("OutTo")) {
			return false;
		}
		else if (hasWorked = false) {
			//print("Eval says go WORK");
			setPersonState(PersonState.NeedsToWork);
			return true;
		}
		else if(funds < 100.00) {
			//print("Eval says GO WITHDRAW");
			setPersonState(PersonState.WantsToWithdraw);
			return true;
		}
		else if(checkInventory() == false) {
			//print("Just checked inventory, need to replenish!");
			setPersonState(PersonState.NeedsToGoMarket);
			return true;
		}
		else if(currentLocation != homeName) {
			//print ("Eval says GO HOME");
			setPersonState(PersonState.WantsToGoHome);
			return true;
		}
		else if(getPersonState() == PersonState.Idle){
			setPersonState(PersonState.Sleeping);
			personGui.DoSleep();
			actionComplete.acquireUninterruptibly();
			return false;
		}
		else {
			//print("Eval says ELSE!");
			setPersonState(PersonState.Sleeping);
			personGui.DoSleep();
			actionComplete.acquireUninterruptibly();
			return false;
		}
	}
	private void enterHome() {
		personGui.setPresentTrue();
		personGui.DoEnterHouse();
		setPersonState(PersonState.Idle);
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
		setPersonState(PersonState.InTransit);
		roles.clear();
		Role t = new TransportationRole(homeName, currentLocation);
		t.setPerson(this);
		roles.add(t);
	}	
	private void cookHomeFood() {
		print("Action cookHomeFood - State set to cooking " + inventory.get(desiredFood).type + ".");
		setPersonState(PersonState.Cooking);
		personGui.DoCook();
		actionComplete.acquireUninterruptibly();
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgCookingDone();
			}
		},
		inventory.get(desiredFood).preparationTime);//time for cooking
	}
	private void goRestaurant() {
		print("Action goRestaurant - State set to OutToEat");
		setPersonState(PersonState.OutToEat);
		//Decide Which restaurant to go to
		Restaurant r = Directory.sharedInstance().getRestaurants().get(0);
		//End of Decide block
		if(currentLocation == homeName) {
			personGui.DoLeaveHouse();
			actionComplete.acquireUninterruptibly();
			personGui.setPresentFalse();
		}
		//Role logic
		roles.clear();
		Role custRole = factory.createRole(r.getName(), this);
		roles.add(custRole);//Hacked factory LOL
		custRole.msgGotHungry();
		custRole.setHost(Directory.sharedInstance().getAgents().get(r.getName() + "Host"));
		custRole.setCashier(Directory.sharedInstance().getAgents().get(r.getName() + "Cashier"));
		Role t = new TransportationRole(r.getName(), currentLocation);
		t.setPerson(this);
		roles.add(t);
	}
	private void decideFood() {
		print("Action decideFood - Deciding to eat in or out.");
		personGui.DoDecideEat();
		actionComplete.acquireUninterruptibly();
		Random rng = new Random();
		desiredFood = rng.nextInt();
		desiredFood = desiredFood % 4;
		desiredFood = 1;
		boolean cook; //cooks at home at the moment
		if (inventory.get(desiredFood).stock > 0) {
			cook = true;
		}
		else {
			cook = false;
		}
		cook = false;
		//if Stay at home and eat. Alters Cook true or false
		if (cook == true) {
			setPersonState(PersonState.CookHome);
		}
		else {
			setPersonState(PersonState.GoOutEat);
		}
	}
	private void eatFood() {
		print("Action eatFood - State set to Eating at home.");
		setPersonState(PersonState.Eating);
		personGui.DoEat();
		actionComplete.acquireUninterruptibly();
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgDoneEating();
			}
		},
		6000);//time for Eating

	}
	private void goWork() {
		print("Action goWork - hasWorked = true. Going to work.");
		hasWorked = true;
		setPersonState(PersonState.OutToWork);
		if(currentLocation == homeName) {
			personGui.DoLeaveHouse();
			actionComplete.acquireUninterruptibly();
			personGui.setPresentFalse();
		}
		//Role Logic
		roles.clear();
		roles.add(workDetails.workRole);
		workDetails.workRole.setPerson(this);
		Role t = new TransportationRole(workDetails.workLocation, currentLocation);
		t.setPerson(this);
		roles.add(t);
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgDoneWorking();
			}
		},
		9000000);//time for working
	}
	private void cleanRoom() {

	}
	private boolean checkInventory() {
		groceryList.clear();
		for(Food f : inventory) {
			if (f.stock <=1) {
				groceryList.put(f.type, 3);
			}
		}
		return groceryList.isEmpty();
	}
	private void goMarket() {
		print("Action goMarket - State set to OutToMarket");
		setPersonState(PersonState.OutToMarket);
		if(currentLocation == homeName) {
			personGui.DoLeaveHouse();
			actionComplete.acquireUninterruptibly();
			personGui.setPresentFalse();
		}
		Market m = Directory.sharedInstance().getMarkets().get(0);
		roles.clear();
		Role marketCust = factory.createRole(m.getName(), this);
		marketCust.setMarket(Directory.sharedInstance().marketDirectory.get(m.getName()).getWorker());
		roles.add(marketCust);
		Role t = new TransportationRole(m.getName(), currentLocation);
		t.setPerson(this);
		roles.add(t);
	}
	/** Non Norm Actions **/
	private void goRob() {
		/**
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		roles.add(new BankCustomerRole(personState.toString(), 0.0, 0.0));//Hacked factory LOL
		//roles.add(new TransportationRole(b.getName()));
		print("Action goRob - State set to OutBank");
		personState = PersonState.OutToBank;
		*/
		
	}
	private void goDeposit() {
		double deposit;
		if (funds - 100.00 > 0) {
			deposit = funds - 100.00;
		}
		else {
			deposit = 0.0;
		}
		if(currentLocation == homeName) {
			personGui.DoLeaveHouse();
			actionComplete.acquireUninterruptibly();
			personGui.setPresentFalse();
		};
		//Role logic
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		Role bankCustRole = new BankCustomerRole(getPersonState().toString(), deposit, 0.0);
		//bankCustRole.setManager(Directory.sharedInstance().getAgents().get(b.getName()));
		bankCustRole.setPerson(this);
		roles.add(bankCustRole);
		Role t = new TransportationRole(b.getName(), currentLocation);
		t.setPerson(this);
		roles.add(t);
		print("Action goDeposit - State set to OutBank");
		setPersonState(PersonState.OutToBank);
	}
	private void goLoan() {
		if(currentLocation == homeName) {
			personGui.DoLeaveHouse();
			actionComplete.acquireUninterruptibly();
			personGui.setPresentFalse();
		}
		//Role logic
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		Role bankCustRole = new BankCustomerRole(getPersonState().toString(), 0.0, 1000.0);
		//bankCustRole.setManager(Directory.sharedInstance().getAgents().get(b.getName()));
		bankCustRole.setPerson(this);
		roles.add(bankCustRole);
		Role t = new TransportationRole(b.getName(), currentLocation);
		t.setPerson(this);
		roles.add(t);
		print("Action goLoan - State set to OutBank");
		setPersonState(PersonState.OutToBank);
		
	}
	
	private void goWithdraw() {
		if(currentLocation == homeName) {
			personGui.DoLeaveHouse();
			actionComplete.acquireUninterruptibly();
			personGui.setPresentFalse();
		}
		//Role logic
		Bank b = Directory.sharedInstance().getBanks().get(0);
		roles.clear();
		Role bankCustRole = new BankCustomerRole(getPersonState().toString(), 0.0, 0.0);
		//bankCustRole.setManager(Directory.sharedInstance().getAgents().get(b.getName()));
		bankCustRole.setPerson(this);
		roles.add(bankCustRole);
		Role t = new TransportationRole(b.getName(), currentLocation);
		t.setPerson(this);
		roles.add(t);
		print("Action goWithraw - State set to OutBank");
		setPersonState(PersonState.OutToBank);
	}
	public void clearInventory() {
		for (Food f : inventory) {
			f.stock = 0;
		}
	}
	public void clearGroceries(Map<String, Integer> givenGroceries) {
		for (Food f : inventory) {
			f.stock += givenGroceries.get(f.type);
			//print("Increasing inventory from groceries!");
		}
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
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTransportationMethod() {
		return transMethod.toString();
	}

	public PersonState getPersonState() {
		return personState;
	}

	public void setPersonState(PersonState personState) {
		this.personState = personState;
	}
}
