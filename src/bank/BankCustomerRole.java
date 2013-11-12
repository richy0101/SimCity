package bank;

import bank.interfaces.*;
import agent.Role;

public class BankCustomerRole extends Role implements BankCustomer {	
//data--------------------------------------------------------------------------------
	BankTeller teller;
	BankManager manager;
	private enum CustomerState {DoingNothing, Waiting, BeingHelped, Done};
	CustomerState state = CustomerState.DoingNothing;
	

//messages----------------------------------------------------------------------------
	public void msgHowCanIHelpYou(BankTeller teller) {
		
	}
	
	public void msgLoanDenied() {
		
	}
	
	public void msgHereAreFunds(double funds) {
		
	}
	
	public void msgHereIsYourAccount(int accountNumber) {
		
	}
	
	public void msgDepositSuccessful() {
		
	}
//scheduler---------------------------------------------------------------------------
	
	
//actions-----------------------------------------------------------------------------
	private void askForAssistance() {
		
	}
	
	private void openAccount() {
		
	}
	
	private void takeOutLoan() {
		
	}
	
	private void depositMoney() {
		
	}
	
	private void withdrawMoney() {
		
	}
	
	private void leaveBank() {
		
	}
//GUI Actions-------------------------------------------------------------------------
}
