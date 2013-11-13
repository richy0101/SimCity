package city;

import java.util.concurrent.Semaphore;

import city.interfaces.Bus;

public class BusAgent extends Agent implements Bus {
	
    /**
	*Data
	*/
	private TransportationRole passenger;
	
	public enum busState
	{inTransit, atDestination, Idle}
	public busState currentState = busState.Idle;
	
	private String destination;
	private Semaphore driving = new Semaphore(0,true);
		
	/**
	*Scheduler
	*/	
		protected boolean pickAndExecuteAnAction(){
			if (currentState == busState.inTransit){
				goTo(destination);
				return true;
			}

			return false;
		}
		
	/**
	 * Messages
	 * @param myDestination
	 */
		public void msgTakeMeHere(String myDestination){ //receives msg from passenger
			currentState = busState.inTransit;
			destination= myDestination;
			stateChanged();
		}


	/**
	 * Actions	
	 * @param myDestination
	 */



}
