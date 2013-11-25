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
	{inTransit, atDestination, Idle, atStop}
	public busState currentState = busState.Idle;
	
	public enum Station
	{Stop1, Stop2, Stop3, Stop4}
	public Station lastStation= Station.Stop1;
	
	private String destination;
	private Semaphore driving = new Semaphore(0,true);
		
	/**
	*Scheduler
	*/	
	
	/*the bus should always be running and not be controlled by states.
	 * It should stop at a station only if there is someone to pickup/dropoff
	 * (non-Javadoc)
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
		protected boolean pickAndExecuteAnAction(){
			
			//start btwn stations and moving CCW
			if ((lastStation == Station.Stop1) && (currentState==busState.atStop) && Stop1Passengers.isEmpty()){
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
		
		public void msgAtStopOne(){
			lastStation = Station.Stop1;
		}
		
		public void msgAtStopTwo(){
			lastStation = Station.Stop2;
		}

		public void msgAtStopThree(){
			lastStation = Station.Stop3;
		}
		
		public void msgAtStopFour(){
			lastStation = Station.Stop4;
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
