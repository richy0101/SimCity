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
import agent.Agent;
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
//		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "StackRestaurant");
//		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterSharedRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterNormalRole", "StackRestaurant");
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
	Coordinate huangRestaurantLocation = new Coordinate(227,76);
	
	private Restaurant nakamuraRestaurant = new NakamuraRestaurant("NakamuraRestaurant"); //restaurant 3
	Coordinate nakamuraRestaurantLocation = new Coordinate(334,64);
	
	private Restaurant phillipsRestaurant = new PhillipsRestaurant("PhillipsRestaurant"); //restaurant 4
	Coordinate phillipsRestaurantLocation = new Coordinate(768,346);
	
	private Restaurant shehRestaurant = new ShehRestaurant("ShehRestaurant"); //restaurant 5
	Coordinate shehRestaurantLocation = new Coordinate(621,331);
	
	private Restaurant tanRestaurant = new TanRestaurant("TanRestaurant"); //restaurant 6
	Coordinate tanRestaurantLocation = new Coordinate(380,361);
	*/
	
//HOUSES
	private Home house1 = new Home("House1");
	Coordinate house1Location = new Coordinate(290,272);
	
	private Home house2 = new Home("House2");
	Coordinate house2Location = new Coordinate(367,168);

	private Home house3 = new Home("House3");
	Coordinate house3Location = new Coordinate(449,163);
	
	private Home house4 = new Home("House4");
	Coordinate house4Location = new Coordinate(597,279);
	
	private Home house5 = new Home("House5");
	Coordinate house5Location = new Coordinate(480,283);
	
	private Home house6 = new Home("House6");
	Coordinate house6Location = new Coordinate(290,272);	
	
		
//MARKETS
	private Market market1 = new Market("Market1"); //priority market
	Coordinate market1Location = new Coordinate(494,69);
	
	private Market market2 = new Market("Market2"); //secondary market
	Coordinate market2Location = new Coordinate(488,333);
	
	public Map<String, Market> marketDirectory = new HashMap<String, Market>(); {
		marketDirectory.put("Market1", market1);
		marketDirectory.put("Market2", market2);
	}
	
	
//APARTMENTS
	private Apartment apartment1 = new Apartment("Apartment1"); //smaller limited apartment
	Coordinate apartment1Location = new Coordinate(211,281);
	
	private Apartment apartment2 = new Apartment("Apartment2"); //larger infinite apartment
	Coordinate apartment2Location = new Coordinate(668,66);
	
	private Apartment apartment3 = new Apartment("Apartment3"); //larger infinite apartment
	Coordinate apartment3Location = new Coordinate(730,197);

//BANKS
	private Bank bank = new Bank("Bank");
	Coordinate bankLocation = new Coordinate(89,160);
	
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
		locationDirectory.put(apartment3.getName(), apartment3Location);
		
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
	
	public Map <String, Agent> agents =  new HashMap<String, Agent>();{
		agents.put(stackRestaurant.getName() + "Host", (Agent) stackRestaurant.getHost());
		agents.put(stackRestaurant.getName() + "Cashier", (Agent) stackRestaurant.getCashier());
		agents.put( bank.getName(),(Agent) bank.getManager());
	}
	public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
	public static List<Bank> banks = new ArrayList<Bank>();
	public static List<Market> markets = new ArrayList<Market>();
	public static List<PersonAgent> people = new ArrayList<PersonAgent>();
	public Map<String, Agent> getAgents() {
		return agents;
	}
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
