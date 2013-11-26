package city.test;

import city.interfaces.Bus;
import city.helpers.BusHelper;
import city.BusAgent;
import city.BusAgent.Event;
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
	//preconditions: 
		assertTrue("should be no passengers at stop 1, but there is, ", BusHelper.sharedInstance().getWaitingPassengersAtStop1().isEmpty());
		assertTrue("passengersOnBoard should be empty, but isn't", bus.passengersOnBoard.isEmpty());
		assertTrue("bus's state should be driving but isn't", bus.state==State.driving);
	
	//Step 1: adding customer to bus stop
		BusHelper.sharedInstance().getWaitingPassengersAtStop1().add(passenger1);
		assertEquals("should be 1 passenger at stop 1, but there are "+BusHelper.sharedInstance().getWaitingPassengersAtStop1().size(), 1,BusHelper.sharedInstance().getWaitingPassengersAtStop1().size());	
		
	//Step 2: bus arrives at stop and should stop accordingly
		bus.msgAtStopOne();
		assertTrue("bus event should record reachedStop, but doesn't", bus.event==Event.reachedStop);
		assertTrue("Bus's scheduler should have returned true (one action to do), but didn't.", bus.pickAndExecuteAnAction());
		assertTrue("bus state should record stopping, but doesn't", bus.state==State.stopping);
		assertTrue("bus event should record stopped after running scheduler", bus.event==Event.stopped);
	
	//Step 3: notifying passengers on board to alight
		assertTrue("Bus's scheduler should have returned true (one action to do), but didn't.", bus.pickAndExecuteAnAction());
		assertTrue("bus state should record notifyingToAlight after running scheduler", bus.state==State.notifyingPassengersToAlightBus);
		assertTrue("bus event should record notifiedToAlight after running scheduler", bus.event==Event.notifiedPassengersToAlightBus);
	
	//Step 4: waiting for passengers to alight
		assertTrue("Bus's scheduler should have returned true (one action to do), but didn't.", bus.pickAndExecuteAnAction());
		assertTrue("bus state should record waitForAlighting after running scheduler", bus.state==State.waitForAlighting);
		assertTrue("bus event should record passengersAlighted after running scheduler", bus.event==Event.passengersAlighted);
		assertTrue("passengersOnBoard should be empty, but isn't", bus.passengersOnBoard.isEmpty());
		
	//Step 5: notifying passengers to board bus
		assertTrue("Bus's scheduler should have returned true (one action to do), but didn't.", bus.pickAndExecuteAnAction());
		assertTrue("bus state should record notifyingPassengersToBoardBus after running scheduler", bus.state==State.notifyingPassengersToBoardBus);
		assertTrue("bus event should record notifiedPassengersToBoardBus after running scheduler", bus.event==Event.notifiedPassengersToBoardBus);
		assertTrue("passengersOnBoard should be empty, but isn't", bus.passengersOnBoard.isEmpty());
			
	//Step 6: passenger boards bus
		assertTrue("Bus's scheduler should have returned true (one action to do), but didn't.", bus.pickAndExecuteAnAction());
		assertTrue("bus state should record waitForBoarding after running scheduler", bus.state==State.waitForBoarding);
		assertTrue("bus event should record passengersBoarded after running scheduler", bus.event==Event.passengersBoarded);
		bus.msgBoardingBus(passenger1);
		assertEquals("passengersOnBoard should have one passenger, but it has "+ bus.passengersOnBoard.size(), 1, bus.passengersOnBoard.size());
	
	//Step 7:
		
	}
	
	

}
