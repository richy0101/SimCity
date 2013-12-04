package restaurant.shehRestaurant.gui;

import restaurant.shehRestaurant.CustomerRole;
import restaurant.shehRestaurant.HostAgent;
import restaurant.shehRestaurant.helpers.Table;

import java.awt.*;

import javax.swing.*;

public class CustomerGui implements Gui{

	private CustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private HostAgent host;
	Table table; //from CustomerAgent
	RestaurantGui gui;

	private int xPos, yPos, agentSize;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	private Command command=Command.noCommand;

	//public static final int xTable = 200;
	public int xTable;
	public static final int yTable = 250;

	public CustomerGui(CustomerRole c, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
		
		agentSize = 20;
		
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
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
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
		g.fillRect(xPos, yPos, agentSize, agentSize);
		//customerLabel.setHorizontalTextPosition(JLabel.CENTER);
		//customerLabel.setVerticalTextPosition(JLabel.CENTER);
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
	/*
	public void Queue(int num) {
		queue = num;
		System.out.println("q" + queue);
		
	}
	*/
	public void DoWaitInRestaurant(int queue) {
		//System.out.println("do" + queue);
		xDestination = 10 + (queue * 40);
		yDestination = 10;
		
	}

	public void DoGoToSeat(int seatnumber, Table table) {//later you will map seat number to table coordinates.
		if(table.getTableNumber() == 1) {
			xTable = 100;
		}
		if(table.getTableNumber() == 2) {
			xTable = 200;
		}
		if(table.getTableNumber() == 3) {
			xTable = 300;
		}
		
		xDestination = xTable;
		yDestination = yTable;
		command = Command.GoToSeat;
	}

	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
}