package bank;

import city.helpers.Directory;
import bank.interfaces.*;
import agent.Role;

public class BankCustomerRole extends Role implements BankCustomer {	
//data--------------------------------------------------------------------------------
	BankTeller teller;
	BankManager manager;
	private enum CustomerState {DoingNothing, AtHost, Waiting, BeingHelped, AtTeller, Done, Left};
	private enum CustomerEvent {NoAccount,AccountOpened};
	private enum CustomerIntention {Deposit,Withdraw};
	CustomerState state = CustomerState.Waiting;
	CustomerEvent event = CustomerEvent.NoAccount;
	CustomerIntention intention = null;
	
	double moneyToDeposit;
	double moneyToWithdraw;
	double moneyRequired;
    
	String task;
	public BankCustomerRole(String task) {
		this.task = task;
		this.manager = Directory.sharedInstance().getBanks().get(0).getManager();
	}
	
	
    //messages from animation-------------------------------------------------------------
	public void msgAtTeller() {
		//from animation
		state = CustomerState.AtTeller;
		stateChanged();
	}
	public void msgAtHost() {
		//from animation
		state = CustomerState.AtHost;
		stateChanged();
	}
	public void msgAnimationFinishedLeavingBank() {
		//from animation
		state = CustomerState.Left;
		stateChanged();
	}
    
    //messages----------------------------------------------------------------------------
	public void msgHowCanIHelpYou(BankTeller teller) {
		state = CustomerState.BeingHelped;
		this.teller = teller;
	    stateChanged();
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
	
	protected boolean pickAndExecuteAction(){
		if(state == CustomerState.DoingNothing){
			askForAssistance();
			return true;
		}
		if(state == CustomerState.BeingHelped && event == CustomerEvent.NoAccount){
			openAccount();
			return true;
		}
		else if(state == CustomerState.BeingHelped && event == CustomerEvent.AccountOpened){
			if(state == CustomerState.BeingHelped && task.equals("WantsToDeposit")){
				depositMoney();
			}
			else if(state == CustomerState.BeingHelped && task.equals("WantsToWithdraw")){
				withdrawMoney();
			}
			else if(state == CustomerState.BeingHelped && task.equals("WantToGetLoan")) {
				takeOutLoan();
			}
		}
		return false;
	}
	
    //actions-----------------------------------------------------------------------------
	private void askForAssistance() {
		print("I need assistance");
		manager.msgINeedAssistance(this);
		state = CustomerState.Waiting;
	}
	
	private void openAccount() {
		teller.msgOpenAccount(this);
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
