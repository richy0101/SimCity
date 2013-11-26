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
	private boolean isHungry = false;
	private TableList tableList = new TableList();
	private String choice = "";
	BufferedImage customerImage;
	BufferedImage chickenImage;
	BufferedImage pizzaImage;
	BufferedImage saladImage;
	BufferedImage steakImage;


	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToWaitingArea, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;

	
	private static final int PERSONSIZEX = 32, PERSONSIZEY = 40;
	private static final int CASHIERX = 460;
	private static final int CASHIERY = 34;
	private int WAITINGX = 725, WAITINGY = 333;

	public CustomerGui(StackCustomerRole c) { 
		agent = c;
		xPos = 850;
		yPos = 450;
		xDestination = 850;
		yDestination = 450;
		
		try {
        	customerImage = ImageIO.read(getClass().getResource("stackRestaurantCustomer.png"));
        	chickenImage = ImageIO.read(getClass().getResource("chicken.png"));
            pizzaImage = ImageIO.read(getClass().getResource("pizza.png"));
            saladImage = ImageIO.read(getClass().getResource("salad.png"));
            steakImage = ImageIO.read(getClass().getResource("steak.png"));
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
				isHungry = false;
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
		if(choice == "") {
			g.drawImage(customerImage, xPos, yPos, null);
		}
		else if(choice == "Steak") {
			g.drawImage(customerImage, xPos, yPos, null);
			g.drawImage(steakImage, xPos + 26, yPos + 20, null);
		}
		else if(choice == "Chicken") {
			g.drawImage(customerImage, xPos, yPos, null);
			g.drawImage(chickenImage, xPos + 26, yPos + 20, null);
		}
		else if(choice == "Pizza") {
			g.drawImage(customerImage, xPos, yPos, null);
			g.drawImage(pizzaImage, xPos + 26, yPos + 20, null);
		}
		else if(choice == "Salad") {
			g.drawImage(customerImage, xPos, yPos, null);
			g.drawImage(saladImage, xPos + 26, yPos + 20, null);
		}
	}

	public void setHungry() {
		isHungry = true;
		agent.msgGotHungry();
	}
	public boolean isHungry() {
		return isHungry;
	}
	
	public boolean isPresent() {
        return true;
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
