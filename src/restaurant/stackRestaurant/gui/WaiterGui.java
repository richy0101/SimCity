package restaurant.stackRestaurant.gui;

import restaurant.stackRestaurant.StackWaiterRole;
import restaurant.stackRestaurant.helpers.TableList;
import gui.Gui;

import java.awt.*;

public class WaiterGui implements Gui {

    private StackWaiterRole agent = null;
    private TableList tableList = new TableList();
    private String choice = "";
    
    private int xHome = 50, yHome = 250;
    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = 50, yDestination = 250;//default start position
    private int xTable = -20, yTable = -20;
    private int xCook = 280, yCook = 200;
    private int xBreak = 200, yBreak = 300;
    private int WAITINGX = 3, WAITINGY = 3;
  
    RestaurantGui gui;
    
    public static final int HOSTSIZE = 20;

    public WaiterGui(StackWaiterRole agent, RestaurantGui gui) {
        this.agent = agent;
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

        if (xPos == xDestination && yPos == yDestination && xDestination == WAITINGX + HOSTSIZE && yDestination == WAITINGY - HOSTSIZE) {
        	agent.msgAtCustomer();
        }
        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + HOSTSIZE) & (yDestination == yTable - HOSTSIZE)) {
        	agent.msgAtTable();
        	DoGoHome();
        }
        if (xPos == xCook && yPos == yCook 
        		&& (xDestination == xCook && yDestination == yCook)) {
        	agent.msgAtCook();
        	DoGoHome();	
        }
        if(xPos == xHome && yPos == yHome) {
        	agent.msgAnimationHome();
        }
    }

    public void updateGui(String choice) {
    	this.choice = choice;
    }
    
    public void setWantsToGoOnBreak() {
    	agent.msgIWantToGoOnBreak();
    }
    
    public void setWaiterCheckOff() {
    	gui.setWaiterBreakOff(agent);
    }

    public void setGoingOffBreak() {
    	agent.msgImComingOffBreak();
    	DoGoHome();
    	
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, HOSTSIZE, HOSTSIZE);
        g.setColor(Color.WHITE);
        g.drawString(choice, xPos + 20, yPos + 20);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(int table) {
    	
    	xTable = (int)tableList.getTables().get(table-1).getX();
		yTable = (int)tableList.getTables().get(table-1).getY();
        xDestination = xTable + HOSTSIZE;
        yDestination = yTable - HOSTSIZE;
    }
    
    public void DoGoToCustomer() {
    	xDestination = WAITINGX + HOSTSIZE;
    	yDestination = WAITINGY - HOSTSIZE;
    }
  

    public void DoGoHome() {
        xDestination = xHome;
        yDestination = yHome;
        
    }
    
    public void DoGoOnBreak() {
    	xDestination = xBreak;
    	yDestination = yBreak;
    }
    
    public void DoGoToCook() {
    	xDestination = xCook;
    	yDestination = yCook;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
