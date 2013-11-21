package city.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import market.Market;
import bank.Bank;
import home.Apartment;
import home.Home;
import restaurant.Restaurant;
import restaurant.stackRestaurant.*;

public class Directory {
	public static Directory sharedInstance;
	
	Directory() {
		restaurants.add(stackRestaurant);
		
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
		roleDirectory.put("home.LandlordRole1", "AppartmentComplex1");
		roleDirectory.put("home.LandlordRole2", "AppartmentComplex2");
		
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
	private Home houseOne = new Home("HouseOne");
	Coordinate houseOneLocation = new Coordinate(350,275);
	
	private Home houseTwo = new Home("HouseTwo");
	Coordinate houseTwoLocation = new Coordinate(478,285);

	private Home houseThree = new Home("HouseThree");
	Coordinate houseThreeLocation = new Coordinate(594,285);
	
	private Home houseFour = new Home("HouseFour");
	Coordinate houseFourLocation = new Coordinate(370,140);
	
	private Home houseFive = new Home("HouseFive");
	Coordinate houseFiveLocation = new Coordinate(502,140);
	
	private Home houseSix = new Home("HouseSix");
	Coordinate houseSixLocation = new Coordinate(602,140);
		
//MARKETS
	private Market marketOne = new Market("MarketOne"); //priority market
	Coordinate marketOneLocation = new Coordinate(523,72);
	
	private Market marketTwo = new Market("MarketTwo"); //secondary market
	Coordinate marketTwoLocation = new Coordinate(397,344);
	
//APARTMENTS
	private Apartment apartmentOne = new Apartment("ApartmentOne"); //smaller limited apartment
	Coordinate apartmentOneLocation = new Coordinate(236,285);
	
	private Apartment apartmentTwo = new Apartment("ApartmentTwo"); //larger infinite apartment
	Coordinate apartmentTwoLocation = new Coordinate(771,259);

//BANKS
	private Bank bank = new Bank("Bank");
	Coordinate bankLocation = new Coordinate(108,151);
	
//LOCATION DIRECTORY
	private static Map<String, Coordinate> locationDirectory = new HashMap<String, Coordinate>(); {
		//Bank
		locationDirectory.put(bank.getName(), bankLocation);
		
		//Markets
		locationDirectory.put(marketOne.getName(), marketOneLocation);
		locationDirectory.put(marketTwo.getName(), marketTwoLocation);
		
		//Apartments
		locationDirectory.put(apartmentOne.getName(), apartmentOneLocation);
		locationDirectory.put(apartmentTwo.getName(), apartmentTwoLocation);
		
		//Homes
		locationDirectory.put(houseOne.getName(), houseOneLocation);
		locationDirectory.put(houseTwo.getName(), houseTwoLocation);
		locationDirectory.put(houseThree.getName(), houseThreeLocation);
		locationDirectory.put(houseFour.getName(), houseFourLocation);
		locationDirectory.put(houseFive.getName(), houseFiveLocation);
		locationDirectory.put(houseSix.getName(), houseSixLocation);
		
		//Restaurants
		locationDirectory.put(stackRestaurant.getName(), stackRestaurantLocation);
		locationDirectory.put(shehRestaurant.getName(), shehRestaurantLocation);
		locationDirectory.put(tanRestaurant.getName(), tanRestaurantLocation);
		locationDirectory.put(phillipsRestaurant.getName(), phillipsRestaurantLocation);
		locationDirectory.put(nakamuraRestaurant.getName(), nakamuraRestaurantLocation);
		locationDirectory.put(huangRestaurant.getName(), huangRestaurantLocation);
		
	}
	
	public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public Map<String, Coordinate> getDirectory() {
		return locationDirectory;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	

}
