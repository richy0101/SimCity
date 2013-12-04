package restaurant.shehRestaurant.gui;


import restaurant.shehRestaurant.ShehCustomerRole;
import restaurant.shehRestaurant.ShehWaiterRole; 
import restaurant.shehRestaurant.helpers.Table;




import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class WaiterGui implements Gui {

    private ShehWaiterRole agent = null;

    private int xPos = 450, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position
    private int agentSize = 20; //default agent size
    
    private final int KIOSK = 30;
    private final int CHEFSKITCHEN = 400; 
    private final int CHEFSPLATING = 200;
    private final int CHEFSCOOKING = 100;
     
    public int xTable, homePosition;
    public static final int yTable = 250; 
    public ArrayList<Table> table; //Declaration of Table
    private Boolean wantsBreak = false;

	private RestaurantGui gui;

    public WaiterGui(ShehWaiterRole agent, RestaurantGui gui) {
        this.agent = agent;
        table = agent.getTables();
        
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

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + agentSize) & (yDestination == yTable - agentSize)) { 
           agent.msgAtTable();
        }
        
        if(xPos == KIOSK){
        	if(yPos == KIOSK) {
        		agent.msgAtKiosk();
        	}
        }
        if(xPos == CHEFSKITCHEN){
        	if(yPos == CHEFSPLATING) {
        		agent.msgAtKitchen();
        	}
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, agentSize, agentSize);
    }
    
    public void label(Graphics g, String label, int xLoc, int yLoc) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.drawString(label, xLoc, yLoc);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(ShehCustomerRole customer, Table table) { //SEATING AT TABLE
    		if(table.getTableNumber() == 1){
    			xTable = 100;
    		}
    		if(table.getTableNumber() == 2){
    			xTable = 200;
    		}
    		if(table.getTableNumber() == 3){
    			xTable = 300;
    		}
    		
    		xDestination = xTable + agentSize;
    		yDestination = yTable - agentSize;
    }
    
    public void GoToTable(Table table) { //SEATING AT TABLE
		if(table.getTableNumber() == 1){
			xTable = 100;
		}
		if(table.getTableNumber() == 2){
			xTable = 200;
		}
		if(table.getTableNumber() == 3){
			xTable = 300;
		}
		
		xDestination = xTable + agentSize;
		yDestination = yTable - agentSize;
    }
    
    public void DoGoToKitchen() {
    	xDestination = CHEFSKITCHEN;
    	yDestination = CHEFSPLATING;
    }
    
    public void DeliverFoodToTable(Table table, String order) {
    	//Graphics2D g2 = (Graphics2D)g;
    	//g2.setColor(Color.BLACK);
    	
		if(table.getTableNumber() == 1){
			xTable = 100;
			//g2.drawString(order, xTable, yTable);
		}
		if(table.getTableNumber() == 2){
			xTable = 200;
			//g2.drawString(order, xTable, yTable);
		}
		if(table.getTableNumber() == 3){
			xTable = 300;
			//g2.drawString(order, xTable, yTable);
		}
		
		xDestination = xTable + agentSize;
		yDestination = yTable - agentSize;
    }
    
    public void DoGoToKiosk() {
        xDestination = KIOSK;
        yDestination = KIOSK;
    }
    
    public void DoGoOnBreak() {
    	xDestination = 450;
    	yDestination = 0;
    }
    
    public void DoStandby(int num) {
    	xDestination = 410 - (homePosition * 40);
    	yDestination = 20;
    }
    
    public void setHome(int num) {
    	homePosition = num;
    }
    
	public void setOnBreak() {
		wantsBreak = true;
		agent.msgImTired();
		//setPresent(true);;
	}
	
	public void returnFromBreak() {
		agent.msgBackFromBreak();
		agent.msgAtKiosk();
	}
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

	public boolean isBreak() {
		return wantsBreak;
	}

}