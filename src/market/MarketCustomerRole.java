package market;

import gui.Building;

import java.util.*;
import java.util.concurrent.Semaphore;

import city.PersonAgent;
import city.helpers.Directory;
import agent.Role;
import market.gui.MarketCustomerGui;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;
import market.test.mock.EventLog;
import market.test.mock.LoggedEvent;

public class MarketCustomerRole extends Role implements MarketCustomer {

	//data--------------------------------------------------------------------------------
	Map<String, Integer> myGroceryList;
	
	public enum State {DoingNothing, WaitingForService, Paying, DoneTransaction, CantPay};
	public enum Event {WantsGroceries, GotBill, TurnedAway, GotGroceries};
	State roleState;
	Event roleEvent;
	//BufferedImage customerImage;
	
	Market market;
	String myLocation;
	double orderCost = 0;
	
	private Semaphore actionComplete = new Semaphore(0,true);
	private MarketCustomerGui gui;

	public EventLog log;
	
	public MarketCustomerRole(Map<String, Integer> groceries, String location) {
		roleEvent = Event.WantsGroceries;
		roleState = State.DoingNothing;
		
		myGroceryList = groceries;
		gui = new MarketCustomerGui(this);
		log = new EventLog();
		
		myLocation = location;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(gui);
			}
		}
	}
	
	public MarketCustomerRole(Map<String, Integer> groceries) {
		roleEvent = Event.WantsGroceries;
		roleState = State.DoingNothing;
		
		myGroceryList = groceries;
		gui = new MarketCustomerGui(this);
		
		log = new EventLog();
	}
	
	public void setMarket(MarketRole m) {
		market = m;
	}
	public Market getMarket() {
		return market;
	}
	public double getOrderCost() {
		return orderCost;
	}
	public Event getEvent() {
		return roleEvent;
	}
	public State getState() {
		return roleState;
	}
	
	//messages----------------------------------------------------------------------------
	public void msgHereIsBill(double price) {
		print("Received msgHereIsBill");
		
		orderCost = price;
	    roleEvent = Event.GotBill;
	    
	    log.add(new LoggedEvent("Received msgHereIsBill. Price = $" + price));
	    stateChanged();
	}
	
	public void msgHereAreYourGroceries(Map<String, Integer> groceries) {
		print("Received msgHereAreYourGroceries");
		
		myGroceryList = groceries;
	    roleEvent = Event.GotGroceries;

	    log.add(new LoggedEvent("Received msgHereAreYourGroceries."));
	    stateChanged();
	}
	
	public void msgCantFillOrder(Map<String, Integer> groceries) {
		print("Receieved msgCantFillOrder");
		
		roleEvent = Event.TurnedAway;
		
	    log.add(new LoggedEvent("Received msgHereAreYourGroceries."));
		stateChanged();
	}
	
	public void msgActionComplete() {
		actionComplete.release();
		stateChanged();
	}
	
	//scheduler---------------------------------------------------------------------------
	public boolean pickAndExecuteAnAction() {
		if(roleState == State.DoingNothing && roleEvent == Event.WantsGroceries) {
		    roleState = State.WaitingForService;
		    GiveGroceryOrder();
		    return true;
		}
		
		if(roleState == State.WaitingForService && roleEvent == Event.GotBill) {
		    roleState = State.Paying;
		    Pay();
		    return true;
		}
		
		if(roleState == State.WaitingForService && roleEvent == Event.TurnedAway) {
		    roleState = State.DoneTransaction;
		   LeaveMarket();
		   return true;
		}
		
		if(roleState == State.Paying && roleEvent == Event.GotGroceries) {
		    roleState = State.DoneTransaction;
		   LeaveMarket();
		   return true;
		}
		
		if(roleState == State.CantPay && roleEvent == Event.GotBill) {
			roleState = State.DoneTransaction;
			LeaveMarket();
			return true;
		}
		
		return false;
	}
	
	//actions-----------------------------------------------------------------------------
	public void GiveGroceryOrder() {
		DoEnterMarket();
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(market == null) {
			print("market null");
		}
		else if(myGroceryList == null)
			print("grocerylist null");
	    market.msgGetGroceries(this, myGroceryList);
	    log.add(new LoggedEvent("Ordered groceries."));
	}
	
	public void Pay() {
		if(getPersonAgent().getFunds() >= orderCost) {
			market.msgHereIsMoney(this, orderCost);
			getPersonAgent().setFunds(getPersonAgent().getFunds() - orderCost);

		    log.add(new LoggedEvent("Paid."));
		}
		else {
			market.msgCantAffordGroceries(this);
			roleState = State.CantPay;
			
		    log.add(new LoggedEvent("Couldn't pay."));
		}
	}
	
	public void LeaveMarket() {
		DoLeaveMarket();
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	    log.add(new LoggedEvent("Left market."));
		getPersonAgent().clearGroceries(myGroceryList);
		getPersonAgent().msgRoleFinished();
	}
	
	//GUI Actions-------------------------------------------------------------------------
	private void DoEnterMarket() {
		gui.DoEnterMarket();
	}
	
	private void DoLeaveMarket() {
		gui.DoLeaveMarket();
	}
}