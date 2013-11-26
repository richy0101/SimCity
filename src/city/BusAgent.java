package city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import agent.Agent;
import city.BusAgent.MyPassenger.Status;
import city.gui.BusGui;
import city.interfaces.Bus;
import city.helpers.Directory;
import city.helpers.BusHelper;


public class BusAgent extends Agent implements Bus {
	
    /**
	*Data
	*/	
	private BusGui busGui= null;
	
	public enum busState
	{inTransit, atStop}
	public busState currentState = busState.atStop;
	//public busState currentState = busState.inTransit;
	
	public enum Station
	{Stop1, Stop2, Stop3, Stop4}
	public Station lastStation = Station.Stop1;
	
	public List<MyPassenger> passengersOnBoard
	= Collections.synchronizedList(new ArrayList<MyPassenger>());
	
	private Semaphore driving = new Semaphore(0,true);
	
	boolean stopRequested= false;
	
	Timer timer = new Timer();
	
	public static class MyPassenger{

		MyPassenger(TransportationRole tr){
			passenger= tr;
			status= Status.none;
		}
		TransportationRole passenger;
		enum Status{Riding, requestingStop, Leaving, none};
		Status status;
		
	}
	
	public BusAgent(){//constructor
		print("Bus in city.");
		busGui = new BusGui(this);
	}
		
	/**
	*Scheduler
	*/	
	
	/*the bus should always be running and not be controlled by states.
	 * It should stop at a station only if there is someone to pickup/dropoff
	 * (non-Javadoc)
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	public enum State
	{driving, stopping, notifyingPassengersToAlightBus, waitForAlighting, notifyingPassengersToBoardBus, waitForBoarding}
	public State state= State.driving;
	
	public enum Event
	{reachedStop, stopped, notifiedPassengersToAlightBus, passengersAlighted, notifiedPassengersToBoardBus, passengersBoarded}
	public Event event= Event.reachedStop;
	
		protected boolean pickAndExecuteAnAction(){
			if(state==State.driving && event==Event.reachedStop){
				state=State.stopping;
				stopBus();//change event to stopped
				return true;
			}
			
			if(state==State.stopping && event==Event.stopped){
				state=State.notifyingPassengersToAlightBus;
				alertPassengersToAlightBus(); //change event to notified passengers
				return true;
			}
			
			if(state==State.notifyingPassengersToAlightBus && event==Event.notifiedPassengersToAlightBus){
				state=State.waitForAlighting;
				waitForPassengersToAlight(); //timer event when done changes to passengersAlighted
				return true;
			}
			
			if(state==State.waitForAlighting && event==Event.passengersAlighted){
				state=State.notifyingPassengersToBoardBus;
				alertPassengersToBoardBus(); //change event to notifiedPassengersToBoardBus 
				return true;
			}
			
			if(state==State.notifyingPassengersToBoardBus && event==Event.notifiedPassengersToBoardBus){
				state=State.waitForBoarding;
				waitForPassengersToBoard(); //timer event when done changes to passengersBoarded
				return true;
			}
			
			if(state==State.waitForBoarding && event==Event.passengersBoarded){
				state=State.driving;
				keepDriving(); //changes event to reachedStop when reaches stop
				return true;
			}
		
			return false;
		}
		

	/**
	 * Messages
	 * @param myDestination
	 */
		public void msgBoardingBus(TransportationRole person){
			print("Received request from passenger to board bus.");	
					passengersOnBoard.add(new MyPassenger(person));	
		}
		
		public void msgLeavingBus(TransportationRole person){
			print("Received request from passenger to leave bus.");
			synchronized(passengersOnBoard) {
				for(MyPassenger p: passengersOnBoard){
					if (p.passenger==person){
						p.status= Status.Leaving;
						passengersOnBoard.remove(p);
					}			
				}
			}
		}
		
		public void msgAtStopOne(){
			print("AT STOP 1");
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop1;
			stateChanged();
		}
		
		public void msgAtStopTwo(){
			print("AT STOP 2");
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop2;
		}

		public void msgAtStopThree(){
			print("AT STOP 3");
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop3;
		}
		
		public void msgAtStopFour(){
			print("AT STOP 4");
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop4;
		}

	/**
	 * Actions	
	 * @param myDestination
	 */
		private void stopBus(){
			print("Bus is stopping.");
			busGui.DoStopDriving();
			event = Event.stopped;
		}
		
		private void alertPassengersToAlightBus(){
			if((lastStation == Station.Stop1) && (state!=State.driving)){
				print("Attention passengers: This is bus stop one.");
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(1);
				}
			}
			if((lastStation == Station.Stop2) && (state!=State.driving)){
				print("Attention passengers: This is bus stop two.");
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(2);
				}
			}
			if((lastStation == Station.Stop3) && (state!=State.driving)){
				print("Attention passengers: This is bus stop three.");
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(3);
				}
			}
			if((lastStation == Station.Stop4) && (state!=State.driving)){
				print("Attention passengers: This is bus stop four");
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(4);
				}
			}	
			event=Event.notifiedPassengersToAlightBus;
		}
		
		private void waitForPassengersToAlight(){
			print("Unloading passengers.");
			
			event = Event.passengersAlighted; //temporarily placed here until timer issue is resolved
			
			//TIMER DOES NOT RUN THIS?
			/*
			timer.schedule(new TimerTask() {
				public void run() {
					event=Event.passengersAlighted;
				}
			},
			1000);
			*/	
		}
		
		private void alertPassengersToBoardBus(){
			print("Passengers boarding bus.");
			if((lastStation == Station.Stop1) && (state!=State.driving)){
				print("Boarding Stop 1");
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop1()){
					person.msgGetOnBus(this);
				}	
			}
			if((lastStation == Station.Stop2) && (state!=State.driving)){
				print("Boarding Stop 3");
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop2()){
					person.msgGetOnBus(this);
				}	
			}
			if((lastStation == Station.Stop3) && (state!=State.driving)){
				print("Boarding Stop 3");
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop3()){
					person.msgGetOnBus(this);
				}	
			}
			if((lastStation == Station.Stop4) && (state!=State.driving)){
				print("Boarding Stop 4");
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop4()){
					person.msgGetOnBus(this);
				}	
			}
			event=Event.notifiedPassengersToBoardBus;
		}
		
		private void waitForPassengersToBoard(){
			print("Loading passengers.");
			
			event = Event.passengersBoarded; //TEMPORARILY PLACED UNTIL RESOLVING TIMER ISSUE
			
			//TIMER ISSUES
			/*
			timer.schedule(new TimerTask() {
				public void run() {
					event=Event.passengersBoarded;
				}
			},
			3000);
			*/
		}
		
		private void keepDriving(){
			//doGoTo(myDestination); //sets destination in carGui
			print("Bus is driving.");
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
		
		
		/*
		 * Utilities
		 */
		public void setGui(BusGui gui){
			busGui = gui;
		}
}
