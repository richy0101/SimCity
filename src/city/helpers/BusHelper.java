package city.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import city.PersonAgent;
import city.TransportationRole;
import city.gui.BusStop;
import city.gui.StreetCorner;
import city.interfaces.Transportation;
import market.Market;
import bank.Bank;
import gui.MacroAnimationPanel;
import gui.SimCityGui;
import home.Apartment;
import home.Home;
import restaurant.Restaurant;
import restaurant.stackRestaurant.*;

public class BusHelper {
	public static BusHelper sharedInstance;
	
	private SimCityGui cityGui;
	
	BusHelper() {
		
	}
	
	public static BusHelper sharedInstance() {
		if(sharedInstance == null) {
    		sharedInstance = new BusHelper();
    	}
    	return sharedInstance;
	}
	
//BUS STOPS
	private BusStop busStop1 = new BusStop("BusStop1"); //bottom left
	Coordinate busStop1Location = new Coordinate(171,361);
	
	private BusStop busStop2 = new BusStop("BusStop2"); //bottom right
	Coordinate busStop2Location = new Coordinate(675,356);
	
	private BusStop busStop3 = new BusStop("BusStop3"); //top right
	Coordinate busStop3Location = new Coordinate(729, 102);//370);
	
	private BusStop busStop4 = new BusStop("BusStop4"); //top left
	Coordinate busStop4Location = new Coordinate(118,77);//(737,107);
	
	public Map<String, Coordinate> busStopEvaluator = new HashMap<String, Coordinate>(); {
		busStopEvaluator.put("Bank", busStop4Location);
		
		busStopEvaluator.put("House1", busStop1Location);
		busStopEvaluator.put("House2", busStop4Location);
		busStopEvaluator.put("House3", busStop3Location);
		busStopEvaluator.put("House4", busStop3Location);
		busStopEvaluator.put("House5", busStop2Location);
		busStopEvaluator.put("House6", busStop2Location);
		
		busStopEvaluator.put("Apartment1", busStop1Location);
		busStopEvaluator.put("Apartment2", busStop2Location);
		
		busStopEvaluator.put("Market1", busStop3Location);
		busStopEvaluator.put("Market2", busStop2Location);
		
		busStopEvaluator.put("StackRestaurant", busStop1Location);
	}
	public Map<String, Integer> busStopToInt = new HashMap<String, Integer>(); {
		busStopToInt.put("Bank", 4);
		
		busStopToInt.put("House1", 1);
		busStopToInt.put("House2", 4);
		busStopToInt.put("House3", 3);
		busStopToInt.put("House4", 3);
		busStopToInt.put("House5", 2);
		busStopToInt.put("House6", 2);
		
		busStopToInt.put("Apartment1", 1);
		busStopToInt.put("Apartment2", 2);
		
		busStopToInt.put("Market1", 3);
		busStopToInt.put("Market2", 2);
		
		busStopToInt.put("StackRestaurant", 1);
		busStopToInt.put("HuangRestaurant", 4);
		
	}
		
//STREETCORNERS
	
	private StreetCorner bottomLeft = new StreetCorner("Bottom-Left");
	Coordinate streetCornerBottomLeftLocation = new Coordinate(135, 325);
	
	private StreetCorner bottomRight = new StreetCorner("Bottom-Right");
	Coordinate streetCornerBottomRightLocation = new Coordinate(700, 325);
	
	private StreetCorner topLeft = new StreetCorner("Top-Left");
	Coordinate streetCornerTopLeftLocation = new Coordinate(135, 105);
	
	private StreetCorner topRight = new StreetCorner("Top-Right");
	Coordinate streetCornerTopRightLocation = new Coordinate(700, 105);

	
		

//LOCATION DIRECTORY
	public Map<String, Coordinate> locationDirectory = new HashMap<String, Coordinate>(); {
		
		
	}
	
	public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
	public static List<Bank> banks = new ArrayList<Bank>();
	public static List<Market> markets = new ArrayList<Market>();
	public static List<PersonAgent> people = new ArrayList<PersonAgent>();
	public static List<Transportation> waitingPassengersAtStop1 = Collections.synchronizedList(new ArrayList<Transportation>());
	public static List<Transportation> waitingPassengersAtStop2 = Collections.synchronizedList(new ArrayList<Transportation>());
	public static List<Transportation> waitingPassengersAtStop3 = Collections.synchronizedList(new ArrayList<Transportation>());
	public static List<Transportation> waitingPassengersAtStop4 = Collections.synchronizedList(new ArrayList<Transportation>());
	
	public Map<String, Coordinate> getDirectory() {
		return locationDirectory;
	}
	public void addWaitingPerson(Transportation t, int stopNumber) {
		//System.out.println("adding waiting person");
		if (stopNumber == 1) {
			waitingPassengersAtStop1.add(t);
		}
		else if (stopNumber == 2) {
			waitingPassengersAtStop2.add(t);
		}
		else if (stopNumber == 3) {
			waitingPassengersAtStop3.add(t);
		}
		else {
			waitingPassengersAtStop4.add(t);
		}
	}
	public void removeWaitingPerson(Transportation t, int stopNumber) {
		if (stopNumber == 1) {
			waitingPassengersAtStop1.remove(t);
		}
		else if (stopNumber == 2) {
			waitingPassengersAtStop2.remove(t);
		}
		else if (stopNumber == 3) {
			waitingPassengersAtStop3.remove(t);
		}
		else {
			waitingPassengersAtStop4.remove(t);
		}
	}
	public List<Transportation> getWaitingPassengersAtStop1(){
		return waitingPassengersAtStop1;
	}
	public List<Transportation> getWaitingPassengersAtStop2(){
		return waitingPassengersAtStop2;
	}
	public List<Transportation> getWaitingPassengersAtStop3(){
		return waitingPassengersAtStop3;
	}
	public List<Transportation> getWaitingPassengersAtStop4(){
		return waitingPassengersAtStop4;
	}
	public SimCityGui getCityGui() {
		return cityGui;
	}
	public void setCityGui(SimCityGui gui) {
		this.cityGui = gui;
	}
}
