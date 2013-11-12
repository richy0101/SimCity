package bank.interfaces;

public interface BankManager {
	public void msgINeedAssistance(BankCustomer customer);
	
	public void msgTellerFree(BankTeller teller);
	
	public void msgAddTeller(BankTeller teller);
}
