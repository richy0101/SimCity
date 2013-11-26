package bank;

import gui.Building;

import java.util.ArrayList;
import city.helpers.Directory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import bank.gui.BankTellerGui;
import bank.helpers.AccountSystem;
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
	    double moneyToDeposit;
	    
	    public MyCustomer(BankCustomer customer, CustomerState state){
	    	this.customer = customer;
	    	this.custState = state;
	    }
    }
	
	private static final int MAXLOAN = 1000;
	private Semaphore doneAnimation = new Semaphore(0,true);
    private int registerNumber;
    BankManager manager;
    private BankTellerGui tellerGui;
    private enum TellerState {ArrivedAtWork, AtManager, GoingToRegister, ReadyForCustomers, DoneWorking, Gone};
    TellerState state = TellerState.ArrivedAtWork;
    
    private enum CustomerState {NeedingAssistance, 
    	AskedAssistance, OpeningAccount, OpenedAccount, 
    	DepositingMoney, WithdrawingMoney, LoanAccepted, 
    	LoanRejected, Leaving};
    String myLocation;
    
    public BankTellerRole(String location){
    	tellerGui = new BankTellerGui(this);
    	myLocation = location;
    	List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(tellerGui);
			}
		}
    }
    
    public void setGui(BankTellerGui tellerGui){
    	this.tellerGui = tellerGui;
    }
    
    //messages----------------------------------------------------------------------------
	public void msgGoToRegister(int registerNumber) {
		this.registerNumber = registerNumber;
		state = TellerState.GoingToRegister;
		stateChanged();
		
	}
	public void msgAssigningCustomer(BankCustomer customer) {
		customers.add(new MyCustomer(customer, CustomerState.NeedingAssistance));
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
		for(MyCustomer tempCustomer : customers){
			if(tempCustomer.accountNumber == accountNumber){
				tempCustomer.moneyToDeposit = money;
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
		for(MyCustomer customer : customers) {
			if(customer.accountNumber == accountNumber) {
				if(AccountSystem.sharedInstance().getAccounts().get(accountNumber).elligibleForLoan) {
					customer.custState = CustomerState.LoanAccepted;
				}
				else {
					customer.custState = CustomerState.LoanRejected;
				}
			}
		}
		stateChanged();
	}
	
	public void msgThankYouForAssistance(BankCustomer customer) {
		for(MyCustomer mCustomer : customers) {
			if(mCustomer.equals(customer)) {
				mCustomer.custState = CustomerState.Leaving;
			}
		}
		
	}
	
	public void msgDoneWorking() {
		state = TellerState.DoneWorking;
		stateChanged();
	}
    //scheduler---------------------------------------------------------------------------
	protected boolean pickAndExecuteAction(){
		if(state == TellerState.ArrivedAtWork) {
			DoGoCheckInWithManager();
			return true;
		}
		if(state == TellerState.GoingToRegister) {
			DoGoToRegister();
			return true;
		}
		if(state == TellerState.ReadyForCustomers) {
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.NeedingAssistance){
						OfferAssistance(tempCustomer);
						return true;
					}
				}
			}
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.OpeningAccount){
						OpenedAccount(tempCustomer);
						return true;
					}
				}
			}
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.DepositingMoney){
						DepositMoney(tempCustomer);
						return true;
					}
				}
			}
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.WithdrawingMoney){
						WithdrawMoney(tempCustomer);
						return true;
					}
				}
			}
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.LoanAccepted){
						GiveLoan(tempCustomer);
						return true;
					}
				}
			}
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.LoanRejected){
						RejectLoan(tempCustomer);
						return true;
					}
				}
			}
			synchronized(customers){
				for(MyCustomer tempCustomer: customers){
					if(tempCustomer.custState == CustomerState.Leaving){
						CustomerFinished(tempCustomer);
						return true;
					}
				}
			}
			manager.msgTellerFree(this);
		}
		if(state == TellerState.DoneWorking) {
			LeaveBank();
			return true;
		}
		return false;
	}

	//actions-----------------------------------------------------------------------------
	private void DoGoCheckInWithManager() {
		tellerGui.DoGoToManager();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = TellerState.AtManager;
	}
    private void DoGoToRegister() {
		tellerGui.DoGoToRegister(registerNumber);
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = TellerState.ReadyForCustomers;
		manager.msgTellerFree(this);
	}
	private void OfferAssistance(MyCustomer account) {
		print("What do you need help with?");
		account.customer.msgHowCanIHelpYou(this,registerNumber);
		account.custState = CustomerState.AskedAssistance;
	}
	
	private void OpenedAccount(MyCustomer myCustomer) {
		print("Opening up your account");
		myCustomer.customer.msgHereIsYourAccount(myCustomer.accountNumber);
		myCustomer.custState = CustomerState.OpenedAccount;
	}
	
	private void DepositMoney(MyCustomer myCustomer) {
		print("Depositing your money into your account");
		AccountSystem.sharedInstance().getAccounts().get(myCustomer.accountNumber).depositMoney(myCustomer.moneyToDeposit);
		myCustomer.customer.msgDepositSuccessful();
		myCustomer.custState = CustomerState.Leaving;
	}
	
	private void WithdrawMoney(MyCustomer myCustomer) {
		print("Withdrawing money from your account");
		
		AccountSystem.sharedInstance().getAccounts().get(myCustomer.accountNumber).withdrawMoney(myCustomer.moneyToWithdraw);
		myCustomer.customer.msgHereAreFunds(myCustomer.moneyToWithdraw);
		myCustomer.custState = CustomerState.Leaving;
	}
	
	private void CustomerFinished(MyCustomer myCustomer){
		print("Finished aiding cusomter");
		customers.remove(myCustomer);
	}
	
	private void GiveLoan(MyCustomer myCustomer) {
		AccountSystem.sharedInstance().getAccounts().get(myCustomer.accountNumber).loanAccepted(MAXLOAN);
		myCustomer.customer.msgHereAreFunds(MAXLOAN);
		myCustomer.custState = CustomerState.Leaving;
	}
	
	private void RejectLoan(MyCustomer myCustomer) {
		myCustomer.customer.msgLoanDenied();
		myCustomer.custState = CustomerState.Leaving;
	}
	
	private void LeaveBank() {
		tellerGui.DoLeaveBank();
		try {
			doneAnimation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = TellerState.Gone;
		getPersonAgent().msgRoleFinished();
	}
	//animation messages-------------------------------------------------------------------
	public void msgAtRegister() {
		doneAnimation.release();
	}
	public void msgAtManager() {
		doneAnimation.release();
	}
	public void msgAnimationFinishedLeavingBank() {
		doneAnimation.release();
	}
}
