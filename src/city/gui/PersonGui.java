package city.gui;

import gui.Gui;
import city.helpers.Clock;
import java.awt.Graphics2D;
import city.PersonAgent;

public class PersonGui implements Gui {
	
	private PersonAgent agent = null;	
	
	void DoSleeping() {
		if(Clock.sharedInstance().getTime() == 0) { //CHANGE TIME TO APPROPRIATE WAKE UP TIME
			agent.msgWakeUp();
		}
	}
	
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

}