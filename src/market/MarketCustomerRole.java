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

public class MarketCustomerRole extends Role implements MarketCustomer {

	//data--------------------------------------------------------------------------------
	Map<String, Integer> myGroceryList;
	
	enum State {DoingNothing, WaitingForService, Paying, DoneTransaction, CantPay};
	enum Event {WantsGroceries, GotBill, TurnedAway, GotGroceries};
	State roleState;
	Event roleEvent;
	//BufferedImage customerImage;
	
	Market market;
	String myLocation;
	double orderCost;
	
	private Semaphore actionComplete = new Semaphore(0,true);
	private MarketCustomerGui gui;
	
	public MarketCustomerRole(PersonAgent person, Map<String, Integer> groceries, String location) {
		roleEvent = Event.WantsGroceries;
		roleState = State.DoingNothing;
		
		myGroceryList = groceries;
		gui = new MarketCustomerGui(this);
		
		setPerson(person);
		
		myLocation = location;
		List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(gui);
			}
		}
	}
	
	//messages----------------------------------------------------------------------------
	public void msgHereIsBill(double price) {
		orderCost = price;
	    roleEvent = Event.GotBill;
	    stateChanged();
	}
	
	public void msgHereAreYourGroceries(Map<String, Integer> groceries) {
//		getPersonAgent().groceryList.keySet().removeAll(myGroceryList.keySet());
		
		getPersonAgent().clearGroceries();
	    roleEvent = Event.GotGroceries;
	    stateChanged();
	}
	
	public void msgCantFillOrder(Map<String, Integer> groceries) {
		roleEvent = Event.TurnedAway;
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
		
	    market.msgGetGroceries(this, myGroceryList);
	}
	
	public void Pay() {
		if(getPersonAgent().getFunds() >= orderCost) {
			market.msgHereIsMoney(this, orderCost);
			getPersonAgent().setFunds(getPersonAgent().getFunds() - orderCost);
		}
		else {
			market.msgCantAffordGroceries(this);
			roleState = State.CantPay;
		}
	}
	
	public void LeaveMarket() {
		DoLeaveMarket();
		try {
			actionComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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