package market;

import java.util.*;

import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MarketCustomerRole implements MarketCustomer {

	//data--------------------------------------------------------------------------------
	Map<String, Integer> myGroceryList;
	
	enum State {DoingNothing, DecidingGroceries, WaitingForService, Paying, DoneTransaction};
	enum Event {WantsGroceries, DecidedGroceries, GotBill, GotGroceries};
	State roleState = State.DoingNothing;
	Event roleEvent;
	
	Market market;
	double orderCost;
	
	//messages----------------------------------------------------------------------------
	public void msgWantGroceries(Map<String, Integer> groceries) {
	    myGroceryList = groceries;
	    roleEvent = Event.WantsGroceries;
	}
	
	public void msgHereIsBill(double price) {
		orderCost = price;
	    roleEvent = Event.GotBill;
	}
	
	public void msgHereAreYourGroceries(Map<String, Integer> groceries) {
	    roleEvent = Event.GotGroceries;
	}
	
	public void msgCantFillOrder(Map<String, Integer> groceries) {
		
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
	    market.msgGetGroceries(this, myGroceryList);
	}
	
	public void Pay() {
		//Decide if he has enough money
	    market.msgHereIsMoney(this, orderCost);
	}
	
	public void LeaveMarket() {
		
	}
	
	//GUI Actions-------------------------------------------------------------------------

}