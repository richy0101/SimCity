package city.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import agent.Constants;
import city.PersonAgent;

public class Clock implements ActionListener{
	public static final Clock sharedInstance = new Clock();
	private final int DELAY = 15000;
	int hour;
	int day;
	Timer timer;
	
	private Clock() {
		hour = 1;
		day = 1;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public static Clock sharedInstance() {
		return sharedInstance;
	}
	
	public void setDelay(int delay) {
		timer.setDelay(delay);		
	}
	
	public boolean isDay() {
		return hour > 6 && hour < 18;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		updateTime();
	}
	
	private void updateTime() {
		if(hour == 24) {
			hour = 1;
			
			if(day == 7)
				day = 1;
			else
				day++;
		}
		
		else
			hour++;
		
		
		for(PersonAgent person : Directory.sharedInstance().getPeople()) {
			person.msgCheckTime(hour, day);
		}
	}
}
