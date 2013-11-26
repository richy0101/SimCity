package city.interfaces;

public interface Person{

	public void clearGroceries();
	public double getFunds();
	public void setFunds(double funds);
	public String getName();
	public String getTransportationMethod();
	public void setAccountNumber(int accountNumber);
	public int getAccountNumber();
	
	public void stateChanged();
	public void print(String msg);
	public void Do(String msg);

	public void msgRoleFinished();
	public void msgTransportFinished(String currentLocation);
}
