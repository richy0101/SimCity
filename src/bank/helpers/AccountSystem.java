package bank.helpers;

import java.util.*;


public class AccountSystem {
	
	private static List<BankAccount> accounts = new ArrayList<BankAccount>();
    private int uniqueAccountNumber = 0;
    
    private static AccountSystem sharedInstance = null;
    
    private AccountSystem() {
    	
    }
    
    public static AccountSystem sharedInstance() {
    	if(sharedInstance == null) {
    		sharedInstance = new AccountSystem();
    	}
    	return sharedInstance;
    }
    
    public int newUniqueAccountNumber(){
    	uniqueAccountNumber++;
    	return uniqueAccountNumber;
    }
    
    public void addMoney(double money,int accountNum){
    	for(BankAccount tempAccount : accounts){
    		if(tempAccount.accountNumber == accountNum){
    			tempAccount.totalFunds+=money;
    		}
    	}
    }
    
    public List<BankAccount> getAccounts() {
    	return accounts;
    }
    
    public class BankAccount {
	    double totalFunds;
	    double moneyToDeposit;
	    double moneyToWithdraw;
	    double moneyRequest;
	    int accountNumber;
	    
	    public BankAccount(){
	    	totalFunds = 0;
	    }
    }
	
}
