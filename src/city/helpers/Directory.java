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
import restaurant.huangRestaurant.HuangRestaurant;
import restaurant.shehRestaurant.ShehRestaurant;
import restaurant.stackRestaurant.*;
import restaurant.tanRestaurant.TanRestaurant;

public class Directory {
	public static Directory sharedInstance;
	
	private SimCityGui cityGui;
	
	Directory() {
		restaurants.add(stackRestaurant);
		restaurants.add(huangRestaurant);
		restaurants.add(tanRestaurant); //should be added last to be 5th on the list
		banks.add(bank);
		banks.add(bank2);
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
		
//		roleDirectory.put("bank.BankCustomerRole2", "Bank2");
//		roleDirectory.put("bank.BankManagerRole2", "Bank2");
//		roleDirectory.put("bank.BankTellerRole2", "Bank2");
		
		//Market Roles
		roleDirectory.put("market.MarketRole" , "Market1");
		roleDirectory.put("market.MarketRole2", "Market2");
		
		//Restaurant Roles Stack
//		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "HuangRestaurant");
//		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterSharedRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterNormalRole", "StackRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "StackRestaurant");
		
		//Restaurant Roles Huang
		roleDirectory.put("restaurant.huangRestaurant.HuangWaiterSharedRole", "HuangRestaurant");
		roleDirectory.put("restaurant.huangRestaurant.HuangHostRole", "HuangRestaurant");
		roleDirectory.put("restaurant.huangRestaurant.HuangWaiterNormalRole", "HuangRestaurant");
		roleDirectory.put("restaurant.huangRestaurant.HuangCookRole", "HuangRestaurant");	
		/*
		//Restaurant Roles Tan
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "HuangRestaurant");	
		
		//Restaurant Roles Sheh
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "HuangRestaurant");	
		
		//Restaurant Roles Nakamura
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "HuangRestaurant");	
		

		
		//Restaurant Roles Phillips
		roleDirectory.put("restaurant.stackRestaurant.StackCashierRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackHostRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackWaiterRole", "HuangRestaurant");
		roleDirectory.put("restaurant.stackRestaurant.StackCookRole", "HuangRestaurant");	
		*/
		
	}
//RESTAURANT Instantiations
	private Restaurant stackRestaurant = new StackRestaurant("StackRestaurant"); //restaurant 1
	Coordinate stackRestaurantLocation = new Coordinate(105,275);
	
	private Restaurant huangRestaurant = new HuangRestaurant("HuangRestaurant"); //restaurant 2
	Coordinate huangRestaurantLocation = new Coordinate(227,76);
	/*

	
	private Restaurant nakamuraRestaurant = new NakamuraRestaurant("NakamuraRestaurant"); //restaurant 3
	Coordinate nakamuraRestaurantLocation = new Coordinate(334,64);
	
	private Restaurant phillipsRestaurant = new PhillipsRestaurant("PhillipsRestaurant"); //restaurant 4
	Coordinate phillipsRestaurantLocation = new Coordinate(768,346);
	*/
	private Restaurant shehRestaurant = new ShehRestaurant("ShehRestaurant"); //restaurant 5
	Coordinate shehRestaurantLocation = new Coordinate(621,331);
	
	
	private Restaurant tanRestaurant = new TanRestaurant("TanRestaurant"); //restaurant 6
	Coordinate tanRestaurantLocation = new Coordinate(380,361);
	
	
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
	private Apartment apartmentA = new Apartment("ApartmentA"); //smaller limited apartment
	Coordinate apartmentALocation = new Coordinate(211,281);
	
	private Apartment apartmentB = new Apartment("ApartmentB"); //larger infinite apartment
	Coordinate apartmentBLocation = new Coordinate(668,66);
	
	private Apartment apartmentC = new Apartment("ApartmentC"); //larger infinite apartment
	Coordinate apartmentCLocation = new Coordinate(730,197);

//BANKS
	private Bank bank = new Bank("Bank");
	Coordinate bankLocation = new Coordinate(89,160);
	
	private Bank bank2 = new Bank("Bank2");
	Coordinate bankLocation2 = new Coordinate(789,75);
//LOCATION DIRECTORY
	public Map<String, Coordinate> locationDirectory = new HashMap<String, Coordinate>(); {
		//Bank
		locationDirectory.put(bank.getName(), bankLocation);
		locationDirectory.put(bank2.getName(), bankLocation2);
		
		//Markets
		locationDirectory.put(market1.getName(), market1Location);
		locationDirectory.put(market2.getName(), market2Location);
		
		//Apartments
		locationDirectory.put(apartmentA.getName(), apartmentALocation);
		locationDirectory.put(apartmentB.getName(), apartmentBLocation);
		locationDirectory.put(apartmentC.getName(), apartmentCLocation);
		
		//Homes
		locationDirectory.put(house1.getName(), house1Location);
		locationDirectory.put(house2.getName(), house2Location);
		locationDirectory.put(house3.getName(), house3Location);
		locationDirectory.put(house4.getName(), house4Location);
		locationDirectory.put(house5.getName(), house5Location);
		locationDirectory.put(house6.getName(), house6Location);
		
		//Restaurants
		locationDirectory.put(stackRestaurant.getName(), stackRestaurantLocation);
		
		locationDirectory.put(shehRestaurant.getName(), shehRestaurantLocation);
		
		locationDirectory.put(huangRestaurant.getName(), huangRestaurantLocation);
		/*
		locationDirectory.put(tanRestaurant.getName(), tanRestaurantLocation);
		locationDirectory.put(phillipsRestaurant.getName(), phillipsRestaurantLocation);
		locationDirectory.put(nakamuraRestaurant.getName(), nakamuraRestaurantLocation);
		*/
		
	}
	
	public Map <String, Agent> agents =  new HashMap<String, Agent>();{
		agents.put(stackRestaurant.getName() + "Host", (Agent) stackRestaurant.getHost());
		agents.put(stackRestaurant.getName() + "Cashier", (Agent) stackRestaurant.getCashier());
		agents.put(huangRestaurant.getName() + "Host", (Agent) huangRestaurant.getHost());
		agents.put(huangRestaurant.getName() + "Cashier", (Agent) huangRestaurant.getCashier());
		agents.put( bank.getName(),(Agent) bank.getManager());
		agents.put( bank2.getName(),(Agent) bank2.getManager());
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
