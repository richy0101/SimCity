package city;

import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;



import market.MarketCustomerRole;
import market.MarketRole;
import bank.BankCustomerRole;
import bank.BankManagerRole;
import bank.BankTellerRole;
import city.helpers.Directory;
import city.interfaces.Person;
import agent.Agent;
import agent.Role;

public class PersonAgent extends Agent implements Person {
	/**
	 * Data---------------------------------------------------------------------------------------------------------------
	 */
	Stack<Role> roles = new Stack<Role>();
	WorkDetails workDetails;
	//LandLordRole landLord;
	double funds;
	boolean hasCar;
	boolean hasWorked;
	boolean rentDue;
	String name;
	String homeName;
	public enum PersonPosition {AtHome, InTransit, AtMarket, AtRestaurant, AtBank};
	public enum HouseState {OwnsHouse, OwnsAppt, Homeless};
	public enum PersonState {Idle, WantsToGoHome, WantFood, CookHome, WaitingForCooking, GoOutEat, StartEating, Eating, NeedsToWork, InTransit, Cooking, OutToEat};
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
	}
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
	}
	/**
	 * Messages
	 */
	public void msgWakeUp() {
		hasWorked = false;
		personState = PersonState.WantFood;
		stateChanged();
	}
	public void msgCookingDone() {
		personState = PersonState.StartEating;
		stateChanged();
	}
	public void msgDoneEating() {
		personState = PersonState.Idle;
		hungerLevel = 0;
		stateChanged();
	}
	public void msgGoWork() {
		personState = PersonState.NeedsToWork;
		stateChanged();
	}
	public void msgDoneWorking() {
		personState = PersonState.WantFood;
		stateChanged();
	}
	public void msgGoHome() {
		personState = PersonState.WantsToGoHome;
		stateChanged();
	}
	public void msgRentPaid() {
		rentDue = false;
		stateChanged();
	}
	public void msgRoleFinished() {
		roles.pop();
		stateChanged();
	}
	public void msgAtHome() {
		personPosition = PersonPosition.AtHome;
		stateChanged();
	}
	public void msgPayRent() {
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
		if (personState == PersonState.WantsToGoHome) {
			goHome();
			return true;
		}
		if (personState == PersonState.CookHome && personPosition == PersonPosition.AtHome) {
			cookHomeFood();
			return true;
		}
		//End of Home Person
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
		personState = PersonState.InTransit;
		roles.clear();
		roles.add(new TransportationRole(homeName));
		
	}	
	private void cookHomeFood() {
		personState = PersonState.Cooking;
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgCookingDone();
			}
		},
		1000);//time for cooking
	}
	private void goRestaurant() {
		personState = PersonState.OutToEat;
		Restaurant r = Directory.sharedInstance().restaurants.get(0);
		roles.clear();
		roles.add(REFLECTIONCODE);//Implement Reflection
		roles.add(new TransportationRole(r.getName));
		
	}
	private void decideFood() {
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
		personState = PersonState.Eating;
		personTimer.schedule(new PersonTimerTask(this) {
			public void run() {
				p.msgDoneEating();
			}
		},
		5000);//time for Eating
		
	}
	private void goWork() {
		hasWorked = true;
		roles.clear();
		roles.add(workDetails.workRole);
		roles.add(new TransportationRole(workDetails.workLocation));
		
	}
	private void cleanRoom() {
		
	}
}
