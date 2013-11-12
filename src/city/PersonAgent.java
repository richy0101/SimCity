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
import city.interfaces.Person;
import agent.Agent;
import agent.Role;

public class PersonAgent extends Agent implements Person {
	/**
	 * Data
	 */
	Stack<Role> roles;
	Map<String, Restaurant> restaurants;
	Map<String, Market> markets;
	Map<String, Bank> banks;
	Role workRole;
	double funds;
	boolean hasCar;
	boolean hasWorked;
	String homeName;
	public enum PersonPosition {AtHome, InTransit, AtMarket, AtRestaurant, AtBank};
	public enum HouseState {OwnsHouse, OwnsAppt, Homeless};
	public enum PersonState {Idle, WantsToGoHome, WantFood, CookHome, WaitingForCooking, GoOutEat, StartEating, Eating, NeedsToWork, InTransit};
	PersonPosition personPosition;
	HouseState houseState;
	PersonState personState;
	int hungerLevel;
	Timer personTimer = new Timer();
	public class PersonTimerTask extends TimerTask {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}	
	};
	public class Restaurant {
		String locationName;
		Host host;
		Customer customer;
		Restaurant(String type, Host host, Customer cust) {
			this.host = host;
			this.locationName = type;
			this.customer = cust;
		}
	};
	public class Market {
		String locationName;
		MarketRole market;
		MarketCustomerRole marketCustomer;
		
	};
	public class Bank {
		String locationName;
		BankTellerRole teller;
		BankManagerRole bankManager;
		BankCustomerRole bankCustomer;
		
	};
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
	public void msgRoleFinished() {
		roles.pop();
		stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it.
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
		}
		return evaluateStatus();
	}
	/**
	 * Actions.
	 * 
	 */
	private boolean evaluateStatus() {
		// TODO Auto-generated method stub
		return false;
	}
	private void goHome() {
		roles.clear();
		personState = PersonState.InTransit;
		roles.add(new TransportationRole(homeName));
		
	}	
	private void cookHomeFood() {
		// TODO Auto-generated method stub
		
	}
	private void goRestaurant() {
		// TODO Auto-generated method stub
		
	}
	private void decideFood() {
		// TODO Auto-generated method stub
		
	}
	private void eatFood() {
		// TODO Auto-generated method stub
		
	}
	private void goWork() {
		// TODO Auto-generated method stub
		
	}
}
