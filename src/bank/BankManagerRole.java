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
	}
	private enum BankTellerState {Idle, Busy}; 
//messages----------------------------------------------------------------------------
	public void msgINeedAssistance(BankCustomer customer) {
		
	}
	
	public void msgTellerFree(BankTeller teller) {
		
	}
	
	public void msgAddTeller(BankTeller teller) {
		
	}
//scheduler---------------------------------------------------------------------------
//actions-----------------------------------------------------------------------------
	private void AssignCustomerToTeller(BankCustomer customer, MyBankTeller teller) {
	    teller.teller.msgAssigningCustomer(customer);
	    teller.state = BankTellerState.Busy;
	    customers.remove(customer);
	}
//GUI Actions-------------------------------------------------------------------------

}
