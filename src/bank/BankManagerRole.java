package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import bank.interfaces.*;
import agent.Role;

public class BankManagerRole extends Role implements BankManager {
    //data--------------------------------------------------------------------------------
	private List<BankCustomer> customers = Collections.synchronizedList(new ArrayList<BankCustomer>());
	private List<MyBankTeller> tellers = Collections.synchronizedList(new ArrayList<MyBankTeller>());
	private class MyBankTeller {
	    BankTeller teller;
	    BankTellerState state;
	    
	    public MyBankTeller(BankTeller teller, BankTellerState state){
	    	this.teller = teller;
	    	this.state = state;
	    }
	}
	private enum BankTellerState {Idle, Busy};
    //messages----------------------------------------------------------------------------
	public void msgINeedAssistance(BankCustomer customer) {
		customers.add(customer);
	    stateChanged();
	}
	
	public void msgTellerFree(BankTeller teller) {
		for(MyBankTeller tempTeller:tellers){
			if(tempTeller.teller == teller){
				tempTeller.state = BankTellerState.Idle;
			}
		}
	    stateChanged();
	}
	
	public void msgAddTeller(BankTeller teller) {
		tellers.add(new MyBankTeller(teller,null));
		stateChanged();
	}
    //scheduler---------------------------------------------------------------------------
	protected boolean pickAndExecuteAction(){
		synchronized(this.tellers){
			for(MyBankTeller tempTeller: tellers){
				if(tempTeller.state == BankTellerState.Idle){
					if(customers.size() != 0){
						AssignCustomerToTeller(customers.get(0),tempTeller);
						return true;
					}
				}
			}
		}
		return false;
	}
    //actions-----------------------------------------------------------------------------
	private void AssignCustomerToTeller(BankCustomer customer, MyBankTeller myTeller) {
	    myTeller.teller.msgAssigningCustomer(customer);
	    myTeller.state = BankTellerState.Busy;
	    customers.remove(customer);
	    stateChanged();
	}
    //GUI Actions-------------------------------------------------------------------------
    
}
