package bank;

import java.util.ArrayList;

import city.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import bank.*;
import bank.BankManagerRole.BankTellerState;
import bank.helpers.AccountSystem;
import bank.helpers.AccountSystem.BankAccount;
import bank.BankCustomerRole;
//import bank.BankManagerRole.MyBankTeller;
import bank.interfaces.*;
import agent.Role;

public class BankTellerRole extends Role implements BankTeller {
    //data--------------------------------------------------------------------------------
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private class MyCustomer {
	    BankCustomer customer;
	    int accountNumber;
	    CustomerState custState;
	    double moneyToWithdraw;
	    
	    public MyCustomer(BankCustomer customer, CustomerState state){
	    	this.customer = customer;
	    	this.custState = state;
	    }
    }
    private int tellerNumber;
    BankManager manager;
    private enum CustomerState {ArrivedAtWork, DoingNothing, NeedingAssistance, AskedAssistance, OpeningAccount, OpenedAccount, DepositingMoney, WithdrawingMoney, LoanAccepted, LoanRejected, Leaving};
    
    public BankTellerRole(){
    	//GotToWork();
    }
    
    //messages----------------------------------------------------------------------------
	public void msgAssigningCustomer(BankCustomer customer) {
		boolean newCustomer = true;
		for(MyCustomer tempCustomer : customers){
			if(tempCustomer.customer == customer){
				tempCustomer.custState = CustomerState.NeedingAssistance;
				newCustomer = false;
			}
		}
		if(newCustomer == true){
			customers.add(new MyCustomer(customer, CustomerState.NeedingAssistance));
		}
	    stateChanged();
	}
	
	public void msgOpenAccount(BankCustomer customer) {
		int uniqueNum = AccountSystem.sharedInstance().newUniqueAccountNumber();
		AccountSystem.sharedInstance().addAccount(uniqueNum);
		for(MyCustomer tempCustomer : customers){
			if(tempCustomer.customer == customer){
				tempCustomer.accountNumber = uniqueNum;
				tempCustomer.custState = CustomerState.OpeningAccount;
			}
		}
	    stateChanged();
	}
	
	public void msgDepositMoney(int accountNumber, double money) {
		for (Map.Entry<Integer, AccountSystem.BankAccount> entry : AccountSystem.sharedInstance().getAccounts().entrySet()) {
			if(entry.getKey() == accountNumber){
				entry.getValue().addMoney(money);
			}
		}
		for(MyCustomer tempCustomer : customers){
			if(tempCustomer.accountNumber == accountNumber){
				tempCustomer.custState = CustomerState.DepositingMoney;
			}
		}
	    stateChanged();
	}
	
	public void msgWithdrawMoney(int accountNumber, double money) {
		for(MyCustomer tempCustomer : customers){
			if(tempCustomer.accountNumber == accountNumber){
				tempCustomer.moneyToWithdraw = money;
				tempCustomer.custState = CustomerState.WithdrawingMoney;
			}
		}
	    stateChanged();
	}
	
	public void msgIWantLoan(int accountNumber, double moneyRequest) {
		for (Map.Entry<Integer, AccountSystem.BankAccount> entry : AccountSystem.sharedInstance().getAccounts().entrySet()) {
			if(entry.getKey() == accountNumber){
				if(entry.getValue().elligibleForLoan == true){
					for(MyCustomer tempCustomer : customers){
						if(tempCustomer.accountNumber == accountNumber){
							tempCustomer.moneyToWithdraw = moneyRequest;
							tempCustomer.custState = CustomerState.LoanAccepted;
						}
					}
				}
			}
		}
	}
    //scheduler---------------------------------------------------------------------------
	protected boolean pickAndExecuteAction(){
		synchronized(this.customers){
			for(MyCustomer tempCustomer: customers){
				if(tempCustomer.custState == CustomerState.NeedingAssistance){
					OfferAssistance(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.customers){
			for(MyCustomer tempCustomer: customers){
				if(tempCustomer.custState == CustomerState.OpeningAccount){
					OpenedAccount(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.customers){
			for(MyCustomer tempCustomer: customers){
				if(tempCustomer.custState == CustomerState.DepositingMoney){
					DepositMoney(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.customers){
			for(MyCustomer tempCustomer: customers){
				if(tempCustomer.custState == CustomerState.WithdrawingMoney){
					WithdrawMoney(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.customers){
			for(MyCustomer tempCustomer: customers){
				if(tempCustomer.custState == CustomerState.Leaving){
					TellerIsFree(tempCustomer);
					return true;
				}
			}
		}
		return false;
	}
    //actions-----------------------------------------------------------------------------
	private void GotToWork(){
		manager.msgAddTeller(this);
	}
	private void OfferAssistance(MyCustomer account) {
		account.customer.msgHowCanIHelpYou(this,tellerNumber);
		account.custState = CustomerState.AskedAssistance;
		stateChanged();
	}
	
	private void OpenedAccount(MyCustomer myCustomer) {
		myCustomer.customer.msgHereIsYourAccount(myCustomer.accountNumber);
		myCustomer.custState = CustomerState.OpenedAccount;
		stateChanged();
	}
	
	private void DepositMoney(MyCustomer myCustomer) {
		myCustomer.customer.msgDepositSuccessful();
		myCustomer.custState = CustomerState.Leaving;
		stateChanged();
	}
	private void WithdrawMoney(MyCustomer myCustomer) {
		for (Map.Entry<Integer, AccountSystem.BankAccount> entry : AccountSystem.sharedInstance().getAccounts().entrySet()) {
			if(entry.getKey() == myCustomer.accountNumber){
				entry.getValue().addMoney(-myCustomer.moneyToWithdraw);
				myCustomer.customer.msgHereAreFunds(myCustomer.moneyToWithdraw);
			}
		}
		myCustomer.custState = CustomerState.Leaving;
		stateChanged();
	}
	private void TellerIsFree(MyCustomer myCustomer){
		manager.msgTellerFree(this);
		customers.remove(myCustomer);
	}
	private void GiveLoan(MyCustomer myCustomer) {
		
	}
}
