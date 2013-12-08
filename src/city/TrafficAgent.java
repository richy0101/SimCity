package city;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.Agent;
import city.interfaces.Vehicle;

public class TrafficAgent extends Agent{

	
	//might no longer be necessary
	/*
	 * (non-Javadoc)
	 * need lists:
	 * 1) Pedestrians crossing should be priority
	 * 1) Vehicles going straight through (takes priority unless there are >4 vehicles in turning list)
	 * 2) Vehicles making turns
	 * 
	 * if someone's going from left to right on top, ppl on that lane can still right turn,
	 */
	//need a list of cars going straight through, pedestrians (can they just walk over pedestrian bridges?) 
	
	/*
	public List<Vehicle> vehiclesGoingStraight
	= Collections.synchronizedList(new ArrayList<Vehicle>());
	public List<Vehicle> vehiclesTurning
	= Collections.synchronizedList(new ArrayList<Vehicle>());
	public List<MyPassenger> passengersOnBoard
	= Collections.synchronizedList(new ArrayList<MyPassenger>());
	public List<MyPassenger> passengersOnBoard
	= Collections.synchronizedList(new ArrayList<MyPassenger>());
	*/
	
	//instead, now we need a traffic light system that acts like the bus stop. when pedestrians are waiting, it waits a
	
	@Override
	protected boolean pickAndExecuteAnAction() {

		
		return false;
	}

}
