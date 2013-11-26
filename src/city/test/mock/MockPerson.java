package city.test.mock;

import city.PersonAgent.TransportationMethod;
import city.test.mock.EventLog;
import city.interfaces.Person;


public class MockPerson extends Mock implements Person {
	
	public enum TransportationMethod {OwnsACar, TakesTheBus, Walks};

	public EventLog log;
	public city.PersonAgent.TransportationMethod transMethod;

	public MockPerson(String name) {
		super(name);
		log = new EventLog();
		//String startingLocation=
	}

	@Override
	public void clearGroceries() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getFunds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFunds(double funds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransportationMethod() {
		return ("Bus");
	}

	@Override
	public void setAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAccountNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stateChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Do(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgRoleFinished() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgTransportFinished(String currentLocation) {
		// TODO Auto-generated method stub
		
	}
	
}
