package city;


//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.Semaphore;

import java.util.concurrent.Semaphore;

import agent.Role;
import city.PersonAgent.TransportationMethod;
import city.gui.TransportationGui;
import city.helpers.BusHelper;
import city.helpers.Directory;
import city.interfaces.Transportation;
import city.PersonAgent;

public class TransportationRole extends Role implements Transportation  {

	String destination;
	String startingLocation;
	String currentLocation;
	//String stopDestination; //for bus stop
	private Semaphore actionComplete = new Semaphore(0,true);
	CarAgent car;
	BusAgent bus;
	Boolean hasCar = false;
	int startX, startY, startStopX, startStopY, endStopX, endStopY, startStopNumber, finalStopNumber;
	
	public enum BusStop
		{stop1, stop2, stop3, stop4, none}
	BusStop stopDestination = BusStop.none; 
	
	public enum TransportationState 
		{Walking, NeedsToTravel, InTransit, AtDestination, None, WaitingForBus, OnBus, GettingOnBus, AtFinalStop, JustGotOffBus, Finished};	
	TransportationState state = TransportationState.None;
	
	TransportationGui guiToStop;//use for bus stop
	TransportationGui guiToDestination;
	
	public TransportationRole(String destination, String startingLocation) {
		super();
		hasCar = false; //hack for normative
		state = TransportationState.NeedsToTravel; // hack for normative;
		this.destination = destination;
		this.startingLocation = startingLocation;
	}
	
	/*
	 * Messages
	 */

	public void msgActionComplete() {
		actionComplete.release();
	}
	
	public void msgGetOnBus(BusAgent b) {
		print("Bus is here");
		this.bus = b;
		state = TransportationState.GettingOnBus;
		stateChanged();
	}
	
	public void msgArrivedAtDestination(String destination) {
		print("Car successfully took me to " + destination + ".");
		currentLocation= destination;
		state = TransportationState.AtDestination;
		stateChanged();
	}
	public void msgAtStop(int stopNumber) {
		if(stopNumber == finalStopNumber) {
			state = TransportationState.AtFinalStop;
			currentLocation= "BusStop"+stopNumber;
		}
		stateChanged();
	}
	
	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	public boolean pickAndExecuteAnAction() {
		if (state == TransportationState.AtDestination) {
			EnterBuilding();
			return true;
		}
		if	(state == TransportationState.JustGotOffBus) {
			WalkToFinalDestination();
			return true;
		}
		if (state == TransportationState.AtFinalStop) {
			GetOffBus();
			return true;
		}
		if(state == TransportationState.GettingOnBus) {
			GetOnBus();
			return true;
		}	
		if(state == TransportationState.NeedsToTravel) {
			GetAVehicle();
			return true;
		}
		
		return false;
	}
	/*
	 * Actions
	 */
	
	private void EnterBuilding() {
		state = TransportationState.Finished;
		Directory.sharedInstance().getCityGui().getMacroAnimationPanel().removeGui(guiToDestination);
		getPersonAgent().msgTransportFinished(currentLocation);
		stateChanged();
	}

	private void WalkToFinalDestination() {
		state = TransportationState.Walking;
		int finalDestinationX = Directory.sharedInstance.getDirectory().get(destination).xCoordinate;
		int finalDestinationY = Directory.sharedInstance.getDirectory().get(destination).yCoordinate;
		guiToDestination = new TransportationGui(this, endStopX, endStopY, finalDestinationX, finalDestinationY);
		Directory.sharedInstance().getCityGui().getMacroAnimationPanel().addGui(guiToDestination);
		print("adding gotodestination to macro");
		actionComplete.acquireUninterruptibly();
		state = TransportationState.AtDestination;
		stateChanged();
	}

	private void GetOnBus() {
		print("Getting on bus");
		Directory.sharedInstance().getCityGui().getMacroAnimationPanel().removeGui(guiToStop);
		BusHelper.sharedInstance().removeWaitingPerson(this, startStopNumber);
		bus.msgBoardingBus(this);
		state = TransportationState.OnBus;
		stateChanged();
	}


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
		if (getPersonAgent().getTransportationMethod().contains("Bus")) {
			startStopX = BusHelper.sharedInstance().busStopEvaluator.get(startingLocation).xCoordinate;
			startStopY = BusHelper.sharedInstance().busStopEvaluator.get(startingLocation).yCoordinate;
			endStopX = BusHelper.sharedInstance().busStopEvaluator.get(destination).xCoordinate;
			endStopY = BusHelper.sharedInstance().busStopEvaluator.get(destination).yCoordinate;
			finalStopNumber = BusHelper.sharedInstance().busStopToInt.get(destination);
			startStopNumber = BusHelper.sharedInstance().busStopToInt.get(startingLocation);
			print("Want bus stop " + startStopNumber);
		}
		startX = Directory.sharedInstance.getDirectory().get(startingLocation).xCoordinate;
		startY = Directory.sharedInstance.getDirectory().get(startingLocation).yCoordinate;
		guiToStop = new TransportationGui(this, startX, startY, startStopX, startStopY);
		Directory.sharedInstance().getCityGui().getMacroAnimationPanel().addGui(guiToStop);
		print("adding transport gui to macro");
		actionComplete.acquireUninterruptibly();
		BusHelper.sharedInstance().addWaitingPerson(this, startStopNumber);
		state = TransportationState.WaitingForBus;
		stateChanged();
	}
	private void GetOffBus() {
		print("Getting off bus");
		bus.msgLeavingBus(this);
		state = TransportationState.JustGotOffBus;
		stateChanged();
	}
	private void GetOffVehicle() {
//		if(hasCar) {
//			//remove car gui from main window
//		}
//		else if (!hasCar) {
//			//create transportationrole gui at bus stop
//			//have transportationrole gui walk to destination
//			//remove transportationrole gui
//		}
		
		state = TransportationState.None;
		getPersonAgent().msgTransportFinished(currentLocation); //haven't implemented updating the currentLoc for cars
		//change roles
	}
}
