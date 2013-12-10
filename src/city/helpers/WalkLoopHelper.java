package city.helpers;


import java.util.HashMap;
import java.util.Map;

import city.gui.BusStop;
import city.gui.Walkway;
import gui.SimCityGui;


public class WalkLoopHelper {
	public static WalkLoopHelper sharedInstance;
	
	private SimCityGui cityGui;
	
	WalkLoopHelper() {
		
	}
	
	public static WalkLoopHelper sharedInstance() {
		if(sharedInstance == null) {
    		sharedInstance = new WalkLoopHelper();
    	}
    	return sharedInstance;
	}
	

		
	//RESTAURANT Instantiations
		private Walkway stackRestaurant = new Walkway("StackRestaurant"); //restaurant 1
		Coordinate stackRestaurantLocation = new Coordinate(136,320);
		
		private Walkway huangRestaurant = new Walkway("HuangRestaurant"); //restaurant 2
		Coordinate huangRestaurantLocation = new Coordinate(227,110);
		
		/*
		private Walkway nakamuraRestaurant = new Walkway("NakamuraRestaurant"); //restaurant 3
		Coordinate nakamuraRestaurantLocation = new Coordinate(334,93);
		
		private Walkway phillipsRestaurant = new Walkway("PhillipsRestaurant"); //restaurant 4
		Coordinate phillipsRestaurantLocation = new Coordinate(685,320);
		*/
		private Walkway shehRestaurant = new Walkway("ShehRestaurant"); //restaurant 5
		Coordinate shehRestaurantLocation = new Coordinate(621,320);
		
		
		private Walkway tanRestaurant = new Walkway("TanRestaurant"); //restaurant 6
		Coordinate tanRestaurantLocation = new Coordinate(380,320);
		
		
	//HOUSES
		private Walkway house1 = new Walkway("House1");
		Coordinate house1Location = new Coordinate(290,320);
		
		private Walkway house2 = new Walkway("House2");
		Coordinate house2Location = new Coordinate(367,110);

		private Walkway house3 = new Walkway("House3");
		Coordinate house3Location = new Coordinate(449,110);
		
		private Walkway house4 = new Walkway("House4");
		Coordinate house4Location = new Coordinate(597,110);
		
		private Walkway house5 = new Walkway("House5");
		Coordinate house5Location = new Coordinate(480,320);//480,283
		
		private Walkway house6 = new Walkway("House6");
		Coordinate house6Location = new Coordinate(290,320);	
		
		
	//MARKETS
		private Walkway market1 = new Walkway("Market1"); //priority market
		Coordinate market1Location = new Coordinate(494,110); //done changing
		
		private Walkway market2 = new Walkway("Market2"); //secondary market
		Coordinate market2Location = new Coordinate(488,320);
		
		public Map<String, Walkway> marketWalkwayDirectory = new HashMap<String, Walkway>(); {
			marketWalkwayDirectory.put("Market1", market1);
			marketWalkwayDirectory.put("Market2", market2);
		}
		
		
	//APARTMENTS
		private Walkway apartmentA = new Walkway("ApartmentA"); //smaller limited apartment
		Coordinate apartmentALocation = new Coordinate(211,320);
		
		private Walkway apartmentB = new Walkway("ApartmentB"); //larger infinite apartment
		Coordinate apartmentBLocation = new Coordinate(668,110);
		
		private Walkway apartmentC = new Walkway("ApartmentC"); //larger infinite apartment
		Coordinate apartmentCLocation = new Coordinate(685,730);

	//BANKS
		private Walkway bank = new Walkway("Bank");
		Coordinate bankLocation = new Coordinate(136,160); //d
		
		private Walkway bank2 = new Walkway("Bank2");
		Coordinate bankLocation2 = new Coordinate(685,122);
	
		private Walkway busStop1 = new Walkway("BusStop1"); //bottom left
		Coordinate busStop1Location = new Coordinate(171,361);
		
		private Walkway busStop2 = new Walkway("BusStop2"); //bottom right
		Coordinate busStop2Location = new Coordinate(675,356);
		
		private Walkway busStop3 = new Walkway("BusStop3"); //top right
		Coordinate busStop3Location = new Coordinate(729, 102);//370);
		
		private Walkway busStop4 = new Walkway("BusStop4"); //top left
		Coordinate busStop4Location = new Coordinate(118,77);//(737,107);
		
		
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
			
			locationDirectory.put(tanRestaurant.getName(), tanRestaurantLocation);
			
			/*
			locationDirectory.put(phillipsRestaurant.getName(), phillipsRestaurantLocation);
			locationDirectory.put(nakamuraRestaurant.getName(), nakamuraRestaurantLocation);
			*/
			
		}
		
}
