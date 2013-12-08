package city;

import city.interfaces.Car;
import city.interfaces.Vehicle;

import city.interfaces.Person;
import city.interfaces.Transportation;
import agent.Agent;
import city.gui.CarGui;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CarAgent extends Agent implements Vehicle {
	
    /**
	*Data
	*/
	private Transportation passenger;
	CarGui carGui;
	
	public enum carState
	{inTransit, atDestination, Idle}
	public carState currentState = carState.Idle;
	
	private String destination;
	private String currentLocation;
	private Semaphore driving = new Semaphore(0,true);
	
	CarAgent() {
		
	}
		
	/**
	*Scheduler
	*/	
		protected boolean pickAndExecuteAnAction(){
			if (currentState == carState.inTransit){
				print("STUBPICKANDEXECUTEANACTION");
				goTo(destination);
				return true;
			}
		
			else if (currentState == carState.atDestination){
				parkCar();
				return true;
			}

			else if (currentState == carState.Idle){
				return true;//do nothing
			}
			return false;
		}
		
	/**
	 * Messages
	 * @param myDestination
	 */
		public void msgTakeMeHere(String myDestination){ //receives msg from passenger
			destination = myDestination;
			print("Car Destination: " + myDestination + ".");
			
			currentState = carState.inTransit;
			stateChanged();
		}
		
		public void msgAtDestination(){ //from carGui when reached destination
			print("Car arrived to destination.");
			driving.release();
			currentLocation= destination;
			currentState = carState.atDestination;
			stateChanged();
		}

	/**
	 * Actions	
	 * @param myDestination
	 */
		private void goTo(String myDestination){
			print("Starting car.");
			doGoTo(myDestination); //sets destination in carGui
			try {
				driving.acquire(); //to ensure that the gui is uninterrupted on the way
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
		private void doGoTo(String myDestination){
			System.out.println("Car is going to "+ myDestination);
			carGui.DoGoTo(myDestination);
		}
		
		private void parkCar(){
		//msg gives passenger the destination so its gui can reappear at an appropriate place
			print("Car is parking.");
			passenger.msgArrivedAtDestination(destination);
			carGui.DoParkCar();
			currentState = carState.Idle;
		}
		
		/**
		 * Utilities
		 */
		public void setGui(CarGui gui){
			carGui = gui;
		}


}
