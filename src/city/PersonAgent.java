package city;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
















import restaurant.Restaurant;
import restaurant.stackRestaurant.StackCustomerRole;
import market.MarketCustomerRole;
import market.MarketRole;
import bank.BankCustomerRole;
import bank.BankManagerRole;
import bank.BankTellerRole;
import city.helpers.Clock;
import city.helpers.Directory;
import city.interfaces.Person;
import agent.Agent;
import agent.Role;

public class PersonAgent extends Agent implements Person {
	/**
	 * Data---------------------------------------------------------------------------------------------------------------
	 */
	Stack<Role> roles = new Stack<Role>();
	RoleFactory factory = new RoleFactory();
	WorkDetails workDetails;
	//LandLordRole landLord;
	double funds;
	boolean hasCar;
	boolean hasWorked;
	boolean rentDue;
	String name;
	String homeName;
	public enum PersonPosition {AtHome, AtMarket, AtRestaurant, AtBank, City};
	public enum HouseState {OwnsHouse, OwnsAppt, Homeless};
	public enum PersonState {
		//Norm Scenario Constants
		Idle, InTransit, WantsToGoHome, WantFood, CookHome, WaitingForCooking, GoOutEat, StartEating, Eating, NeedsToWork, Cooking, OutToEat,
		//Bank Scenario Constants
		OutToBank, WantsToWithdraw, WantsToGetLoan, WantsToDeposit, WantsToRob,
		//Market Scenario Constants
		};
	PersonPosition personPosition;
	HouseState houseState;
	PersonState personState;
	int hungerLevel;
	int dirtynessLevel;
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
		Role createRole(String order) {
			if(order == "StackRestaurant") {
				this.newRole = new StackCustomerRole(name);
			}
			return newRole;
		}
	};
	PersonAgent(Role job, String job_location, String home) {
		workDetails = new WorkDetails(job, job_location);
		homeName = home;
		houseState = HouseState.OwnsHouse;
		personPosition = PersonPosition.AtHome;
		personState = PersonState.Idle;
		hungerLevel = 0;
		dirtynessLevel = 0;
		funds = 10000.00;
		hasCar = true;
		rentDue = false;
		hasWorked = false;
		startThread();
	}
	//Hax for testing
	PersonAgent (Role hardCodeJob) {
		roles.add(hardCodeJob);
		startThread();
	}
	/**
	 * Messages
	 */
	public void msgWakeUp() {
		System.out.println(name + ": msgWakeUp received - Setting state to WantFood.");
		hasWorked = false;
		personState = PersonState.WantFood;
		stateChanged();
	}
	public void msgCookingDone() {
		System.out.println(name + ": msgCookingDone received - Setting state to StartEating.");
		personState = PersonState.StartEating;
		stateChanged();
	}
	public void msgDoneEating() {
		System.out.println(name + ": msgDoneEating received - Setting state to Idle.");
		personState = PersonState.Idle;
		hungerLevel = 0;
		stateChanged();
	}
	public void msgGoWork() {
		System.out.println(name + ": msgGoWork received - Setting state to NeedsToWork.");
		personState = PersonState.NeedsToWork;
		stateChanged();
	}
	public void msgDoneWorking() {
		System.out.println(name + ": msgDoneWorking received - Setting state to WantFood.");
		personState = PersonState.WantFood;
		stateChanged();
	}
	public void msgGoHome() {
		System.out.println(name + ": msgGoHome received - Setting state to WantsToGoHome");
		personState = PersonState.WantsToGoHome;
		stateChanged();
	}
	public void msgRentPaid() {
		System.out.println(name + ": msgRentPaid received - Setting rentDue to false.");
		rentDue = false;
		stateChanged();
	}
	public void msgRoleFinished() {
		Role r = roles.pop();
		System.out.println(name + ": msgRoleFinished received - Popping current Role: " + r.toString() + ".");
		stateChanged();
	}
	public void msgAtHome() {
		System.out.println(name + ": msgAtHome received - Setting position to AtHome.");
		personPosition = PersonPosition.AtHome;
		stateChanged();
	}
	public void msgPayRent() {
		System.out.println(name + ": msgPayrent received - setting rentDue to true.");
		rentDue = true;
		stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it. -------------------------------------------------------
	 */
	protected boolean pickAndExecuteAnAction() {
		if(!roles.isEmpty()) {
			boolean b = roles.peek().pickAndExecuteAnAction();
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
		if(funds <= 25.00) {
			
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
		System.out.println(name + ": Action goHome - State set to InTransit. Adding new Transportation Role.");
		personState = PersonState.InTransit;
		roles.clear();
		roles.add(new TransportationRole(homeName));
		
	}	
	private void cookHomeFood() {
		System.out.println(name + ": Action cookHomeFood - State set to Cooking.");
		personState = PersonState.Cooking;
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgCookingDone();
			}
		},
		1000);//time for cooking
	}
	private void goRestaurant() {
		System.out.println(name + ": Action goRestaurant - State set to OutToEat");
		personState = PersonState.OutToEat;
		Restaurant r = Directory.sharedInstance().restaurants.get(0);
		roles.clear();
		roles.add(factory.createRole(r.getName()));//Hacked factory LOL
		roles.add(new TransportationRole(r.getName()));
		
	}
	private void decideFood() {
		System.out.println(name + ": Action decideFood - Deciding to eat in or out.");
		boolean cook = true; //cooks at home at the moment
		//if Stay at home and eat. Alters Cook true or false
		if (cook == true) {
			personState = PersonState.CookHome;
		}
		else {
			personState = PersonState.GoOutEat;
		}
	}
	private void eatFood() {
		System.out.println(name + ": Action eatFood - State set to Eating at home.");
		personState = PersonState.Eating;
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgDoneEating();
			}
		},
		5000);//time for Eating
		
	}
	private void goWork() {
		System.out.println(name + ": Action goWork - hasWorked = true. Going to work.");
		hasWorked = true;
		roles.clear();
		roles.add(workDetails.workRole);
		roles.add(new TransportationRole(workDetails.workLocation));
		
	}
	private void cleanRoom() {

	}
	/** Non Norm Actions **/
	private void goRob() {
		// TODO Auto-generated method stub
		
	}
	private void goDeposit() {
		// TODO Auto-generated method stub
		
	}
	private void goLoan() {
		// TODO Auto-generated method stub
		
	}
	
	private void goWithdraw() {
		/* 
		personState = PersonState.OutToEat;
		Restaurant r = Directory.sharedInstance().banks.get(0);
		roles.clear();
		roles.add(factory.createRole(r.getName()));//Hacked factory LOL
		roles.add(new TransportationRole(r.getName()));
		*/
	}
}
