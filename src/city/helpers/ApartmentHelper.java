package city.helpers;

import city.PersonAgent;

public class ApartmentHelper {
	public static ApartmentHelper sharedInstance;
	PersonAgent agent;
	
	ApartmentHelper() {
		
	}
	
	public static ApartmentHelper sharedInstance() {
		if(sharedInstance == null) {
    		sharedInstance = new ApartmentHelper();
    	}
    	return sharedInstance;
	}
	
	public String getApartmentLetter(String location) {
		String letter = null;
		
		if(location.toLowerCase().contains("a")) {
			letter = "a";
		}
		if(location.toLowerCase().contains("b")) {
			letter = "b";
		}
		if(location.toLowerCase().contains("c")) {
			letter = "c";
		}
		
		return letter;
	}
	
	public int getXMultiplier(String location) {
		int xMult = 0;
		
		if(location.toLowerCase().contains("1")) {
			xMult = 1;
		}		
		
		if(location.toLowerCase().contains("2")) {
			xMult = 2;
		}
		
		if(location.toLowerCase().contains("3")) {
			xMult = 3;
		}

		if(location.toLowerCase().contains("4")) {
			xMult = 4;
		}

		if(location.toLowerCase().contains("5")) {
			xMult = 1;
		}

		if(location.toLowerCase().contains("6")) {
			xMult = 2;
		}

		if(location.toLowerCase().contains("7")) {
			xMult = 3;
		}
		
		if(location.toLowerCase().contains("8")) {
			xMult = 4;
		}

		if(location.toLowerCase().contains("9")) {
			xMult = 1;
		}

		if(location.toLowerCase().contains("10")) {
			xMult = 2;
		}

		if(location.toLowerCase().contains("11")) {
			xMult = 3;
		}

		if(location.toLowerCase().contains("12")) {
			xMult = 1;
		}

		if(location.toLowerCase().contains("13")) {
			xMult = 2;
		}

		if(location.toLowerCase().contains("14")) {
			xMult = 3;
		}
		
		return xMult;
	}
	
	public int getYMultiplier(String location) {
		int yMult = 0;
		
		if(location.toLowerCase().contains("1")) {
			yMult = 1;
		}		
		
		if(location.toLowerCase().contains("2")) {
			yMult = 1;
		}
		
		if(location.toLowerCase().contains("3")) {
			yMult = 1;
		}

		if(location.toLowerCase().contains("4")) {
			yMult = 1;
		}

		if(location.toLowerCase().contains("5")) {
			yMult = 2;
		}

		if(location.toLowerCase().contains("6")) {
			yMult = 2;
		}

		if(location.toLowerCase().contains("7")) {
			yMult = 2;
		}
		
		if(location.toLowerCase().contains("8")) {
			yMult = 2;
		}

		if(location.toLowerCase().contains("9")) {
			yMult = 3;
		}

		if(location.toLowerCase().contains("10")) {
			yMult = 3;
		}

		if(location.toLowerCase().contains("11")) {
			yMult = 3;
		}

		if(location.toLowerCase().contains("12")) {
			yMult = 4;
		}

		if(location.toLowerCase().contains("13")) {
			yMult = 4;
		}

		if(location.toLowerCase().contains("14")) {
			yMult = 4;
		}
		
		return yMult;
	}
	

}
