package stackRestaurant.gui;

import stackRestaurant.CookAgent;

import java.awt.*;

public class CookGui implements Gui {

    private CookAgent agent = null;
    
    private int xHome = 250, yHome = 250;
    private int xPos = 250, yPos = 250;//default waiter position
    private int xDestination = 250, yDestination = 250;//default start position
    private int PLATINGX = 280, PLATINGY = 220 + COOKSIZE;
    private int COOKTOPX = 280, COOKTOPY = 300 - COOKSIZE;
    private int FRIDGEX = 310 - COOKSIZE, FRIDGEY = 250;
    
    private enum Command 
    {noCommand, GoToFridge, GoToCooktop, GoToPlating};
	private Command command=Command.noCommand;
  
    RestaurantGui gui;
    
    public static final int COOKSIZE = 20;

    public CookGui(CookAgent agent, RestaurantGui gui) {
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
        
        if(xPos == xDestination && yPos == yDestination) {
        	if(xDestination == COOKTOPX && yDestination == COOKTOPY && command == Command.GoToCooktop) {
        		agent.msgAtCooktop();
        	} else if(xDestination == PLATINGX && yDestination == PLATINGY && command == Command.GoToPlating) {
        		agent.msgAtPlating();
        		DoGoHome();
        	} else if(xDestination == FRIDGEX && yDestination == FRIDGEY && command == Command.GoToFridge) {
        		agent.msgAtFridge();
        	}
        	command = Command.noCommand;
        }   
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect(xPos, yPos, COOKSIZE, COOKSIZE);
    }
    
    public void DoGoToFridge() {
    	xDestination = FRIDGEX;
    	yDestination = FRIDGEY;
    	command = Command.GoToFridge;
    }
    
    public void DoGoToCookTop() {
    	xDestination = COOKTOPX;
    	yDestination =  COOKTOPY;
    	command = Command.GoToCooktop;
    }
    
    public void DoGoToPlatingArea() {
    	xDestination = PLATINGX;
    	yDestination = PLATINGY;
    	command = Command.GoToPlating;
    }

    public void DoGoHome() {
    	xDestination = xHome;
    	yDestination = yHome;
    }
    
    public boolean isPresent() {
        return true;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
