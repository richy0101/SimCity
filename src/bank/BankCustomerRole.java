package bank;

import city.helpers.Directory;
import bank.interfaces.*;
import agent.Role;

public class BankCustomerRole extends Role implements BankCustomer {	
//data--------------------------------------------------------------------------------
	BankTeller teller;
	BankManager manager;
	private enum CustomerState {DoingNothing, Waiting, BeingHelped, Done};
	private enum BankTask {WantsToWithdraw, WantsToGetLoan, WantsToDeposit, WantsToRob};
	CustomerState state = CustomerState.DoingNothing;
	BankTask task;
	public BankCustomerRole(String task) {
		this.task = BankTask.valueOf(task);
		this.manager = Directory.sharedInstance().getBanks().get(0).getManager();
	}
	

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
