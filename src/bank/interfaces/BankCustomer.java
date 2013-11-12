package bank.interfaces;

public interface BankCustomer {
	public void msgHowCanIHelpYou(BankTeller teller);
	
	public void msgLoanDenied();
	
	public void msgHereAreFunds(double funds);
	
	public void msgHereIsYourAccount(int accountNumber);
	
	public void msgDepositSuccessful();
}
