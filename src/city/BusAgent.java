package city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import agent.Agent;
import city.gui.BusGui;
import city.interfaces.Bus;
import city.helpers.Directory;

public class BusAgent extends Agent implements Bus {
	
    /**
	*Data
	*/
	private TransportationRole passenger;
	
	private BusGui busGui= null;
	
	public enum busState
	{inTransit, atDestination, Idle, atStop}
	public busState currentState = busState.Idle;
	
	public enum Station
	{Stop1, Stop2, Stop3, Stop4}
	public Station lastStation= Station.Stop1;
	
	public List<TransportationRole> passengersOnBoard
	= Collections.synchronizedList(new ArrayList<TransportationRole>());
	
	private String destination;
	private Semaphore driving = new Semaphore(0,true);
	
	//flags for whether there are any passengers for each stop
	boolean Flag1 = false;
	boolean Flag2 = false;
	boolean Flag3 = false;
	boolean Flag4 = false; //flag if there are any passengers who want to alight at stop 4
	
	BusAgent(){//constructor
	}
		
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
			
			if((lastStation == Station.Stop1) && (currentState==busState.atStop)){
				stopBus(); //stop bus no matter what
				boardBusStop1();
				alightBusStop1();
			}
			
			
			if ((lastStation == Station.Stop1) && (currentState==busState.atStop)){
				if(Directory.sharedInstance().getWaitingPassengersAtStop1().isEmpty() && Flag1==false){ //no one waiting & no one alighting
					keepDriving();
					currentState= busState.inTransit;
				}
				else{
					//start sequence to wait for passengers to alight/get on
					stopBus();
					//get passengers on bus might have probs if someone tries to get on waiting list while bus is doing this
					for(TransportationRole person: Directory.sharedInstance().getWaitingPassengersAtStop1()){
						passengersOnBoard.add(person);
						Directory.sharedInstance().getWaitingPassengersAtStop1().remove(person); //remove from waiting
					}
					
					//get passengers off bus
					for(TransportationRole person: passengersOnBoard){
						if (person.stopDestination.equals("stop1")){
							passengersOnBoard.remove(person); //get off bus
							//need to msg something to recreate the transportationRole
						}
					}
					
					Flag1 = false; //all ppl alighted, now need to wait for ppl to get on
					
					while(!Directory.sharedInstance().getWaitingPassengersAtStop1().isEmpty()){
						//wait till there are no more people waiting
					}
					
					keepDriving();
				}			
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
			currentState = busState.atStop;
			stateChanged();
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
		private void boardBusStop1(){
			for(TransportationRole person: Directory.sharedInstance().getWaitingPassengersAtStop1()){
				passengersOnBoard.add(person);
				Directory.sharedInstance().getWaitingPassengersAtStop1().remove(person); //remove from waiting
			}
		}
		
		private void alightBusStop1(){
			for(TransportationRole person: passengersOnBoard){
				person.msgAtStop();
				if (person.stopDestination.equals("stop1")){
					passengersOnBoard.remove(person); //get off bus
					//need to msg something to recreate the transportationRole
				}
			}
		}
		
		private void addPassenger(TransportationRole passenger){
			passengersOnBoard.add(passenger);
			if(passenger.stopDestination.equals("Stop1"))
				Flag1= true;
			else if(passenger.stopDestination.equals("Stop2"))
				Flag2= true;
			else if(passenger.stopDestination.equals("Stop3"))
				Flag3= true;
			else if(passenger.stopDestination.equals("Stop4"))
				Flag4= true;
		}

		/*
		private void goTo(String myDestination){
			doGoTo(myDestination); //sets destination in carGui
			try {
				driving.acquire(); //to ensure that the gui is uninterrupted on the way
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}*/
		
		private void keepDriving(){
			//doGoTo(myDestination); //sets destination in carGui
			doKeepDriving();
			try {
				driving.acquire(); //to ensure that the gui is uninterrupted on the way
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
		private void doKeepDriving(){
			busGui.DoKeepDriving();
		}
		
		private void stopBus(){
			busGui.DoStopDriving();
		}
		
		/*
		private void doGoTo(String myDestination){
			busGui.DoKeepDriving();
			//haven't implemented carGui
			//carGui.msgGoTo(myDestination);
		}*/
		
		/*
		 * Utilities
		 */
		private void setGui(BusGui gui){
			busGui = gui;
		}




}
