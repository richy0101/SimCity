package restaurant.stackRestaurant.gui;

import restaurant.stackRestaurant.StackCookRole;
import gui.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CookGui implements Gui {

    private StackCookRole agent = null;
    
    private int xHome = 250, yHome = 250;
    private int xPos = 250, yPos = 250;//default waiter position
    private int xDestination = 250, yDestination = 250;//default start position
    private int PLATINGX = 543, PLATINGY = 70;
    private int COOKTOPX = 781, COOKTOPY = 35;
    private int FRIDGEX = 595, FRIDGEY = 35;
    
    BufferedImage cookImage;
    
    private enum Command 
    {noCommand, GoToFridge, GoToCooktop, GoToPlating};
	private Command command=Command.noCommand;
  
    
	private static final int PERSONSIZEX = 32, PERSONSIZEY = 40;

    public CookGui(StackCookRole agent) {
        this.agent = agent;
        
        try {
        	cookImage = ImageIO.read(getClass().getResource("stackRestaurantCook.png"));
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
    	g.drawImage(cookImage, 0, 0, null);
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
