package home;

import agent.Role;
import home.interfaces.*;

public class HomePersonRole extends Role implements HomePerson {
	
	//data--------------------------------------------------------------------------------
	Landlord landlord;
	boolean needToPayRent;
	double debt;
	int dirtinessLevel;
	
	//messages----------------------------------------------------------------------------
	public void msgPayRent(double moneyOwed) {
		debt = moneyOwed;
		needToPayRent = true;
	}
	
	public void msgPayLater() {
		
	}
		
	//scheduler---------------------------------------------------------------------------
	public boolean pickAndExecuteAnAction() {
		if(needToPayRent == true) {
			PayRent();
		}
		
		if(dirtinessLevel > 5) {
			Clean();
		}
		
		return false;
	}
	
	//actions-----------------------------------------------------------------------------
	private void PayRent() {
		if(myPerson.funds >= debt) {
			landlord.msgHereIsRent(debt);
			myPerson.funds -= debt;
		}
		else 
			landlord.msgCantPayRent();
		
		needToPayRent = false;
	}
	
	private void Clean() {
		DoClean();
		dirtinessLevel = 0;
	}
	
	//GUI Actions-------------------------------------------------------------------------
	private void DoClean() {
		
	}
}
