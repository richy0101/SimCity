package market.gui;

import gui.GUIMarket;
import gui.Gui;

import java.awt.Graphics2D;

import market.MarketRole;

public class MarketGui implements Gui {

	private MarketRole role = null;
	GUIMarket gui;
	
	private boolean isPresent = false;
	private int xPos, yPos;
	private int xDestination, yDestination;
	
	private enum state {NoCommand, GettingItem, GoingToCounter};
	private state command = state.NoCommand;
	
	public static final int xCounter = 75;
	public static final int yCounter = 50;
	public static final int xStart = 75;
	public static final int yStart = 50;
	public static final int xShelf = 100;
	public static final int yShelf = 100;
	
	
	public MarketGui(MarketRole mr) {
		role = mr;
//		gui = m;
		
		xPos = xStart;
		yPos = yStart;
		xDestination = xStart;
		yDestination = yStart;
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
			if(command == state.GettingItem || command == state.GoingToCounter) {
				role.msgActionComplete();
				command = state.NoCommand;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {

	}

	@Override
	public boolean isPresent() {
		return isPresent;
	}

	public void DoGetFood() {
		xDestination = xShelf;
		yDestination = yShelf;
		command = state.GettingItem;
	}
	
	public void DoGoToCounter() {
		xDestination = xCounter;
		yDestination = yCounter;
		command = state.GoingToCounter;
	}
	
}
