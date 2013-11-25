package city.gui;

import gui.Gui;
import city.helpers.Clock;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import city.PersonAgent;

public class PersonGui implements Gui {
	
	private PersonAgent agent = null;
	private int xPos, yPos;
	private int xDestination, yDestination;
	private int xBed, yBed, xKitchen, yKitchen, xTable, yTable;

	BufferedImage personLeft;
	BufferedImage personRight;
	BufferedImage personUp;
	BufferedImage personDown;
	public PersonGui(PersonAgent agent) {
		xBed = 35;
		yBed = 165;
		xKitchen = 695;
		yKitchen = 160;
		xTable = 425;
		yTable = 300;
		xPos = xBed;
		yPos = yBed;
		xDestination = xBed;
		yDestination = yBed;
		try {
        	personLeft = ImageIO.read(getClass().getResource("GUIPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUIPersonRight.png"));
        	personUp = ImageIO.read(getClass().getResource("GUIPersonUp.png"));
        	personDown = ImageIO.read(getClass().getResource("GUIPersonDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
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
	}

	@Override
	public void draw(Graphics2D g) {
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
		else if (xPos == xBed && yPos == yBed) {
			g.drawImage(personUp, xPos, yPos, null);
		}
		else if (xPos == xTable && yPos == yTable) {
			g.drawImage(personDown, xPos, yPos, null);
		}
		else if (xPos == xKitchen && yPos == yKitchen) {
			g.drawImage(personUp, xPos, yPos, null);
		}
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	public void DoCook() {
		xDestination = xKitchen;
		yDestination = yKitchen;
	}
	public void DoEat() {
		xDestination = xTable;
		yDestination = yTable;
	}
	public void DoSleep() {
		xDestination = xBed;
		yDestination = yBed;
	}
}
