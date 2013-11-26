package city.helpers;

import java.util.Timer;
import java.util.TimerTask;

import agent.Constants;
import city.PersonAgent;

public class Clock {
	private static long startTime;
	public static final Clock sharedInstance = new Clock();
	
	private Clock() {
		startTime = System.currentTimeMillis();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				notifyTimeToWakeUp();
			}
		},
		6000);
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
