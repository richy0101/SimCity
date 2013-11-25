package bank;

import bank.interfaces.BankManager;

public class Bank {
	private static Bank sharedInstance = null;
	String name = "Bank";
	BankManagerAgent manager;
    
    private Bank() {
    	manager = new BankManagerAgent();
    	manager.startThread();
    	
    }
    
    public Bank(String buildingName) {
    	name = buildingName;
    }
    
    public static Bank sharedInstance() {
    	if(sharedInstance == null) {
    		sharedInstance = new Bank();
    	}
    	return sharedInstance;
    }
    public BankManagerAgent getManager() {
    	return manager;
    }
	
	public String getName() {
		return name;
	}
	
}
