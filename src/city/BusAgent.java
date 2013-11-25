package city;

import java.util.concurrent.Semaphore;

import agent.Agent;
import city.interfaces.Bus;
import agent.Agent;

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
			if (currentState == busState.atStop1){
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

		private void goTo(String myDestination){
			doGoTo(myDestination); //sets destination in carGui
			try {
				driving.acquire(); //to ensure that the gui is uninterrupted on the way
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
		private void doGoTo(String myDestination){
			System.out.println("Bus is going to "+ myDestination);
			//haven't implemented carGui
			//carGui.msgGoTo(myDestination);
		}




}
