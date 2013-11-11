package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bank.interfaces.*;
import agent.Role;

public class BankTellerRole extends Role implements BankTeller {
//data--------------------------------------------------------------------------------
	private List<MyBankCustomer> customers = Collections.synchronizedList(new ArrayList<MyBankCustomer>());
	private class MyBankCustomer {
	    BankCustomer customer;
	    double moneyToDeposit;
	    double moneyToWithdraw;
	    double moneyRequest;
	    int accountNumber;
	    CustomerState state;
	 }
	 BankManager manager;
	 private enum CustomerState {NeedingAssistance, AskedAssistance, OpeningAccount, DepositingMoney, WithdrawingMoney, GettingLoan};
//messages----------------------------------------------------------------------------
	public void msgAssigningCustomer(BankCustomer customer) {
		
	}
	
	public void msgOpenAccount(BankCustomer customer) {
		
	}
	
	public void msgDepositMoney(int accountNumber, double money) {
		
	}
	
	public void msgWithdrawMoney(int accountNumber, double money) {
		
	}
	
	public void msgIWantLoan(int accountNumber, double moneyRequest) {
		
	}
//scheduler---------------------------------------------------------------------------
//actions-----------------------------------------------------------------------------	
	private void OfferAssistance(MyBankCustomer customer) {
		
	}
	
	private void CreateAccount(MyBankCustomer customer) {
		
	}
	
	private void DepositMoney(MyBankCustomer customer) {
		
	}
	
	private void GiveCustomerMoney(MyBankCustomer customer) {
		
	}
	
	private void GiveLoan(MyBankCustomer customer) {
		
	}
}
