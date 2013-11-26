package bank.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import bank.*;

public class BankCustomerGui implements Gui {
    
	private BankCustomerRole agent = null;
	
	private static final List<Point> tellerBench = new ArrayList<Point>() {{
		add(new Point(87, 51));
		add(new Point(87, 127));
		add(new Point(87, 210));
		add(new Point(728, 51));
		add(new Point(728, 127));
		add(new Point(728, 210));
	}};

	private static final int xManager = 400, yManager = 68;
	private static final int xExit = 400, yExit = 420;
	BufferedImage customerUp;
	BufferedImage customerDown;
	BufferedImage customerLeft;
	BufferedImage customerRight;

	//BankGui gui;

	private int xPos, yPos;
	private int xTeller, yTeller;
	private int xDestination, yDestination;

	private enum Command {noCommand, GoToManager, GoToTeller, LeaveBank};
	private Command command=Command.noCommand;


	public BankCustomerGui(BankCustomerRole customerAgent){
		agent = customerAgent;
		xPos = 450;
		yPos = 450;
		xDestination = 450;
		yDestination = 450;

		try {
			customerLeft = ImageIO.read(getClass().getResource("GUIPersonLeft.png"));
        	customerRight = ImageIO.read(getClass().getResource("GUIPersonRight.png"));
        	customerUp = ImageIO.read(getClass().getResource("GUIPersonUp.png"));
        	customerDown = ImageIO.read(getClass().getResource("GUIPersonDown.png"));
		}
		catch(IOException e) {
			System.out.println("Error w/ Background");
		}
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

		if(xPos == xDestination && yPos == yDestination
				&& (xDestination == xTeller) && (yDestination == yTeller) && command == Command.GoToTeller) {
			agent.msgAtTeller();
			command=Command.noCommand;
		}

		if(xPos == xDestination && yPos == yDestination
				&& (xDestination == xManager) && (yDestination == yManager) && command == Command.GoToManager) {
			agent.msgAtManager();
			command=Command.noCommand;
		}

		if (xPos == xDestination && yPos == yDestination
				&& (xDestination == xExit) && (yDestination == yExit) && command == Command.LeaveBank) {
			agent.msgAnimationFinishedLeavingBank();
			command=Command.noCommand;
		}
	}



	@Override
	public void draw(Graphics2D g) {
		if (xPos < xDestination) {
			g.drawImage(customerRight, xPos, yPos, null);
		}
		else if (xPos > xDestination) {
			g.drawImage(customerLeft,  xPos, yPos, null);
		}
		else if (yPos < yDestination) {
			g.drawImage(customerDown, xPos, yPos, null);
		}
		else if (yPos > yDestination) {
			g.drawImage(customerUp, xPos, yPos, null);
		}
		else {
			g.drawImage(customerDown, xPos, yPos, null);
		}
	}

	@Override
	public boolean isPresent() {
		return true;
	}

	public void DoGoToTeller(int tellerNum) {
		xTeller = (int) tellerBench.get(tellerNum).getX();
		yTeller = (int) tellerBench.get(tellerNum).getY();
		xDestination = (int) tellerBench.get(tellerNum).getX();
		yDestination = (int) tellerBench.get(tellerNum).getY();

		command = Command.GoToTeller;
	}
	
	public void DoGoToManager() {
		xDestination = xManager;
		yDestination = yManager;
		command = Command.GoToManager;
	}

	public void DoLeaveBank() {
		xDestination = xExit;
		yDestination = yExit;
		command = Command.LeaveBank;
	}
	
	//Getters for GUI unit test------------------------------------------------
	public int getxDestination() {
		return xDestination;
	}
	
	public int getyDestination() {
		return yDestination;
	}
	public int getxTeller() {
		return xTeller;
	}
	public int getyTeller() {
		return yTeller;
	}
}
