package restaurant.shehRestaurant.gui;

import restaurant.shehRestaurant.CookAgent;

import java.awt.*;
import java.util.ArrayList;

public class CookGui implements Gui {

    private CookAgent agent = null;

    private int xPos = 400, yPos = 160;//default waiter position
    private int xDestination = 400, yDestination = 160;//default start position
    private int XHOME = 400, YHOME = 160;
    private int agentSize = 20; //default agent size
    
    private final int CHEFSKITCHEN = 400; 
    private final int CHEFSPLATING = 200;
    private final int CHEFSCOOKING = 100;
     
    public int xTable;
    public static final int yTable = 250; 
    public ArrayList<Table> table; //Declaration of Table

	private RestaurantGui gui;

    public CookGui(CookAgent agent) {
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
/*
        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + agentSize) & (yDestination == yTable - agentSize)) { 
           //agent.msgCooking(); //CHANGE FOR SEMAPHORES
        }
*/
        
        if(xPos == CHEFSKITCHEN && yPos == CHEFSPLATING){
        	agent.msgPlating();
    	}
   
        if(xPos == CHEFSKITCHEN && yPos == CHEFSCOOKING){
    		agent.msgCooking();
        }
       
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(xPos, yPos, agentSize, agentSize);
    }
    
    public void label(Graphics g, String label, int xLoc, int yLoc) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.drawString(label, xLoc, yLoc);
    }
    
    public void DoCooking() {
        xDestination = CHEFSKITCHEN;
        yDestination = CHEFSCOOKING;
    }
    
    public void DoPlating() {
    	xDestination = CHEFSKITCHEN;
    	yDestination = CHEFSPLATING;
    }
    
    public void DoStandby() {
    	//xDestination = xDestination + (num * 10);
    	xDestination = XHOME;
    	yDestination = YHOME;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isPresent() {
        return true;
    }
}