package restaurant.nakamuraRestaurant.gui;

import gui.Gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import restaurant.nakamuraRestaurant.NakamuraCustomerRole;

public class CustomerGui implements Gui{

	private NakamuraCustomerRole role = null;
	private boolean isPresent = true;
	private boolean isHungry = false;

	//private HostAgent host;

	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, WaitingForSeat, Entering, BeingSeated, GoToSeat, Waiting, Eating, LeaveRestaurant};
	private Command command=Command.noCommand;
	private String choice;
    private Map<String, String> foodIcon = new HashMap<String, String>();

	public static final int xHost = 75;
	public static final int yHost = 50;
	public static final int xWaiting = 10;
	public static final int yWaiting = 100;
	public int xTable = 100;
	public int yTable = 75;

	public CustomerGui(NakamuraCustomerRole c){ //HostAgent m) {
		role = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
        foodIcon.put("Steak", "ST");
        foodIcon.put("Chicken", "CH");
        foodIcon.put("Pizza", "PZ");
        foodIcon.put("Salad", "SA");
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
			if(command == Command.Entering) {
				role.msgAnimationFinishedEnter();
				command = Command.noCommand;
			}
			else if (command==Command.WaitingForSeat){
				role.msgAnimationFinishedWaiting();
				command = Command.noCommand;
			}
			else if (command==Command.BeingSeated){
				role.msgAnimationFinishedSitting();
				command = Command.noCommand;
			}
			else if (command==Command.GoToSeat){
				role.msgAnimationFinishedGoToSeat();
				command = Command.noCommand;
			}
			else if (command==Command.LeaveRestaurant) {
				role.msgAnimationFinishedLeaveRestaurant();
				isHungry = false;
				command = Command.noCommand;
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
		if(command == Command.Waiting)
        	g.drawString(foodIcon.get(choice) + "?", xPos, yPos);
			
		else if(command == Command.Eating)
        	g.drawString(foodIcon.get(choice), xPos, yPos);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		role.msgGotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}
	
	public void DoEnterRestaurant() {
		xDestination = xHost;
		yDestination = yHost;
		command = Command.Entering;
	}
	
	public void DoGoToWaiting() {
		xDestination = xWaiting;
		yDestination = yWaiting;
		command = Command.WaitingForSeat;		
	}
	
	public void DoWaitAt(int seat) {
		xDestination = xWaiting;
		yDestination = yWaiting + seat*35;
		command = Command.WaitingForSeat;			
	}
	
	public void DoGoToHost() {
		xDestination = xHost;
		yDestination = yHost;
		command = Command.BeingSeated;
	}

	public void DoGoToSeat(int x, int y) {
		xDestination = x;
		yDestination = y;
		command = Command.GoToSeat;
	}
	
	public void DoSetChoice(String c) {
		choice = c;
		command = Command.Waiting;
	}
	
	public void DoEat() {
		command = Command.Eating;
	}

	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
}
