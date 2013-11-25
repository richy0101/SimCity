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
	    
	    public MyCustomer(BankCustomerRole customer, CustomerState state){
	    	this.customer = customer;
	    	this.custState = state;
	    }
    }
    private int tellerNumber;
    BankManager manager;
    private enum CustomerState {ArrivedAtWork, DoingNothing, NeedingAssistance, AskedAssistance, OpeningAccount, OpenedAccount, DepositingMoney, WithdrawingMoney, GettingLoan};
    //messages----------------------------------------------------------------------------
	public void msgAssigningCustomer(BankCustomerRole customer) {
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
			
		}
		for(MyCustomer tempAccount : customers){
			if(tempAccount.accountNumber == accountNumber){
				tempAccount.moneyToDeposit = money;
				tempAccount.custState = CustomerState.DepositingMoney;
			}
		}
	    stateChanged();
	}
	
	public void msgWithdrawMoney(int accountNumber, double money) {
		for(MyCustomer tempAccount : customers){
			if(tempAccount.accountNumber == accountNumber){
				tempAccount.moneyToDeposit = money;
				tempAccount.custState = CustomerState.DepositingMoney;
			}
		}
	    stateChanged();
	}
	
	public void msgIWantLoan(int accountNumber, double moneyRequest) {
		
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
				if(tempCustomer.custState == CustomerState.NeedingAssistance){
					OfferAssistance(tempCustomer);
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
	
	private void OpenedAccount(MyCustomer account) {
		account.customer.msgHereIsYourAccount(account.accountNumber);
		account.custState = CustomerState.OpenedAccount;
		stateChanged();
	}
	
	private void DepositMoney(MyCustomer account) {
		account.totalFunds +=
	}
	
	private void GiveCustomerMoney(MyCustomer myCustomer) {
		
	}
	
	private void GiveLoan(MyCustomer myCustomer) {
		
	}
}
