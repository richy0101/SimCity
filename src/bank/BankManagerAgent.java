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
	    int tellerNum = -1; // hack to test unit test for initial state (-1 equivalent to null)
	    
	    public MyBankTeller(BankTeller teller, BankTellerState state, int tellerNum){
	    	this.teller = teller;
	    	this.state = state;
	    	this.tellerNum = tellerNum;
	    }
	    /**
		 * @return the teller
		 */
		public BankTeller getTeller() {
			return teller;
		}

		/**
		 * @return the state
		 */
		public String getState() {
			return state.toString();
		}

		/**
		 * @return the tellerNum
		 */
		public int getTellerNum() {
			return tellerNum;
		}
	}
	public enum BankTellerState {GotToWork, Idle, Busy};
	private int tellerNum = 0;
	private String name;
	
	public BankManagerAgent(){
		this.name = "siri";
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
		synchronized(this.tellers){
			tellers.add(new MyBankTeller(teller, BankTellerState.GotToWork, tellerNum));
			tellerNum++;
		}
	}
	
	public void msgTellerLeavingWork(BankTeller teller) {
		synchronized(this.tellers) {
			tellers.remove(teller);
			tellerNum--;
		}
	}
    
    //scheduler---------------------------------------------------------------------------
	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(this.tellers){
			for(MyBankTeller tempTeller: tellers){
				if(tempTeller.state == BankTellerState.GotToWork){
					AssignTellerToRegister(tempTeller);
				}
			}
		}
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
	
	private void AssignTellerToRegister(MyBankTeller myTeller) {
		print("Assigning teller to register");
	    myTeller.teller.msgGoToRegister(myTeller.tellerNum);
	    myTeller.state = BankTellerState.Idle;
	}
	
	private void AssignCustomerToTeller(BankCustomer customer, MyBankTeller myTeller) {
		print("Assigning customer to teller");
	    myTeller.teller.msgAssigningCustomer(customer);
	    myTeller.state = BankTellerState.Busy;
	    customers.remove(customer);
	    stateChanged();
	}
	
	//getters for unit test-----------------------------------------------------------------
	/**
	 * @return the tellerNum
	 */
	public int getTellerNum() {
		return tellerNum;
	}

	/**
	 * @return the customers
	 */
	public List<BankCustomer> getCustomers() {
		return customers;
	}

	/**
	 * @return the tellers
	 */
	public List<MyBankTeller> getTellers() {
		return tellers;
	}
	
}
