package restaurant.tanRestaurant.gui;

import restaurant.tanRestaurant.TanCustomerRole;
import restaurant.tanRestaurant.TanCustomerRole.AgentState;
import restaurant.tanRestaurant.TanCustomerRole.OrderStatus;
import gui.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CustomerGui implements Gui{

	private TanCustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private HostAgent host;
	//RestaurantGui gui;

	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToWaitingSeat, GoToSeat, LeaveRestaurant, EscapeRestaurant};
	private Command command=Command.noCommand;

	public static final int xTable = 200;
	public static final int yTable = 250;

	public static final int xSeat = 50;
	public static final int ySeat = 100;
	
	BufferedImage customerImage;
	BufferedImage chickenImage;
	BufferedImage pizzaImage;
	BufferedImage saladImage;
	BufferedImage steakImage;

	public CustomerGui(TanCustomerRole c){//, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xPos = -20;
		yPos = -20;
		xDestination = -40;
		yDestination = -40;
		//maitreD = m;
		//this.gui = gui;
		
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

	/*
	public void clearHungry(){
		isHungry = false;
		gui.setCustomerEnabled(agent);
	}*/
	
	public void updatePosition() {
		if(command != Command.EscapeRestaurant){
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;
		

		if (xPos == xDestination && yPos == yDestination
        		& (xDestination == 200) & (yDestination == 20)) {
        	//if (agent.ws == WaiterState.approachingTable)
        	agent.msgAtCashier();
        }
		
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				//System.out.println("about to call gui.setCustomerEnabled(agent); from custgui");
				isHungry = false;
				//gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
		}
		else{
			if (xPos < xDestination)
				xPos=xPos+3;
			else if (xPos > xDestination)
				xPos=xPos-3;

			if (yPos < yDestination)
				yPos=yPos+3;
			else if (yPos > yDestination)
				yPos=yPos-3;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		if(agent.os == OrderStatus.unavailable)
			g2.drawString("",xPos, yPos);
		
		else if(agent.os == OrderStatus.ordered){
			if (agent.o.getName()== "Chicken")
				g2.drawString("Ch?", xPos, yPos-2);
			else if (agent.o.getName()== "Steak")
				g2.drawString("St?", xPos, yPos-2);
			else if (agent.o.getName()== "Salad")
				g2.drawString("Sa?", xPos, yPos-2);
			else //(agent.o.getName()== "Pizza")
				g2.drawString("Pi?", xPos, yPos-2);
		}
		else if(agent.os == OrderStatus.received){
			if (agent.o.getName()== "Chicken")
					g.drawImage(chickenImage, xPos, yPos-2, null);
				else if (agent.o.getName()== "Steak")
					g.drawImage(steakImage, xPos, yPos-2, null);
				else if (agent.o.getName()== "Salad")
					g.drawImage(saladImage, xPos, yPos-2, null);
				else //(agent.o.getName()== "Pizza")
					g.drawImage(pizzaImage, xPos, yPos-2, null);
		}
		if(command==Command.EscapeRestaurant){
			g2.drawString(">:) ", xPos, yPos-2);
		}
		g.drawImage(customerImage, xPos, yPos, null);
		//g.fillRect(xPos, yPos, 20, 20);
	}

	public boolean isPresent() {
		//return isPresent;
		return true;
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
		//isPresent = p;
		isPresent = true;
	}

    public void GoToCashier(){
    	xDestination= 200;
    	yDestination= 20;
    }
	
	public void DoGoToSeat(int seatnumber) {
		if(seatnumber==1){
			xDestination = xTable;
			yDestination = yTable;
			command = Command.GoToSeat;
		}
		else if(seatnumber==2){
			xDestination= xTable+150;
			yDestination = yTable;
			command = Command.GoToSeat;
		}
		else if(seatnumber==3){
			xDestination= xTable+150;
			yDestination = yTable-150;
			command = Command.GoToSeat;
		}
	}

	public void DoGoToWaitingSeat(int sn){
		if(sn==1){
			xDestination = xSeat;
			yDestination = ySeat;
			command=Command.GoToWaitingSeat;
		}
		if(sn==2){
			xDestination = xSeat-30;
			yDestination = ySeat;
			command=Command.GoToWaitingSeat;
		}
		if(sn==3){
			xDestination = xSeat;
			yDestination = ySeat+30;
			command=Command.GoToWaitingSeat;
		}
		if(sn==4){
			xDestination = xSeat-30;
			yDestination = ySeat+30;
			command=Command.GoToWaitingSeat;
		}
		if(sn==5){
			xDestination = xSeat;
			yDestination = ySeat+60;
			command=Command.GoToWaitingSeat;
		}
		if(sn==6){
			xDestination = xSeat-30;
			yDestination = ySeat+60;
			command=Command.GoToWaitingSeat;
		}
		if(sn==7){
			xDestination = xSeat;
			yDestination = ySeat+90;
			command=Command.GoToWaitingSeat;
		}
		if(sn==8){
			xDestination = xSeat-30;
			yDestination = ySeat+90;
			command=Command.GoToWaitingSeat;
		}
		if(sn==9){
			xDestination = xSeat;
			yDestination = ySeat+120;
			command=Command.GoToWaitingSeat;
		}
		if(sn==10){
			xDestination = xSeat-30;
			yDestination = ySeat+120;
			command=Command.GoToWaitingSeat;
		}
	}
	
	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
	
	public void DoEscapeRestaurant() {
		System.out.println("CAN'T CATCH ME!");
		xDestination = -40;
		yDestination = -40;
		command = Command.EscapeRestaurant;
	}
}
