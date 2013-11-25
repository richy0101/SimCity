package bank.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import bank.*;

public class BankTellerGui implements Gui {
    
	private BankTellerRole agent = null;
	int tellerNum;
    
    public int xPos = 0, yPos = 0;//default teller position
    public int xDestination = 0, yDestination = 0;//default start position
    
    private static final int xManager = 400, yManager = 68;
	private static final int xExit = 100, yExit = 100;
	BufferedImage customerImage;
	
	private static final List<Point> tellerBench = new ArrayList<Point>() {{
		add(new Point(36, 106));
		add(new Point(36, 182));
		add(new Point(36, 265));
		add(new Point(778, 106));
		add(new Point(778, 182));
		add(new Point(778, 265));
	}};
	
	
	BufferedImage personLeft;
	BufferedImage personRight;
	
	public BankTellerGui(BankTellerRole tellerAgent, int tellerNum) {
		this.tellerNum = tellerNum;
		agent = tellerAgent;
		xPos = 450;
		yPos = 450;
		xDestination = 450;
		yDestination = 450;
        try {
        	personLeft = ImageIO.read(getClass().getResource("team02/src/city/gui/GUIPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("team02/src/city/gui/GUIPersonRight.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
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
		return true;
	}
    
}
