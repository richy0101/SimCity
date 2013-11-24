package restaurant.stackRestaurant.gui;

import restaurant.stackRestaurant.StackCustomerRole;
import restaurant.stackRestaurant.helpers.TableList;
import gui.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CustomerGui implements Gui{

	private StackCustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;
	private TableList tableList = new TableList();
	private String choice = "";
	BufferedImage customerImage;


	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToWaitingArea, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;

	
	private static final int PERSONSIZEX = 32, PERSONSIZEY = 40;
	private static final int CASHIERX = 460;
	private static final int CASHIERY = 34;
	private static final int WAITINGX = 3;
	private static final int WAITINGY = 3;

	public CustomerGui(StackCustomerRole c) { 
		agent = c;
		xPos = 850;
		yPos = 450;
		xDestination = 850;
		yDestination = 450;
		
		try {
        	customerImage = ImageIO.read(getClass().getResource("stackRestaurantCustomer.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
		
		StackRestaurantAnimationPanel.sharedInstance().addGui(this);
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
				isHungry = false;
//				gui.setCustomerEnabled(agent);
			}
			else if (command==Command.GoToCashier) {
				agent.msgAnimationFinishedGoToCashier();
			}
			else if (command == Command.GoToWaitingArea) {
				agent.msgAnimationFinishedEnteringRestaurant();
			}
			command=Command.noCommand;
		}
	}

	public void updateGui(String choice) {
		this.choice = choice;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(customerImage, xPos, yPos, null);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		agent.msgGotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void DoGoToSeat(int seatnumber, int tableNumber) {//later you will map seatnumber to table coordinates.
		xDestination = (int)tableList.getTables().get(tableNumber-1).getX(); 
		yDestination = (int)tableList.getTables().get(tableNumber-1).getY();
		command = Command.GoToSeat;
	}
	
	public void DoEnterRestaurant() {
		xDestination = WAITINGX;
		yDestination = WAITINGY;
		command = Command.GoToWaitingArea;
	}

	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
	
	public void DoGoToCashier() {
		xDestination = CASHIERX;
		yDestination = CASHIERY;
		command = Command.GoToCashier;
	}
}
