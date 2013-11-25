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
	private boolean isPresent = false;
	
	private static final List<Point> tellerBench = new ArrayList<Point>() {{
		add(new Point(87, 106));
		add(new Point(87, 182));
		add(new Point(87, 265));
		add(new Point(728, 106));
		add(new Point(728, 182));
		add(new Point(728, 265));
	}};

	private static final int xManager = 50, yManager = 200;
	private static final int xExit = 100, yExit = 100;
	BufferedImage customerImage;
	
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
        	customerImage = ImageIO.read(getClass().getResource("team02/src/restaurant/stackRestaraunt/gui/stackRestaurantCustomer.png"));
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
        }
		
		if(xPos == xDestination && yPos == yDestination
		   && (xDestination == xManager) && (yDestination == yManager) && command == Command.GoToManager) {
		    agent.msgAtManager();
		}
		
		if (xPos == xDestination && yPos == yDestination
				&& (xDestination == xExit) && (yDestination == yExit) && command == Command.LeaveBank) {
            agent.msgAnimationFinishedLeavingBank();
        }
		
        command=Command.noCommand;
	}
    
    
    
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(customerImage, xPos, yPos, null);
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
}
