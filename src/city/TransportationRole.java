package city;


//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.Semaphore;

import agent.Agent;
import city.interfaces.Transportation;

public class TransportationRole extends Agent implements Transportation  {

	String destination;
	CarAgent car;
	BusAgent bus;
	Boolean hasCar = false;
	
	public enum TransportationState 
		{Walking, NeedsToTravel, InTransit, AtDestination, None};
		
	TransportationState state = TransportationState.None;

	
	public TransportationRole() {
		super();
	}
	
	/*
	 * Messages
	 */
	
	void msgThisIsBusStop(int BusStopNumber) {
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
	protected boolean pickAndExecuteAnAction() {
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
		if(hasCar) {
			//create vehicle gui in mainwindow gui at source
			//remove person gui from source gui
			car.msgTakeMeHere(destination);
		}
		else if (!hasCar) {
			//create person gui in mainwindow gui at source
			//remove person gui from source gui
			//bus.msgINeedARide(destination);
		}
		
		state = TransportationState.InTransit;
		stateChanged();
	}
	
	private void GetOffVehicle() {
		if(hasCar) {
			//remove gui from main window
			state = TransportationState.None;
			stateChanged();
		}
		else if (!hasCar) {
			//create gui at bus stop
			state = TransportationState.Walking;
			stateChanged();
		}
	}
}
