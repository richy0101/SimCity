package city.test;

import agent.Role;
import bank.BankTellerRole;
import junit.framework.*;
import city.PersonAgent.PersonState;
import city.test.mock.MockTransportation;
import city.PersonAgent;
import city.test.mock.*;



public class PersonTest extends TestCase {
	
	MockTransportation passenger;
	PersonAgent person;
	MockTransportation trole;

	public void setUp() throws Exception{
		super.setUp();	
		String jobLocation = "Bank";
		String house = "House5";
		String name = "JUNITTestPersonTeller";
		MockRole transport = new MockRole("jobLocation");
		MockRole job = new MockRole("jobLocation");
		PersonAgent person = new PersonAgent(job, jobLocation, house, name);
			
	}	
	
	//wake up, decide to eat in or out, assign role and 
	public void testCase1(){
		//preconditions:
		
		//Waking up:
		person.msgWakeUp();
		assertFalse("person's hasWorked should be false but is true", person.hasWorked);
		assertTrue("person's state should be wantfood but isn't", person.getPersonState() == PersonState.WantFood);

	}
}
