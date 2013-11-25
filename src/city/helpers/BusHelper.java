package city.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import city.PersonAgent;
import city.TransportationRole;
import city.gui.BusStop;
import city.gui.StreetCorner;
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
	
	private BusStop busStop4 = new BusStop("BusStop2"); //bottom right
	Coordinate busStop4Location = new Coordinate(675,356);
	
	private BusStop busStop3 = new BusStop("BusStop3"); //top right
	Coordinate busStop3Location = new Coordinate(610,73);
	
	private BusStop busStop2 = new BusStop("BusStop4"); //top left
	Coordinate busStop2Location = new Coordinate(110,73);
	
	public Map<String, Coordinate> busStopEvaluator = new HashMap<String, Coordinate>();
	
	
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
	public static List<TransportationRole> waitingPassengersAtStop1 = new ArrayList<TransportationRole>();
	public static List<TransportationRole> waitingPassengersAtStop2 = new ArrayList<TransportationRole>();
	public static List<TransportationRole> waitingPassengersAtStop3 = new ArrayList<TransportationRole>();
	public static List<TransportationRole> waitingPassengersAtStop4 = new ArrayList<TransportationRole>();
	
	public Map<String, Coordinate> getDirectory() {
		return locationDirectory;
	}
	public List<TransportationRole> getWaitingPassengersAtStop1(){
		return waitingPassengersAtStop1;
	}
	public List<TransportationRole> getWaitingPassengersAtStop2(){
		return waitingPassengersAtStop2;
	}
	public List<TransportationRole> getWaitingPassengersAtStop3(){
		return waitingPassengersAtStop3;
	}
	public List<TransportationRole> getWaitingPassengersAtStop4(){
		return waitingPassengersAtStop4;
	}
	public SimCityGui getCityGui() {
		return cityGui;
	}
	public void setCityGui(SimCityGui gui) {
		this.cityGui = gui;
	}
}
