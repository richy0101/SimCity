package restaurant.tanRestaurant.gui;

import restaurant.tanRestaurant.TanCustomerRole;
import restaurant.tanRestaurant.TanHostAgent;
import restaurant.tanRestaurant.TanCashierAgent;
import restaurant.tanRestaurant.TanCustomerRole.OrderStatus;
import gui.Gui;

import java.awt.*;

public class CashierGui implements Gui {

    private TanCashierAgent agent = null;

    private int xPos = 200, yPos = 20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position

    public static final int xTable = 200;
    public static final int yTable = 250;

    public CashierGui(TanCashierAgent agent) {
        this.agent = agent;
    }

    
    public void updatePosition() {
    	/*
        if(xPos==-20 && yPos==-20){
        	agent.msgAtStart();
        }
    	if (xPos < xDestination)
            xPos++;
        else if (xPos > xDestination)
            xPos--;

        if (yPos < yDestination)
            yPos++;
        else if (yPos > yDestination)
            yPos--;

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 20) & (yDestination == yTable - 20)) {
           agent.msgAtTable();
        }
        else if(xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 150 + 20) & (yDestination == yTable - 20)) {
            agent.msgAtTable();
         }
        else if(xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 150 + 20) & (yDestination == yTable - 20-150)) {
            agent.msgAtTable();
         }*/
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
        Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.drawString("Cashier",xPos-12, yPos+45);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(TanCustomerRole customer, int seatnumber) {
        if (seatnumber==1){
        	xDestination = xTable + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==2){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==3){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20 - 150;
        }
    }

    public void DoLeaveCustomer() {
        xDestination = -20;
        yDestination = -20;
    }
    
    public boolean isAtStart(){
    	if ((xPos == -20) &&(yPos==-20)){
    		return true;
    	}
    	else return false;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
