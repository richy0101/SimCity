package bank;

public class Bank {
	private static Bank sharedInstance = null;
    
    private Bank() {
    	
    }
    
    public static Bank sharedInstance() {
    	if(sharedInstance == null) {
    		sharedInstance = new Bank();
    	}
    	return sharedInstance;
    }

}
