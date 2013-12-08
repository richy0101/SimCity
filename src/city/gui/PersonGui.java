package city.gui;

import gui.Gui;
import city.helpers.Clock;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import city.PersonAgent;

public class PersonGui implements Gui {
	
	private PersonAgent agent = null;
	
	private int xPos, yPos;
	private int xDestination, yDestination;
	private int xBed, yBed, xKitchen, yKitchen, xTable, yTable, xDoor, yDoor, xFridge, yFridge;

	BufferedImage personLeft;
	BufferedImage personRight;
	BufferedImage personUp;
	BufferedImage personDown;
	String info;
	
	boolean isPresent = true;
	
	public enum CurrentAction {Cooking, Eating, Transition, Idle, Deciding, Leaving, Sleeping};
	CurrentAction currentAction = CurrentAction.Idle;
	public PersonGui(PersonAgent agent) {
		//private String location = agent.getAddress();
		
		//HOME
		//if(location.toLowerCase().contains("house")) {
			xBed = 5;
			yBed = 135;
			xKitchen = 695;
			yKitchen = 160;
			xFridge = 800;
			yFridge = 130;
			xTable = 425;
			yTable = 250;
			xDoor = 380;
			yDoor = 160;
			xPos = xBed;
			yPos = yBed;
			xDestination = xBed;
			yDestination = yBed;
	//	}
		//APARTMENT
		/*
		else if(location.toLowerCase().contains("apartment")) {
			String [] parts = location.split("Apartment");
			String part2 = parts[1];
			
			System.out.println(housingComboBox.getSelectedItem().toString());
			System.out.println(part2);
			
		}
		*/
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
		
		if(xPos == xKitchen && yPos == yKitchen && currentAction == CurrentAction.Cooking) {
			currentAction = CurrentAction.Transition;
			agent.msgActionComplete();
		}
		if(xPos == xTable && yPos == yTable && currentAction == CurrentAction.Eating) {
			currentAction = CurrentAction.Transition;
			agent.msgActionComplete();
		}
		if(xPos == xFridge && yPos == yFridge && currentAction == CurrentAction.Deciding) {
			currentAction = CurrentAction.Transition;
			agent.msgActionComplete();
		}
		if(xPos == xDoor && yPos == yDoor && currentAction == CurrentAction.Leaving) {
			currentAction = CurrentAction.Transition;
			agent.msgActionComplete();
		}
//		if(xPos == xBed && yPos == yBed && currentAction == CurrentAction.Sleeping) {
//			currentAction = CurrentAction.Transition;
//			agent.msgActionComplete();
//		}
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
		else if (xPos == xBed && yPos == yBed) {
			g.drawImage(personDown, xPos, yPos, null);
		}
		else if (xPos == xTable && yPos == yTable) {
			g.drawImage(personDown, xPos, yPos, null);
		}
		else if (xPos == xKitchen && yPos == yKitchen) {
			g.drawImage(personDown , xPos, yPos, null);
		}
		else {
			g.drawImage(personDown, xPos, yPos, null);
		}
		
		info = agent.getName() + "(" + agent.getPersonState() + ")";
		g.drawString(info, xPos, yPos);
		
	}

	public void setPresentFalse() {
		isPresent = false;
	}
	public void setPresentTrue() {
		isPresent = true;
	}
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return isPresent;
	}
	public void DoDecideEat() {
		currentAction = CurrentAction.Deciding;
		xDestination = xFridge;
		yDestination = yFridge;
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
		currentAction = CurrentAction.Sleeping;
		xDestination = xBed;
		yDestination = yBed;
	}
	public void DoLeaveHouse() {
		currentAction = CurrentAction.Leaving;
		xDestination = xDoor;
		yDestination = yDoor;
	}
	public void DoEnterHouse() {
		xPos = xDoor;
		yPos = yDoor;
		xDestination = xBed;
		yDestination = yBed;
	}
}
