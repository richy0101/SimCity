package city.test;

import city.interfaces.Bus;
import city.helpers.BusHelper;
import city.BusAgent;
import city.BusAgent.State;
import city.test.mock.MockTransportation;
import junit.framework.*;

public class BusTest extends TestCase{
	
	BusAgent bus;
	MockTransportation passenger1;
	MockTransportation passenger2;
	MockTransportation passenger3;	
	
	public void setUp() throws Exception{
		super.setUp();		
		bus = new BusAgent();
		bus.state= State.driving;
		passenger1= new MockTransportation("Person 1");
		passenger1= new MockTransportation("Person 2");
		passenger1= new MockTransportation("Person 3");		
	}	
	
	public void testOnePassenger(){
	//how do you check that the gui is there?
	 //check preconditions, bus is driving, person is waiting and not added prematurely
		
		assertTrue("passengersOnBoard should be empty, but isn't", bus.passengersOnBoard.isEmpty());
		
	//Step 1: person messages Bus that he wants to get on
		bus.msgBoardingBus(passenger1);
		assertEquals("passengersOnBoard should have one passenger, but it has "+ bus.passengersOnBoard.size(), 1, bus.passengersOnBoard.size());
		
	}
	
	

}
