package bank.helpers;

import java.util.*;



public class AccountSystem {
	
	private Map<Integer,BankAccount> accounts = new HashMap<Integer,BankAccount>();
    private int uniqueAccountNumber = 0;
    
    private static AccountSystem sharedInstance = null;
    
    public AccountSystem() {
    	
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
    	for (Map.Entry<Integer, BankAccount> entry : accounts.entrySet()) {
			if(entry.getKey() == accountNum){
				entry.getValue().totalFunds += money;
			}
		}
    }
    
    public void addAccount(int uniqueNum){
    	accounts.put(uniqueNum,new BankAccount());
    }
    
    public Map<Integer,BankAccount> getAccounts() {
    	return accounts;
    }
    
    public class BankAccount {
	    double totalFunds;
	    double moneyToDeposit;
	    double moneyToWithdraw;
	    double moneyRequest;
	    //int accountNumber;
	    
	    public BankAccount(){
	    	totalFunds = 0;
	    }
    }
	
}
