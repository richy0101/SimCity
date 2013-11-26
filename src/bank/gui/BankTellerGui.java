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

import bank.BankTellerRole;


public class BankTellerGui implements Gui {
    
	private BankTellerRole agent = null;
	int tellerNum;
    
    public int xPos = 0, yPos = 0;//default teller position
    public int xDestination = 0, yDestination = 0;//default start position
    
    private enum Command {noCommand, GoToManager, GoToRegister, LeaveBank};
	private Command tellerCommand =Command.noCommand;
    
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
	
	public BankTellerGui(BankTellerRole tellerAgent) {
		agent = tellerAgent;
		xPos = 450;
		yPos = 450;
		xDestination = 450;
		yDestination = 450;
        try {
        	personLeft = ImageIO.read(getClass().getResource("GUIPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUIPersonRight.png"));
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
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;
		
		if(xPos == xDestination && yPos == yDestination
			&& tellerCommand == Command.GoToRegister) {
			agent.msgAtRegister();
		}

		if(xPos == xDestination && yPos == yDestination
				&& (xDestination == xManager) && (yDestination == yManager) && tellerCommand == Command.GoToManager) {
			agent.msgAtManager();
		}

		if (xPos == xDestination && yPos == yDestination
				&& (xDestination == xExit) && (yDestination == yExit) && tellerCommand == Command.LeaveBank) {
			agent.msgAnimationFinishedLeavingBank();
		}

		tellerCommand=Command.noCommand;
		
		

	}
    
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(personRight, xPos, yPos, null);
	}
    
	@Override
	public boolean isPresent() {
		return true;
	}
	
	public void DoGoToRegister(int tellerNum) {
		this.tellerNum = tellerNum;
		xDestination = (int) tellerBench.get(tellerNum).getX();
		yDestination = (int) tellerBench.get(tellerNum).getY();

		tellerCommand = Command.GoToRegister;
	}
	
	public void DoGoToManager() {
		xDestination = xManager;
		yDestination = yManager;
		tellerCommand = Command.GoToManager;
	}

	public void DoLeaveBank() {
		xDestination = xExit;
		yDestination = yExit;
		tellerCommand = Command.LeaveBank;
	}
    
}
