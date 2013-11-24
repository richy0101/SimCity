package bank.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;

import bank.*;

public class BankCustomerGui implements Gui {
    
	private BankCustomerRole agent = null;
	private boolean isPresent = false;
	
	private static final int xTeller1 = 80;
	private static final int xTeller2 = 160;
	private static final int xTeller3 = 240;
	private static final int yTeller = 50;
	private static final int xManager = 50, yManager = 200;
	private static final int xExit = 100, yExit = 100;
	
	//BankGui gui;
    
	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToManager, GoToTeller, LeaveBank};
	private Command command=Command.noCommand;
    
	
	public BankCustomerGui(BankCustomerRole customerAgent){
		agent = customerAgent;
		xPos = 0;
		yPos = 0;
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
           && (xDestination == xTeller1) && (yDestination == yTeller) && command == Command.GoToTeller) {
            agent.msgAtTeller();
        }
		if(xPos == xDestination && yPos == yDestination
        		&& (xDestination == xTeller2) && (yDestination == yTeller) && command == Command.GoToTeller) {
            agent.msgAtTeller();
        }
		if(xPos == xDestination && yPos == yDestination
        		&& (xDestination == xTeller3) && (yDestination == yTeller) && command == Command.GoToTeller) {
			agent.msgAtTeller();
        }
		if (xPos == xDestination && yPos == yDestination
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
		//g.setColor(Color.GREEN);
		//g.fillRect(xPos, yPos, 20, 20);
		//set customer to a pokemon image?
	}
    
	@Override
	public boolean isPresent() {
		return false;
	}
	
	public void DoGoToTeller(int tellerNum) {
		if (tellerNum==1)
        {
    		xDestination = xTeller1;
    		yDestination = yTeller;
        }
    	if (tellerNum==2)
        {
    		xDestination = xTeller2;
    		yDestination = yTeller;
        }
    	if (tellerNum==3)
        {
    		xDestination = xTeller3;
    		yDestination = yTeller;
        }
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
	}
    
}
