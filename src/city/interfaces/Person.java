package city.interfaces;

public interface Person{

	public String getTransportationMethod();
	public void msgActionComplete();
	public void msgWakeUp() ;
	public void msgCookingDone();
	public void msgDoneEating();
	public void msgGoWork();
	public void msgDoneWorking();
	public void msgGoHome();
	public void msgRentPaid();
	public void msgRoleFinished();
	public void msgTransportFinished(String s);
	public void msgAtHome();
	public void msgPayRent();
	
	public void clearGroceries();
	public String getName();
	public void setName(String name);
	public double getFunds();
	public void setFunds(double funds);
	public int getAccountNumber();
	public void setAccountNumber(int accountNumber);
	
	public void stateChanged();
	public void print(String msg);
	public void Do(String msg);


}
