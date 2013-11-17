package market;

import java.util.*;

import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MarketCustomerRole implements MarketCustomer {

	//data--------------------------------------------------------------------------------
	Map<String, Integer> myGroceries;
	
	enum State {DoingNothing, DecidingGroceries, WaitingForService, Paying, DoneTransaction};
	enum Event {WantsGroceries, DecidedGroceries, GotBill, GotGroceries};
	State roleState = State.DoingNothing;
	Event roleEvent;
	
	Market market;
	
	//messages----------------------------------------------------------------------------
	public void msgWantGroceries(Map<String, Integer> groceries) {
	    myGroceries = groceries;
	    roleEvent = Event.WantsGroceries;
	}
	
	public void msgHereIsBill(double price) {
	    
	    roleEvent = Event.GotBill;
	}
	
	public void msgHereAreYourGroceries(Map<String, Integer> groceries) {
		
	    roleEvent = Event.GotGroceries;
	}
	
	//scheduler---------------------------------------------------------------------------
	public boolean pickAndExecuteAnAction() {
		if(roleState == State.DoingNothing && roleEvent == Event.WantsGroceries) {
		    roleState = State.DecidingGroceries;
		    DecideGroceries();
		}
		if(roleState == State.DecidingGroceries && roleEvent == Event.DecidedGroceries) {
		    roleState = State.WaitingForService;
		    GiveGroceryOrder();
		}
		if(roleState == State.WaitingForService && roleEvent == Event.GotBill) {
		    roleState = State.Paying;
		    Pay();
		}
		if(roleState == State.Paying && roleEvent == Event.GotGroceries) {
		    roleState = State.DoneTransaction;
		   LeaveMarket();
		}
		return false;
	}
	
	//actions-----------------------------------------------------------------------------
	public void DecideGroceries() {
		
	}
	
	public void GiveGroceryOrder() {
	    market.msgGetGroceries(myGroceries);
	}
	
	public void Pay() {
	    market.HereIsMoney(/*money*/);

	}
	
	public void LeaveMarket() {
		
	}
	
	//GUI Actions-------------------------------------------------------------------------

}