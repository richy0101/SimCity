package restaurant.huangRestaurant.gui;

import restaurant.huangRestaurant.HuangCustomerRole;
import restaurant.huangRestaurant.HuangWaiterRole;

import java.awt.*;

public class CustomerGui implements Gui{

	private HuangCustomerRole agent = null;
	private boolean isPresent = true;
	private boolean isHungry = false;


	private int xPos = 35, yPos = 450;
	private int xDestination = 35, yDestination = 450;
	private int xWait = 35;
	private int yWait = 100;
	public int currentSpot = 0;
	private static final int cashierX = 780; 
	private static final int cashierY = 40;
	private int xExit = 0, yExit = 450;//Exit
	private enum Command {noCommand, GoToSeat, GoToPay, LeaveRestaurant};
	private Command command=Command.noCommand;

	public CustomerGui(HuangCustomerRole c){ //HostAgent m) {
		agent = c;
		xPos = 20;
		yPos = 0;
		xDestination = 20;
		yDestination = 0;
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
		xPos = xWait;
		xDestination = xWait;
	}
	public void moveUpQueue() {
		currentSpot = currentSpot - 1;
		yWait = currentSpot * 40;
		yPos = yWait;
		yDestination = yWait;
		xPos = xWait;
		xDestination = xWait;
	}
}
