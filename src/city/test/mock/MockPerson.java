package city.test.mock;

import city.test.mock.EventLog;
import city.interfaces.Person;


public class MockPerson extends Mock implements Person {

	public EventLog log;

	public MockPerson(String name) {
		super(name);
		log = new EventLog();
	}
	
}
