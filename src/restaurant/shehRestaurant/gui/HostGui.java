package restaurant.shehRestaurant.gui;


import restaurant.shehRestaurant.CustomerAgent;
import restaurant.shehRestaurant.HostAgent; 
//import restaurant.gui.Table;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class HostGui implements Gui {

    private HostAgent agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position
    private int agentSize = 20; //default agent size

    //public static final int xTable = 200; //HARDCODE  
    public int xTable;
    public static final int yTable = 250; 
    public ArrayList<Table> table; //Declaration of Table

    public HostGui(HostAgent agent) {
        this.agent = agent;
        table = agent.getTables();
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

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + agentSize) & (yDestination == yTable - agentSize)) { 
           agent.msgAtTable();
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, agentSize, agentSize);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(CustomerAgent customer, Table table) { //SEATING AT TABLE
    		if(table.getTableNumber() == 1){
    			xTable = 200;
    		}
    		if(table.getTableNumber() == 2){
    			xTable = 100;
    		}
    		if(table.getTableNumber() == 3){
    			xTable = 300;
    		}
    		
    		xDestination = xTable + agentSize;
    		yDestination = yTable - agentSize;
    }

    public void DoLeaveCustomer() {
        xDestination = -agentSize;
        yDestination = -agentSize;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}