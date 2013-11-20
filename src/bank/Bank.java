package bank;

public class Bank {
	private static Bank sharedInstance = null;
	String name;
    
    private Bank() {
    	
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
    
	public Object getOwner() {
		return null;
	}
	
	public String getName() {
		return name;
	}
	
}
