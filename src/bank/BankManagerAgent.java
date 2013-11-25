package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bank.gui.BankCustomerGui;
import bank.interfaces.*;
import agent.Agent;

public class BankManagerAgent extends Agent implements BankManager {
    //data--------------------------------------------------------------------------------
	private List<BankCustomer> customers = Collections.synchronizedList(new ArrayList<BankCustomer>());
	private List<MyBankTeller> tellers = Collections.synchronizedList(new ArrayList<MyBankTeller>());
	private class MyBankTeller {
	    BankTeller teller;
	    BankTellerState state;
	    int tellerNum;
	    
	    public MyBankTeller(BankTeller teller, BankTellerState state, int tellerNum){
	    	this.teller = teller;
	    	this.state = state;
	    	this.tellerNum = tellerNum;
	    }
	}
	public enum BankTellerState {Idle, Busy};
	private int tellerNum = 0;
	
	public BankManagerAgent(){
	}
	
    //messages----------------------------------------------------------------------------
	public void msgINeedAssistance(BankCustomer customer) {
		customers.add(customer);
	    stateChanged();
	}
	
	public void msgTellerFree(BankTeller teller) {
		for(MyBankTeller tempTeller : tellers){
			if(tempTeller.teller == teller){
				tempTeller.state = BankTellerState.Idle;
			}
		}
	    stateChanged();
	}
	
	public void msgHereForWork(BankTeller teller) {
		tellers.add(new MyBankTeller(teller, BankTellerState.Busy, tellerNum));
		tellerNum++;
	}
    
    //scheduler---------------------------------------------------------------------------
	@Override
	protected boolean pickAndExecuteAnAction() {
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
		print("Assigning customer to teller");
		//customer.msgGoToTeller(myTeller.teller,myTeller.tellerNum);
	    myTeller.teller.msgAssigningCustomer(customer);
	    myTeller.state = BankTellerState.Busy;
	    customers.remove(customer);
	    stateChanged();
	}
    //GUI Actions-------------------------------------------------------------------------

	@Override
	public void msgAddTeller(BankTeller teller) {
		// TODO Auto-generated method stub
		
	}  
}
