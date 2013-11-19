package market;

import java.util.*;

import agent.Role;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MarketCustomerRole extends Role implements MarketCustomer {

	//data--------------------------------------------------------------------------------
	Map<String, Integer> myGroceryList;
	
	enum State {DoingNothing, WaitingForService, Paying, DoneTransaction, CantPay};
	enum Event {WantsGroceries, GotBill, TurnedAway, GotGroceries};
	State roleState;
	Event roleEvent;
	
	Market market;
	double orderCost;
	
	public MarketCustomerRole(Map<String, Integer> groceries) {
		roleEvent = Event.WantsGroceries;
		roleState = State.DoingNothing;
		
		myGroceryList = groceries;
	}
	
	//messages----------------------------------------------------------------------------
	public void msgHereIsBill(double price) {
		orderCost = price;
	    roleEvent = Event.GotBill;
	}
	
	public void msgHereAreYourGroceries(Map<String, Integer> groceries) {
		myPerson.groceryList.keySet().removeAll(myGroceryList.keySet());
	    roleEvent = Event.GotGroceries;
	}
	
	public void msgCantFillOrder(Map<String, Integer> groceries) {
		roleEvent = Event.TurnedAway;
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
	    market.msgGetGroceries(this, myGroceryList);
	}
	
	public void Pay() {
		if(myPerson.funds >= orderCost) {
			market.msgHereIsMoney(this, orderCost);
			myPerson.funds -= orderCost;
		}
		else {
			market.msgCantAffordGroceries(this);
			roleState = State.CantPay;
		}
	}
	
	public void LeaveMarket() {
		myPerson.msgRoleFinished();
	}
	
	//GUI Actions-------------------------------------------------------------------------

}