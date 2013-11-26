package city.test;

import agent.Role;
import bank.BankTellerRole;
import bank.test.mock.MockBankTeller;
import junit.framework.*;
import city.PersonAgent.PersonState;
import city.test.mock.MockTransportation;
import city.PersonAgent;

public class PersonTest extends TestCase {
	
	MockTransportation passenger;
	PersonAgent person;
	MockTransportation trole;

	public void setUp() throws Exception{
		super.setUp();	
		String a5 = "Bank";
		String b5 = "House5";
		String name5 = "BankDepositPerson5";
		Role role5 = new MockBankTeller("Bank");
		PersonAgent person = new PersonAgent(role5, a5 , b5, name5);
		role5.setPerson(person);
		MockTransportation trole;
			
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
