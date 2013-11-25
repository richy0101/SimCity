package market.gui;

import gui.GUIMarket;
import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;

import market.MarketCustomerRole;

public class MarketCustomerGui implements Gui {

	private MarketCustomerRole role = null;
	GUIMarket gui;
	
	private boolean isPresent = false;
	private int xPos, yPos;
	private int xDestination, yDestination;
	
	private enum state {NoCommand, Entering, Leaving};
	private state command = state.NoCommand;
	
	public static final int xCounter = 103;
	public static final int yCounter = 346;
	public static final int xStart = 833;
	public static final int yStart = 359;
	
	
	public MarketCustomerGui(MarketCustomerRole mcr) {
		role = mcr;
//		gui = m;
		
		xPos = xStart;
		yPos = yStart;
		xDestination = xStart;
		yDestination = yStart;
		
		isPresent = true;
	}
	
	@Override
	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;
		
		if(xPos == xDestination && yPos == yDestination) {
			if(command == state.Entering || command == state.Leaving)
				role.msgActionComplete();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(xPos, yPos, 20, 20);
	}

	@Override
	public boolean isPresent() {
		return isPresent;
	}

	public void DoEnterMarket() {
		xDestination = xCounter;
		yDestination = yCounter;
		command = state.Entering;
	}
	
	public void DoLeaveMarket() {
		xDestination = xStart;
		yDestination = yStart;
		command = state.Leaving;
	}
}
