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
	
	public enum Station
	{Stop1, Stop2, Stop3, Stop4}
	public Station lastStation= Station.Stop1;
	
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
					passengersOnBoard.add(new MyPassenger(person));	
		}
		
		public void msgLeavingBus(TransportationRole person){
			for(MyPassenger p: passengersOnBoard){
				if (p.passenger==person){
					p.status= Status.Leaving;
					passengersOnBoard.remove(p);
				}			
			}
		}
		
		public void msgAtStopOne(){
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop1;
			stateChanged();
		}
		
		public void msgAtStopTwo(){
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop2;
		}

		public void msgAtStopThree(){
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop3;
		}
		
		public void msgAtStopFour(){
			driving.release();
			event= Event.reachedStop;
			lastStation = Station.Stop4;
		}

	/**
	 * Actions	
	 * @param myDestination
	 */
		private void stopBus(){
			busGui.DoStopDriving();
			event=Event.stopped;
		}
		
		private void alertPassengersToAlightBus(){
			if((lastStation == Station.Stop1) && (state!=State.driving)){
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(1);
				}
			}
			if((lastStation == Station.Stop2) && (state!=State.driving)){
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(2);
				}
			}
			if((lastStation == Station.Stop3) && (state!=State.driving)){
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(3);
				}
			}
			if((lastStation == Station.Stop4) && (state!=State.driving)){
				for(MyPassenger person: passengersOnBoard){
					person.passenger.msgAtStop(4);
				}
			}	
			event=Event.notifiedPassengersToAlightBus;
		}
		
		private void waitForPassengersToAlight(){
			timer.schedule(new TimerTask() {
				public void run() {
					event=Event.passengersAlighted;
				}
			},
			3000);	
		}
		
		private void alertPassengersToBoardBus(){
			if((lastStation == Station.Stop1) && (state!=State.driving)){
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop1()){
					person.msgGetOnBus(this);
				}	
			}
			if((lastStation == Station.Stop2) && (state!=State.driving)){
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop2()){
					person.msgAtStop(2);
				}	
			}
			if((lastStation == Station.Stop3) && (state!=State.driving)){
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop3()){
					person.msgAtStop(3);
				}	
			}
			if((lastStation == Station.Stop4) && (state!=State.driving)){
				for(TransportationRole person: BusHelper.sharedInstance().getWaitingPassengersAtStop4()){
					person.msgAtStop(4);
				}	
			}
			event=Event.notifiedPassengersToBoardBus;
		}
		
		private void waitForPassengersToBoard(){
			timer.schedule(new TimerTask() {
				public void run() {
					event=Event.passengersBoarded;
				}
			},
			3000);
		}
		
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
		
		
		/*
		 * Utilities
		 */
		private void setGui(BusGui gui){
			busGui = gui;
		}




}
