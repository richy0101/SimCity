package restaurant.stackRestaurant.gui;

import restaurant.stackRestaurant.StackCustomerRole;
import restaurant.stackRestaurant.helpers.TableList;
import gui.Gui;

import java.awt.*;

public class CustomerGui implements Gui{

	private StackCustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;
	private TableList tableList = new TableList();
	private String choice = "";


	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToWaitingArea, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;

	
	private static final int CUSTOMERSIZE = 20;
	private static final int CASHIERX = 100;
	private static final int CASHIERY = 180;
	private static final int WAITINGX = 3;
	private static final int WAITINGY = 3;

	public CustomerGui(StackCustomerRole c){ //HostAgent m) {
		agent = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
		//maitreD = m;
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
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, CUSTOMERSIZE, CUSTOMERSIZE);
		g.setColor(Color.WHITE);
		g.drawString(choice, xPos + 20, yPos + 20);
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
