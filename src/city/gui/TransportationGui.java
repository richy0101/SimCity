package city.gui;

import gui.Gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import city.PersonAgent;
import city.TransportationRole;
import city.gui.PersonGui.CurrentAction;

public class TransportationGui implements Gui {
	private TransportationRole agent = null;
	private int xPos, yPos;
	private int xDestination, yDestination;

	BufferedImage personLeft;
	BufferedImage personRight;
	BufferedImage personUp;
	BufferedImage personDown;
	
	public enum CurrentAction {Travelling, Idle};
	CurrentAction currentAction = CurrentAction.Idle;
	public TransportationGui(TransportationRole agent, int startX, int startY, int destX, int destY) {
		this.agent = agent;
		xPos = startX;
		yPos = startY;
		xDestination = destX;
		yDestination = destY;
		
		try {
        	personLeft = ImageIO.read(getClass().getResource("GUICITYPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUICITYPersonRight.png"));
        	personUp = ImageIO.read(getClass().getResource("GUICITYPersonUp.png"));
        	personDown = ImageIO.read(getClass().getResource("GUICITYPersonDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
		
		currentAction = CurrentAction.Travelling;
	}
	
	@Override
	public void updatePosition() {
		//System.out.println("Updating Pos.");
		if (xPos < xDestination) {
			xPos+= 1;
		}
		else if (xPos > xDestination) {
			xPos-= 1;
		}
		
		if (yPos < yDestination) {
			yPos+= 1;
		}
		else if (yPos > yDestination) {
			yPos-= 1;
		}
		
		if(yPos == yDestination && xPos == xDestination && currentAction == CurrentAction.Travelling) {
			agent.msgActionComplete();
			currentAction = CurrentAction.Idle;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		//System.out.println("Updating Pos.");
		if (xPos < xDestination) {
			g.drawImage(personRight, xPos, yPos, null);
		}
		else if (xPos > xDestination) {
			g.drawImage(personLeft,  xPos, yPos, null);
		}
		else if (yPos < yDestination) {
			g.drawImage(personDown, xPos, yPos, null);
		}
		else if (yPos > yDestination) {
			g.drawImage(personUp, xPos, yPos, null);
		}
		else {
			g.drawImage(personDown, xPos, yPos, null);
		}
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}
}