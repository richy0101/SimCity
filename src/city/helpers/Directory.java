package city.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import city.PersonAgent;
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

public class Directory {
	public static Directory sharedInstance;
	
	private SimCityGui cityGui;
	
	Directory() {
		restaurants.add(stackRestaurant);
		banks.add(bank);
		markets.add(market1);
		markets.add(market2);
	}
	
	public static Directory sharedInstance() {
		if(sharedInstance == null) {
    		sharedInstance = new Directory();
    	}
    	return sharedInstance;
	}
//ROLES
	public Map<String, String> roleDirectory = new HashMap<String, String>(); {
		//Bank Roles
		roleDirectory.put("bank.BankCustomerRole", "Bank");
		roleDirectory.put("bank.BankManagerRole", "Bank");
		roleDirectory.put("bank.BankTellerRole", "Bank");
		
		//Market Roles
		roleDirectory.put("market.MarketRole1" , "Market1");
		roleDirectory.put("market.MarketRole2", "Market2");
		
		//LandLord Roles
		roleDirectory.put("home.LandlordRole1", "Appartment1");
		roleDirectory.put("home.LandlordRole2", "Appartment2");
		
		//Restaurant Roles Stack
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");
		
		/*
		//Restaurant Roles Tan
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");	
		
		//Restaurant Roles Sheh
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");	
		
		//Restaurant Roles Nakamura
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");	
		
		//Restaurant Roles Huang
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");	
		
		//Restaurant Roles Phillips
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");	
		*/
		
	}
//RESTAURANT Instantiations
	private Restaurant stackRestaurant = new StackRestaurant("StackRestaurant"); //restaurant 1
	Coordinate stackRestaurantLocation = new Coordinate(105,275);
	
	/*
	private Restaurant huangRestaurant = new HuangRestaurant("HuangRestaurant"); //restaurant 2
	Coordinate huangRestaurantLocation = new Coordinate(255,78);
	
	private Restaurant nakamuraRestaurant = new NakamuraRestaurant("NakamuraRestaurant"); //restaurant 3
	Coordinate nakamuraRestaurantLocation = new Coordinate(383,69);
	
	private Restaurant phillipsRestaurant = new PhillipsRestaurant("PhillipsRestaurant"); //restaurant 4
	Coordinate phillipsRestaurantLocation = new Coordinate(779,340);
	
	private Restaurant shehRestaurant = new ShehRestaurant("ShehRestaurant"); //restaurant 5
	Coordinate shehRestaurantLocation = new Coordinate(576,310);
	
	private Restaurant tanRestaurant = new TanRestaurant("TanRestaurant"); //restaurant 6
	Coordinate tanRestaurantLocation = new Coordinate(258,307);
	*/
	
//HOUSES
	private Home house1 = new Home("House1");
	Coordinate house1Location = new Coordinate(350,275);
	
	private Home house2 = new Home("House2");
	Coordinate house2Location = new Coordinate(478,285);

	private Home house3 = new Home("House3");
	Coordinate house3Location = new Coordinate(594,285);
	
	private Home house4 = new Home("House4");
	Coordinate house4Location = new Coordinate(370,140);
	
	private Home house5 = new Home("House5");
	Coordinate house5Location = new Coordinate(502,140);
	
	private Home house6 = new Home("House6");
	Coordinate house6Location = new Coordinate(602,140);

//BUS STOPS
	private BusStop busStop1 = new BusStop("BusStop1"); //bottom left
	Coordinate busStop1Location = new Coordinate(171,361);
	
	private BusStop busStop2 = new BusStop("BusStop2"); //top left
	Coordinate busStop2Location = new Coordinate(110,73);
	
	private BusStop busStop3 = new BusStop("BusStop3"); //top right
	Coordinate busStop3Location = new Coordinate(610,73);
	
	private BusStop busStop4 = new BusStop("BusStop4"); //bottom right
	Coordinate busStop4Location = new Coordinate(675,356);
	
//STREETCORNERS
	
	private StreetCorner bottomLeft = new StreetCorner("Bottom-Left");
	Coordinate streetCornerBottomLeftLocation = new Coordinate(135, 325);
	
	private StreetCorner bottomRight = new StreetCorner("Bottom-Right");
	Coordinate streetCornerBottomRightLocation = new Coordinate(700, 325);
	
	private StreetCorner topLeft = new StreetCorner("Top-Left");
	Coordinate streetCornerTopLeftLocation = new Coordinate(135, 105);
	
	private StreetCorner topRight = new StreetCorner("Top-Right");
	Coordinate streetCornerTopRightLocation = new Coordinate(700, 105);

	
		
//MARKETS
	private Market market1 = new Market("Market1"); //priority market
	Coordinate market1Location = new Coordinate(523,72);
	
	private Market market2 = new Market("Market2"); //secondary market
	Coordinate market2Location = new Coordinate(397,344);
	
//APARTMENTS
	private Apartment apartment1 = new Apartment("Apartment1"); //smaller limited apartment
	Coordinate apartment1Location = new Coordinate(236,285);
	
	private Apartment apartment2 = new Apartment("Apartment2"); //larger infinite apartment
	Coordinate apartment2Location = new Coordinate(771,259);

//BANKS
	private Bank bank = new Bank("Bank");
	Coordinate bankLocation = new Coordinate(108,151);
	
//LOCATION DIRECTORY
	public Map<String, Coordinate> locationDirectory = new HashMap<String, Coordinate>(); {
		//Bank
		locationDirectory.put(bank.getName(), bankLocation);
		
		//Markets
		locationDirectory.put(market1.getName(), market1Location);
		locationDirectory.put(market2.getName(), market2Location);
		
		//Apartments
		locationDirectory.put(apartment1.getName(), apartment1Location);
		locationDirectory.put(apartment2.getName(), apartment2Location);
		
		//Homes
		locationDirectory.put(house1.getName(), house1Location);
		locationDirectory.put(house2.getName(), house2Location);
		locationDirectory.put(house3.getName(), house3Location);
		locationDirectory.put(house4.getName(), house4Location);
		locationDirectory.put(house5.getName(), house5Location);
		locationDirectory.put(house6.getName(), house6Location);
		
		//Restaurants
		locationDirectory.put(stackRestaurant.getName(), stackRestaurantLocation);
		/*
		locationDirectory.put(shehRestaurant.getName(), shehRestaurantLocation);
		locationDirectory.put(tanRestaurant.getName(), tanRestaurantLocation);
		locationDirectory.put(phillipsRestaurant.getName(), phillipsRestaurantLocation);
		locationDirectory.put(nakamuraRestaurant.getName(), nakamuraRestaurantLocation);
		locationDirectory.put(huangRestaurant.getName(), huangRestaurantLocation);*/
		
	}
	
	public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
	public static List<Bank> banks = new ArrayList<Bank>();
	public static List<Market> markets = new ArrayList<Market>();
	public static List<PersonAgent> people = new ArrayList<PersonAgent>();
	public Map<String, Coordinate> getDirectory() {
		return locationDirectory;
	}
	public void addPerson(PersonAgent p) {
		people.add(p);
	}
	public List<PersonAgent> getPeople() {
		return people;
	}
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	public List<Bank> getBanks() {
		return banks;
	}
	public List<Market> getMarkets() {
		return markets;
	}
	public SimCityGui getCityGui() {
		return cityGui;
	}
	public void setCityGui(SimCityGui gui) {
		this.cityGui = gui;
	}
}
