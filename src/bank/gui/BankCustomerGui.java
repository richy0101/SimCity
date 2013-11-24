package bank.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;

import bank.*;

public class BankCustomerGui implements Gui {
    
	private BankCustomerRole agent = null;
	private boolean isPresent = false;
	boolean atDestination = false;
	
	public static final int xTeller1 = 80;
	public static final int xTeller2 = 160;
	public static final int xTeller3 = 240;
	public static final int yTeller = 50;
	
	//BankGui gui;
    
	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToTeller, LeaveBank};
	private Command command=Command.noCommand;
    
	
	public BankCustomerGui(BankCustomerRole customerAgent, int tellerNum){
		agent = customerAgent;
		xPos = 0;
		yPos = 0;
		switch(tellerNum%3){
            case 0:;
                xDestination = xTeller1;
                yDestination = yTeller;
                break;
            case 1:
                xDestination = xTeller2;
                yDestination = yTeller;
                break;
            case 2:
                xDestination = xTeller3;
                yDestination = yTeller;
                break;
		}
	}
	
	@Override
	public void updatePosition() {
		if (xPos < xDestination)
			xPos = xPos;
		else if (xPos > xDestination)
			xPos = xPos;
        
		if (yPos < yDestination)
			yPos = yPos;
		else if (yPos > yDestination)
			yPos = yPos;
        
		if(atDestination == false && xPos == xDestination && yPos == yDestination
           & (xDestination == 80) & (yDestination == 50)) {
			atDestination = true;
            agent.msgAtTeller();
        }
		else if(atDestination == false && xPos == xDestination && yPos == yDestination
        		& (xDestination == 160) & (yDestination == 50)) {
			atDestination = true;
            agent.msgAtTeller();
        }
		else if(atDestination == false && xPos == xDestination && yPos == yDestination
        		& (xDestination == 240) & (yDestination == 50)) {
			atDestination = true;
			agent.msgAtTeller();
        }
		
		if (atDestination == false && xPos == xDestination && yPos == yDestination &&
			(xDestination == 50) & (yDestination == 200) ) {
			atDestination = true;
			agent.msgAtHost();
		}
		else if (command==Command.LeaveBank) {
            agent.msgAnimationFinishedLeavingBank();
            //gui.setCustomerEnabled(agent);
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
			atDestination = false;
    		xDestination = xTeller1;
    		yDestination = yTeller;
        }
    	if (tellerNum==2)
        {
    		atDestination = false;
    		xDestination = xTeller2;
    		yDestination = yTeller;
        }
    	if (tellerNum==3)
        {
    		atDestination = false;
    		xDestination = xTeller3;
    		yDestination = yTeller;
        }
		command = Command.GoToTeller;
	}
	public void DoGoToHost() {
		atDestination = false;
    	xDestination = 50;
    	yDestination = 200;
	}
    
}
