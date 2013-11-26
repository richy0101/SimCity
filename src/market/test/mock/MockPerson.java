package market.test.mock;

import java.util.Map;

import city.interfaces.Person;
import market.interfaces.Market;

public class MockPerson extends Mock implements Person {

	public Market market;
	public EventLog log;
	public Map<String, Integer> groceryList;
	public double funds;

	public MockPerson(String name) {
		super(name);
		log = new EventLog();
	}

	@Override
	public void clearGroceries() {
		log.add(new LoggedEvent("ClearGroceries called"));
		groceryList.clear();
	}

	@Override
	public double getFunds() {
		log.add(new LoggedEvent("GetFunds called"));
		return funds;
	}

	@Override
	public void setFunds(double funds) {
		log.add(new LoggedEvent("SetFunds called. Amount = $" + funds));
		this.funds = funds;		
	}
	
	@Override
	public void msgRoleFinished() {
		log.add(new LoggedEvent("msgRoleFinished called"));
	}

	@Override
	public void stateChanged() {
	}

	@Override
	public void print(String msg) {
	}

	@Override
	public void Do(String msg) {
	}

	@Override
	public String getTransportationMethod() {
		return null;
	}

	@Override
	public void setAccountNumber(int accountNumber) {
		
	}

	@Override
	public int getAccountNumber() {
		return 0;
	}

	@Override
	public void msgTransportFinished(String currentLocation) {
		
	}
	

}
