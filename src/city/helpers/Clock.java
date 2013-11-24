package city.helpers;

import agent.Constants;
import city.PersonAgent;

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
	
	private void notifyTimeToWakeUp() {
		for(PersonAgent personAgent : Directory.sharedInstance().getPeople()) {
			if(getTime()%Constants.DAY == 5) {
				System.out.println("Goodmorning. It's 5 AM and it's time to wake up");
				personAgent.msgWakeUp();
			}
		}
	}
}
