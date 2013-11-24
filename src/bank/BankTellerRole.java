package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bank.BankManagerRole.BankTellerState;
//import bank.BankManagerRole.MyBankTeller;
import bank.interfaces.*;
import agent.Role;

public class BankTellerRole extends Role implements BankTeller {
    //data--------------------------------------------------------------------------------
	private List<BankAccount> accounts = Collections.synchronizedList(new ArrayList<BankAccount>());
	private class BankAccount {
	    BankCustomer customer;
	    double totalFunds;
	    double moneyToDeposit;
	    double moneyToWithdraw;
	    double moneyRequest;
	    int accountNumber;
	    CustomerState custState;
	    
	    public BankAccount(BankCustomer customer, CustomerState state){
	    	this.customer = customer;
	    	this.custState = state;
	    	totalFunds = 0;
	    }
    }
    private int uniqueAccountNum = 0;
    BankManager manager;
    private enum CustomerState {DoingNothing, NeedingAssistance, AskedAssistance, OpeningAccount, OpenedAccount, DepositingMoney, WithdrawingMoney, GettingLoan};
    //messages----------------------------------------------------------------------------
	public void msgAssigningCustomer(BankCustomer customer) {
		boolean newCustomer = true;
		for(BankAccount tempAccount : accounts){
			if(tempAccount.customer == customer){
				tempAccount.custState = CustomerState.NeedingAssistance;
				newCustomer = false;
			}
		}
		if(newCustomer == true){
			accounts.add(new BankAccount(customer, CustomerState.NeedingAssistance));
		}
	    stateChanged();
	}
	
	public void msgOpenAccount(BankCustomer customer) {
		for(BankAccount tempAccount : accounts){
			if(tempAccount.customer == customer){
				tempAccount.accountNumber = uniqueAccountNum;
				uniqueAccountNum++;
				tempAccount.custState = CustomerState.OpeningAccount;
			}
		}
	    stateChanged();
	}
	
	public void msgDepositMoney(int accountNumber, double money) {
		for(BankAccount tempAccount : accounts){
			if(tempAccount.accountNumber == accountNumber){
				tempAccount.moneyToDeposit = money;
				tempAccount.custState = CustomerState.DepositingMoney;
			}
		}
	    stateChanged();
	}
	
	public void msgWithdrawMoney(int accountNumber, double money) {
		for(BankAccount tempAccount : accounts){
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
		synchronized(this.accounts){
			for(BankAccount tempCustomer: accounts){
				if(tempCustomer.custState == CustomerState.NeedingAssistance){
					OfferAssistance(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.accounts){
			for(BankAccount tempCustomer: accounts){
				if(tempCustomer.custState == CustomerState.OpeningAccount){
					OpenedAccount(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.accounts){
			for(BankAccount tempCustomer: accounts){
				if(tempCustomer.custState == CustomerState.DepositingMoney){
					DepositMoney(tempCustomer);
					return true;
				}
			}
		}
		synchronized(this.accounts){
			for(BankAccount tempCustomer: accounts){
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
	private void OfferAssistance(BankAccount account) {
		account.customer.msgHowCanIHelpYou(this);
		account.custState = CustomerState.AskedAssistance;
		stateChanged();
	}
	
	private void OpenedAccount(BankAccount account) {
		account.customer.msgHereIsYourAccount(account.accountNumber);
		account.custState = CustomerState.OpenedAccount;
		stateChanged();
	}
	
	private void DepositMoney(BankAccount account) {
		account.totalFunds +=
	}
	
	private void GiveCustomerMoney(BankAccount myCustomer) {
		
	}
	
	private void GiveLoan(BankAccount myCustomer) {
		
	}
}
