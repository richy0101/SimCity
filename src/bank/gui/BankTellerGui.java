package bank.gui;

import gui.Gui;

import java.awt.Graphics2D;

import bank.*;

public class BankTellerGui implements Gui {
    
	private BankTellerRole agent = null;
    
    boolean atDestination = false;
    
    public int xPos = 0, yPos = 0;//default teller position
    public int xDestination = 0, yDestination = 0;//default start position
    
    public static final int xTeller1 = 80;
	public static final int xTeller2 = 160;
	public static final int xTeller3 = 240;
	public static final int yTeller = 50;
	
	public BankTellerGui(BankTellerRole agent, int tellerNum) {
        this.agent = agent;
        switch(tellerNum%3){
            case 0:
                xPos = xTeller1;
                yPos = yTeller;
                xDestination = xTeller1;
                yDestination = yTeller;
                break;
            case 1:
                xPos = xTeller2;
                yPos = yTeller;
                xDestination = xTeller1;
                yDestination = yTeller;
                break;
            case 2:
                xPos = xTeller3;
                yPos = yTeller;
                xDestination = xTeller1;
                yDestination = yTeller;
                break;
                
        }
    }
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
        
	}
    
	@Override
	public void draw(Graphics2D g) {
		//set teller to a pokemon image?
        
	}
    
}
