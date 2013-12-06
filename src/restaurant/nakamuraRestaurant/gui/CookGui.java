package restaurant.nakamuraRestaurant.gui;

import gui.Gui;
import java.awt.*;
import java.util.ArrayList;

import restaurant.nakamuraRestaurant.NakamuraCookRole;

public class CookGui implements Gui {

    private NakamuraCookRole agent = null;

    private int xPos = 575, yPos = 100;//default Cook position
    private int xDestination = 575, yDestination = 100;//default start position
    private ArrayList<String> Cooking = new ArrayList<String>();
    private ArrayList<String> Plating = new ArrayList<String>();

    public static final int xCooking = 575;
    public static final int yCooking = 210;
    public static final int xPlating = 575;
    public static final int yPlating = 110;

    public CookGui(NakamuraCookRole cook) {
        this.agent = cook;
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

//        if (xPos == xDestination && yPos == yDestination
//        		& (xDestination > 0) & (yDestination > 0)) {
//        }
//        else if (xPos == -25 && yPos == -25 && xDestination == -25 && yDestination == -25){
//        	xDestination = -20;
//        	yDestination = -20;
//        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 20, 20);
        g.setColor(Color.BLACK);
        for(int i = 0; i < Cooking.size(); i++)
        	g.drawString(Cooking.get(i), xCooking - 25, yCooking + i*10);
        for(int i = 0; i < Plating.size(); i++)
        	g.drawString(Plating.get(i), xPlating - 25, yPlating + i*10);
    }

    public boolean isPresent() {
        return true;
    }
    
    public void checkInventory() {
    	agent.msgCheckInventory();
    }

    public void DoGoToCooking() {
    	xDestination = xCooking;
    	yDestination = yCooking;
    	agent.msgActionComplete();
    }

    public void DoGoToPlating() {
        xDestination = xPlating;
        yDestination = yPlating;
        agent.msgActionComplete();
    }
    
    public void AddCooking(String food) {
    	Cooking.add(food);
    }
    
    public void AddPlating(String food) {
    	Cooking.remove(food);
    	Plating.add(food);
    }
    
    public void RemovePlating(String food) {
    	Plating.remove(food);
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
