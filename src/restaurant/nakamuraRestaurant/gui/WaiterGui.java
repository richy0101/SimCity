package restaurant.nakamuraRestaurant.gui;

import gui.Gui;

import java.awt.*;
import java.util.*;

import restaurant.nakamuraRestaurant.NakamuraCookRole;
import restaurant.nakamuraRestaurant.NakamuraCustomerRole;
import restaurant.nakamuraRestaurant.NakamuraWaiterRole;


public class WaiterGui implements Gui {

    private NakamuraWaiterRole role = null;

    private int xPos, yPos;//default waiter position
    private int xDestination, yDestination;//default start position

    private static final int xTable = 200; //Location of Table 1
    private static final int yTable = 100;
    private int xStart;
    private int yStart;
    private static final int xCooking = 525;
    private static final int yCooking = 200;
    private static final int xPlating = 525;
    private static final int yPlating = 100;
    private String choice;
    private boolean isTired = false;
    
    private enum Command {noCommand, getCustomer, seatCustomer, takeOrder, placeOrder, pickupFood, deliverFood, goHome};
    private Command command = Command.noCommand;
    
    private Map<Integer, Integer> tableX = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> tableY = new HashMap<Integer, Integer>();
    private Map<String, String> foodIcon = new HashMap<String, String>();
    
    public WaiterGui(NakamuraWaiterRole role, int x, int y) {
        this.role = role;
        
        tableX.put(1, xTable);	//Set coordinates for tables
        tableX.put(2, xTable + 150);
        tableX.put(3, xTable);
        tableX.put(4, xTable + 150);
        tableY.put(1, yTable);
        tableY.put(2, yTable);
        tableY.put(3, yTable + 100);
        tableY.put(4, yTable + 100);
        foodIcon.put("Steak", "ST");
        foodIcon.put("Chicken", "CH");
        foodIcon.put("Pizza", "PZ");
        foodIcon.put("Salad", "SA");
        
        xStart = x;
        yStart = y;
        xPos = x;
        yPos = y;
        xDestination = x;
        yDestination = y;
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
        	if(command == Command.getCustomer) {
            	role.msgActionComplete();
            	command = Command.noCommand;            		
        	}
        	else if(command != Command.noCommand) {
        		xDestination = xStart;
        		yDestination = yStart;
            	role.msgActionComplete();
            	command = Command.noCommand;
        	}
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
        if(command == Command.deliverFood) {
        	g.drawString(foodIcon.get(choice), xPos, yPos);
        }
    }

    public boolean isPresent() {
        return true;
    }
    
	public void setTired() {
		isTired = true;
		role.msgWantToGoOnBreak();
	}
	
	public void setNotTired() {
		isTired = false;
		role.msgBreakOver();
	}
	
	public boolean isTired() {
		return isTired;
	}

	public void DoGoToHome() {
		xDestination = xStart;
		yDestination = yStart;
		command = Command.goHome;
	}
    public void DoGoToHost() {
    	xDestination = 100;
    	yDestination = 75;
    	command = Command.getCustomer;
    }
    public void DoBringToTable(NakamuraCustomerRole customer, int tablenumber) {
    	CustomerGui cgui = customer.getGui();
		xDestination = tableX.get(tablenumber) + 20;
		yDestination = tableY.get(tablenumber) - 20;
		cgui.DoGoToSeat(tableX.get(tablenumber), tableY.get(tablenumber));
		command = Command.seatCustomer;
    }

    public void DoGoToTable(int tablenumber) {
		xDestination = tableX.get(tablenumber) + 20;
		yDestination = tableY.get(tablenumber) - 20;
		command = Command.takeOrder;
    }
    
    public void DoGoToPlating() {
    	xDestination = xPlating;
    	yDestination = yPlating;
    	command = Command.pickupFood;
    }
    
    public void DoDeliverFood(int tablenumber, String c, NakamuraCookRole cook) {
    	choice = c;
		xDestination = tableX.get(tablenumber) + 20;
		yDestination = tableY.get(tablenumber) - 20;
		cook.getGui().RemovePlating(c);
		command = Command.deliverFood;
    }
    
    public void DoGoToCook() {
    	xDestination = xCooking;
    	yDestination = yCooking;
    	command = Command.placeOrder;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
