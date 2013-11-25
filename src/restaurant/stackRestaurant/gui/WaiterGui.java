package restaurant.stackRestaurant.gui;

import restaurant.stackRestaurant.StackWaiterRole;
import restaurant.stackRestaurant.helpers.TableList;
import gui.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WaiterGui implements Gui {

    private StackWaiterRole agent = null;
    private TableList tableList = new TableList();
    private String choice = "";
    BufferedImage waiterImage;
    
    //827 x 406 y
    
    private int xHome = 50, yHome = 250;
    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = 50, yDestination = 250;//default start position
    private int xTable = -20, yTable = -20;
    private int xCook = 485, yCook = 70;
    private int xBreak = 200, yBreak = 300;
    private int WAITINGX = 3, WAITINGY = 3;
  
    
    private static final int PERSONSIZEX = 32, PERSONSIZEY = 40;

    public WaiterGui(StackWaiterRole agent) {
        this.agent = agent;
        
        try {
        	waiterImage = ImageIO.read(getClass().getResource("stackRestaurantWaiter.png"));
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

        if (xPos == xDestination && yPos == yDestination && xDestination == WAITINGX + PERSONSIZEX && yDestination == WAITINGY - PERSONSIZEY) {
        	agent.msgAtCustomer();
        }
        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + PERSONSIZEX) & (yDestination == yTable - PERSONSIZEY)) {
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
//    	gui.setWaiterBreakOff(agent);
    }

    public void setGoingOffBreak() {
    	agent.msgImComingOffBreak();
    	DoGoHome();
    	
    }
    
    public void draw(Graphics2D g) {
    	g.drawImage(waiterImage, xPos, yPos, null);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(int table) {
    	
    	xTable = (int)tableList.getTables().get(table-1).getX();
		yTable = (int)tableList.getTables().get(table-1).getY();
        xDestination = xTable + PERSONSIZEX;
        yDestination = yTable - PERSONSIZEY;
    }
    
    public void DoGoToCustomer() {
    	xDestination = WAITINGX + PERSONSIZEX;
    	yDestination = WAITINGY - PERSONSIZEY;
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
