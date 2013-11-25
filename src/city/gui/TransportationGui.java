package city.gui;

import gui.Gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import city.PersonAgent;
import city.gui.PersonGui.CurrentAction;

public class TransportationGui implements Gui {
	private PersonAgent agent = null;
	private int xPos, yPos;
	private int xDestination, yDestination;
	private int xBed, yBed, xKitchen, yKitchen, xTable, yTable;

	BufferedImage personLeft;
	BufferedImage personRight;
	BufferedImage personUp;
	BufferedImage personDown;

	BufferedImage busLeft;
	BufferedImage busRight;
	BufferedImage busUp;
	BufferedImage busDown;
	
	public enum CurrentAction {Cooking, Eating, Transition, Idle};
	CurrentAction currentAction = CurrentAction.Idle;
	public TransportationGui(int startX, int startY, int destX, int destY) {
		this.agent = agent;
		xPos = startX;
		yPos = startY;
		xDestination = destX;
		yDestination = destY;
		try {
        	personLeft = ImageIO.read(getClass().getResource("GUIPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUIPersonRight.png"));
        	personUp = ImageIO.read(getClass().getResource("GUIPersonUp.png"));
        	personDown = ImageIO.read(getClass().getResource("GUIPersonDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
		
		try {
        	busLeft = ImageIO.read(getClass().getResource("busLeft.png"));
        	busRight = ImageIO.read(getClass().getResource("busRight.png"));
        	busUp = ImageIO.read(getClass().getResource("busUp.png"));
        	busDown = ImageIO.read(getClass().getResource("busDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
		
		this.agent = agent;
	}
	
	@Override
	public void updatePosition() {
		//System.out.println("Updating Pos.");
		if (xPos < xDestination) {
			xPos+= 5;
		}
		else if (xPos > xDestination) {
			xPos-= 5;
		}
		
		if (yPos < yDestination) {
			yPos+= 5;
		}
		else if (yPos > yDestination) {
			yPos-= 5;
		}
		
		if(xPos == xKitchen && yPos == yKitchen && currentAction == CurrentAction.Cooking) {
			currentAction = CurrentAction.Transition;
			agent.msgActionComplete();
		}
		if(xPos == xTable && yPos == yTable && currentAction == CurrentAction.Eating) {
			currentAction = CurrentAction.Transition;
			agent.msgActionComplete();
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
			g.drawImage(personUp, xPos, yPos, null);
		}
		else if (yPos > yDestination) {
			g.drawImage(personDown, xPos, yPos, null);
		}
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}
	public void DoCook() {
		currentAction = CurrentAction.Cooking;
		xDestination = xKitchen;
		yDestination = yKitchen;
	}
	public void DoEat() {
		currentAction = CurrentAction.Eating;
		xDestination = xTable;
		yDestination = yTable;
	}
	public void DoSleep() {
		xDestination = xBed;
		yDestination = yBed;
	}
}