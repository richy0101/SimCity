package restaurant.huangRestaurant.gui;

import restaurant.huangRestaurant.CustomerAgent;
import restaurant.huangRestaurant.WaiterAgent;

import java.awt.*;

public class CustomerGui implements Gui{

	private CustomerAgent agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	
	RestaurantGui gui;

	private int xPos, yPos;
	private int xDestination, yDestination;
	private int xWait = 20;
	private int yWait = 40;
	public int currentSpot = 0;
	private static final int cashierX = 200; 
	private static final int cashierY = 500;
	private enum Command {noCommand, GoToSeat, GoToPay, LeaveRestaurant};
	private Command command=Command.noCommand;

	public CustomerGui(CustomerAgent c, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xPos = 20;
		yPos = 0;
		xDestination = 20;
		yDestination = 0;
		//maitreD = m;
		this.gui = gui;
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
			if (command==Command.GoToSeat){
				agent.msgAnimationFinishedGoToSeat();
			}
			else if (command == command.GoToPay) {
				agent.msgAnimationFinishedPay();
			}
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void DoGoToSeat(int xTable, int yTable) {//later you will map seatnumber to table coordinates.
		xDestination = xTable;
		yDestination = yTable;
		command = Command.GoToSeat;
		
	}
	public void DoGoToPay() {
		xDestination = cashierX;
		yDestination = cashierY;
		command =command.GoToPay;
	}

	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}

	public void FullCaseExitRestaurant() {
		xDestination = -41;
		yDestination = -41;
		command = Command.LeaveRestaurant;
	}

	public void setWaitingPos(int iterate) {
		currentSpot = iterate;
		yWait = yWait + iterate * 40;
		yPos = yWait;
		yDestination = yWait;
		xWait = 20;
		xPos = xWait;
		xDestination = xWait;
	}
	public void moveUpQueue() {
		currentSpot = currentSpot - 1;
		yWait = currentSpot * 40;
		yPos = yWait;
		yDestination = yWait;
		xWait = 20;
		xPos = xWait;
		xDestination = xWait;
	}
}
