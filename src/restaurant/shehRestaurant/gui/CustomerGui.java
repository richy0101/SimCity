package restaurant.shehRestaurant.gui;

import restaurant.shehRestaurant.ShehCustomerRole;
import restaurant.shehRestaurant.helpers.Table;
import gui.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CustomerGui implements Gui{

	private ShehCustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;
	
	BufferedImage customerImage;

	//private HostAgent host;
	Table table; //from CustomerAgent

	private int xPos, yPos, agentSize;
	private int xDestination, yDestination;
	private int XTABLE1 = 422;
	private int YTABLE1 = 126;
	private int XTABLE2 = 332;
	private int YTABLE2 = 274;
	private int XTABLE3 = 513;
	private int YTABLE3 = 279;
	
	private int XDOOR = 707;
	private int YDOOR = 53;
	
	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	private Command command=Command.noCommand;

	//public static final int xTable = 200;
	public int xTable;
	public static final int yTable = 250;

	public CustomerGui(ShehCustomerRole c){ //HostAgent m) {
		agent = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
		
		agentSize = 20;
		
		try {
        	customerImage = ImageIO.read(getClass().getResource("shehRestaurantCustomer.png"));
        	/*
        	chickenImage = ImageIO.read(getClass().getResource("chicken.png"));
            pizzaImage = ImageIO.read(getClass().getResource("pizza.png"));
            saladImage = ImageIO.read(getClass().getResource("salad.png"));
            steakImage = ImageIO.read(getClass().getResource("steak.png"));
            */ 
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
	}

	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				//System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				//gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
	}
	

	public void draw(Graphics2D g) {
		g.drawImage(customerImage, xPos, yPos, null);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		setPresent(true);;
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}
	
	public void DoWaitInRestaurant(int queue) {
		xDestination = 10 + (queue * 40);
		yDestination = 10;
		
	}

	public void DoGoToSeat(int seatnumber, Table table) {//later you will map seat number to table coordinates.
		if(table.getTableNumber() == 1) {
			xDestination = XTABLE1;
			yDestination = YTABLE1;
		}
		if(table.getTableNumber() == 2) {
			xDestination = XTABLE2;
			yDestination = YTABLE2;
		}
		if(table.getTableNumber() == 3) {
			xDestination = XTABLE3;
			yDestination = YTABLE3;
		}

		command = Command.GoToSeat;
	}

	public void DoExitRestaurant() {
		xDestination = XDOOR;
		yDestination = YDOOR;
		command = Command.LeaveRestaurant;
	}
}