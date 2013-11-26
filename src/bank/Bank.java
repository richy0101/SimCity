package bank;


public class Bank {
	String name = "Bank";
	BankManagerAgent manager;
    public Bank() {
    	manager = new BankManagerAgent();
    	manager.startThread();
    }
    
    public Bank(String buildingName) {
    	name = buildingName;
    	manager = new BankManagerAgent();
    	manager.startThread();
    }
    
    public BankManagerAgent getManager() {
    	return manager;
    }
	
	public String getName() {
		return name;
	}
	
}
