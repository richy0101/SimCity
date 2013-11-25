package city;


//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.Semaphore;

import agent.Role;
import city.interfaces.Transportation;

public class TransportationRole extends Role implements Transportation  {

	String destination;
	String stopDestination; //for bus stop
	CarAgent car;
	BusAgent bus;
	Boolean hasCar = false;
	
	public enum TransportationState 
		{Walking, NeedsToTravel, InTransit, AtDestination, None};
		
	TransportationState state = TransportationState.None;

	
	public TransportationRole(String workLocation) {
		super();
		hasCar = true; //hack for normative
		state = TransportationState.NeedsToTravel; // hack for normative;
		destination = workLocation;
		
		need a function to assign stopDestination based on final destination
		
	}
	
	/*
	 * Messages
	 */
	
	void msgThisIsYourStop(int BusStopNumber) {
		//not relevant for norm scenario
	}
	
	void msgArrivedAtDestination(String destination) {
		print("Car successfully took me to " + destination + ".");
		state = TransportationState.AtDestination;
		stateChanged();
	}

	
	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	public boolean pickAndExecuteAnAction() {
		if(state == TransportationState.Walking) {
			WalkToDestination();
			return true;
		}
		
		if(state == TransportationState.NeedsToTravel) {
			GetAVehicle();
			return true;
		}
		
		if(state == TransportationState.AtDestination) {
			GetOffVehicle();
			return true;
		}
		
		return false;
	}
	
	
	/*
	 * Actions
	 */
	private void WalkToDestination() {
		//gui
		
		state = TransportationState.None;
		stateChanged();
	}
	
	private void GetAVehicle() {
		/**if(hasCar) {
			//create car gui in mainwindow gui just outside of source
			car.msgTakeMeHere(destination);
		}
		else if (!hasCar) {
			//create transportationrole gui in mainwindow
			//have transportationrole gui walk to bus stop
			
			//bus.msgINeedARide(destination);
		}**/
		
		
		state = TransportationState.InTransit;
		stateChanged();
	}
	
	private void GetOffVehicle() {
		if(hasCar) {
			//remove car gui from main window
		}
		else if (!hasCar) {
			//create transportationrole gui at bus stop
			//have transportationrole gui walk to destination
			//remove transportationrole gui
		}
		
		state = TransportationState.None;
		getPersonAgent().msgRoleFinished();
		//change roles
	}
}
