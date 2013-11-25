package bank.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import bank.*;

public class BankTellerGui implements Gui {
    
	private BankTellerRole agent = null;
    
    boolean atDestination = false;
    
    public int xPos = 0, yPos = 0;//default teller position
    public int xDestination = 0, yDestination = 0;//default start position
    
    public static final int xTeller123 = 0;
	public static final int xTeller456 = 780;
	public static final int yTeller14 = 80;
	public static final int yTeller25 = 156;
	public static final int yTeller36 = 239;
	
	
	BufferedImage personLeft;
	BufferedImage personRight;
	
	public BankTellerGui( int tellerNum) {
        //this.agent = agent;
        try {
        	personLeft = ImageIO.read(getClass().getResource("GUIPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUIPersonRight.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
        switch(tellerNum%6){
            case 0:
                xPos = xTeller123;
                yPos = yTeller14;
                xDestination = xTeller123;
                yDestination = yTeller14;
                break;
            case 1:
                xPos = xTeller123;
                yPos = yTeller25;
                xDestination = xTeller123;
                yDestination = yTeller25;
                break;
            case 2:
                xPos = xTeller123;
                yPos = yTeller36;
                xDestination = xTeller123;
                yDestination = yTeller36;
                break;
            case 3:
                xPos = xTeller456;
                yPos = yTeller14;
                xDestination = xTeller456;
                yDestination = yTeller14;
                break;
            case 4:
                xPos = xTeller456;
                yPos = yTeller25;
                xDestination = xTeller456;
                yDestination = yTeller25;
                break;
            case 5:
                xPos = xTeller456;
                yPos = yTeller36;
                xDestination = xTeller456;
                yDestination = yTeller36;
                break;
                
        }
    }
	
	public void setAgent(BankTellerRole agent){
		this.agent = agent;
	}
	
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
        
	}
    
	@Override
	public void draw(Graphics2D g) {
		if(xPos == 0){
			g.drawImage(personRight, xPos, yPos, null);
		}
		else if(xPos == 780){
			g.drawImage(personLeft, xPos, yPos, null);
		}
	}
    
	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}
    
}
