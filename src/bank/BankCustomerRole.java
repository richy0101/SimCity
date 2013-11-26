package bank;

import bank.gui.BankCustomerGui;
import bank.interfaces.*;
import agent.Role;

public class BankCustomerRole extends Role implements BankCustomer {
    //data--------------------------------------------------------------------------------
	private BankTeller teller;
	private BankManager manager;
	private enum CustomerState {DoingNothing, Waiting, GoingToTeller, BeingHelped, AtManager, Done, Gone};
	private CustomerState state = CustomerState.DoingNothing;
	
	private BankCustomerGui customerGui;
	
	private double moneyToDeposit = 0;
	private double moneyToWithdraw = 100;
	private double moneyRequired = 0;
	
	private int accountNumber = 0;
	private int tellerNumber = -1;  //hack initializer for unit tests (-1 as null)
    
	private String task;

	public BankCustomerRole(String task, double moneyToDeposit, double moneyRequired) {
		this.task = task;
		//this.manager = Directory.sharedInstance().getBanks().get(0).getManager();
		this.moneyRequired = moneyRequired;
		this.moneyToDeposit = moneyToDeposit;
		customerGui = new BankCustomerGui(this);
		
	}
	
	
    //    //messages from animation-------------------------------------------------------------
	public void msgAtTeller() {
		//from animation
		state = CustomerState.BeingHelped;
		stateChanged();
	}
	public void msgAtManager() {
		//from animation
		state = CustomerState.AtManager;
		stateChanged();
	}
	public void msgAnimationFinishedLeavingBank() {
		//from animation
		state = CustomerState.Gone;
		stateChanged();
	}
    
    //messages----------------------------------------------------------------------------
	public void msgHowCanIHelpYou(BankTeller teller, int tellerNumber) {
		state = CustomerState.GoingToTeller;
		this.teller = teller;
		this.tellerNumber = tellerNumber;
	    stateChanged();
	}
	
	public void msgLoanDenied() {
		state = CustomerState.Done;
		teller.msgThankYouForAssistance(this);
		stateChanged();
		
	}
	
	public void msgHereAreFunds(double funds) {
		getPersonAgent().setFunds(getPersonAgent().getFunds() + funds);
		state = CustomerState.Done;
		teller.msgThankYouForAssistance(this);
		leaveBank();
		stateChanged();
	}
	
	public void msgHereIsYourAccount(int accountNumber) {
		getPersonAgent().setAccountNumber(accountNumber);
		this.accountNumber = accountNumber;
		stateChanged();
	}
	
	public void msgDepositSuccessful() {
		state = CustomerState.Done;
		teller.msgThankYouForAssistance(this);
		leaveBank();
		stateChanged();
	}
	
    //scheduler---------------------------------------------------------------------------
	
	protected boolean pickAndExecuteAction(){
		if(state == CustomerState.DoingNothing){
			askForAssistance();
			return true;
		}
		if(state == CustomerState.GoingToTeller) {
			goToTeller();
			return true;
		}
		if(state == CustomerState.BeingHelped && accountNumber == 0){
			openAccount();
			return true;
		}
		if(state == CustomerState.BeingHelped && accountNumber != 0){
			if(state == CustomerState.BeingHelped && task.equals("WantsToDeposit")){
				depositMoney();
			}
			else if(state == CustomerState.BeingHelped && task.equals("WantsToWithdraw")){
				withdrawMoney();
			}
			else if(state == CustomerState.BeingHelped && task.equals("WantToGetLoan")) {
				takeOutLoan();
			}
			return true;
		}
		if(state == CustomerState.Done) {
			leaveBank();
			return true;
		}
		
		return false;
	}
	
    //actions-----------------------------------------------------------------------------
	private void askForAssistance() {
		print("I need assistance");
		customerGui.DoGoToManager();
		manager.msgINeedAssistance(this);
		state = CustomerState.Waiting;
	}
	
	private void openAccount() {
		print("I need my account opened");
		teller.msgOpenAccount(this);
	}
	
	private void takeOutLoan() {
		print("I need to take out a loan");
		teller.msgIWantLoan(accountNumber, moneyRequired);
	}
	
	private void depositMoney() {
		print("I need to deposit money");
		teller.msgDepositMoney(accountNumber, moneyToDeposit);
		getPersonAgent().setFunds(getPersonAgent().getFunds() - moneyToDeposit);
	}
	
	private void withdrawMoney() {
		print("I need to withdraw money");
		teller.msgWithdrawMoney(accountNumber, moneyToWithdraw);
		getPersonAgent().setFunds(getPersonAgent().getFunds() + moneyToWithdraw);
	}
	
	private void leaveBank() {
		print("Leaving bank");
		customerGui.DoLeaveBank();
		
	}
	
	private void goToTeller() {
		print("Going to bank teller");
		customerGui.DoGoToTeller(tellerNumber);
		
	}
    //GUI Actions-------------------------------------------------------------------------
	
	
	//Getters for unit tests--------------------------------------------------------------
	/**
	 * @return the state
	 */
	public String getState() {
		return state.toString();
	}


	/**
	 * @return the moneyToDeposit
	 */
	public double getMoneyToDeposit() {
		return moneyToDeposit;
	}


	/**
	 * @return the moneyToWithdraw
	 */
	public double getMoneyToWithdraw() {
		return moneyToWithdraw;
	}


	/**
	 * @return the moneyRequired
	 */
	public double getMoneyRequired() {
		return moneyRequired;
	}


	/**
	 * @return the accountNumber
	 */
	public int getAccountNumber() {
		return accountNumber;
	}


	/**
	 * @return the tellerNumber
	 */
	public int getTellerNumber() {
		return tellerNumber;
	}

}
