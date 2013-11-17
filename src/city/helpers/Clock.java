package city.helpers;

public class Clock {
	private static long startTime;
	public static final Clock sharedInstance = new Clock();
	
	private Clock() {
		startTime = System.currentTimeMillis();
	}
	
	public static Clock sharedInstance() {
		return sharedInstance;
	}
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}

}
