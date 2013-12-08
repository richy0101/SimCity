package restaurant.huangRestaurant.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import restaurant.huangRestaurant.HuangWaiterRole;
import restaurant.huangRestaurant.interfaces.Customer;



public class WaiterGui implements Gui {
	private HuangWaiterRole agent = null;
	private boolean seatingNew = false;
	private boolean carryingFood = false;
	private boolean wantsBreak = false;
	private boolean onBreak = false;
	private String currentDish;
    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position
    private int xHome = 100, yHome = 50;//waiter Home positions
    private int xCWaitArea = 40, yCWaitArea = 20;//Customer Waiting area.
    
    private class ServedFood {;
    	String food;
    	int xPos;
    	int yPos;
    	
    	
    	ServedFood(String food, int x, int y) {
    		this.food = food;
    		this.xPos = x;
    		this.yPos = y;
    	}
    }
    private List<ServedFood>foodOnTable = new ArrayList<ServedFood>();
    public int xTable = 100;
    public int yTable = 200;
    private static final int tableSpawnX = 100;
	private static final int tableSpawnY = 200;
	private static final int tableOffSetX = 100;
	
	private static final int cookX = 300; 
	private static final int cookY = 400;
	private static final int cashierX = 50; 
	private static final int cashierY = 500;

	//private HostAgent host;

    public WaiterGui(HuangWaiterRole huangWaiterRole) {
		// TODO Auto-generated constructor stub
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
        		& (xDestination == xTable + 20) & (yDestination == yTable - 20)) {
        	if(carryingFood == true) {
        		foodOnTable.add(new ServedFood(currentDish, xTable + 20, yTable + 20));
        		carryingFood = false;
        		currentDish ="";
        	}
           agent.msgAtTable();
        }
        if (xPos == cookX && yPos == cookY) {
        	carryingFood = false;
        	currentDish ="";
        	agent.msgAtCook();
        }
        if (xPos == cashierX && yPos == cashierY) {
        	agent.msgAtCashier();
        }
    	if (xPos == 40 && yPos == 20 && seatingNew == true) {
    		agent.msgCanSeatNew();
    		seatingNew = false;
    	}
    }

    public void draw(Graphics2D g) {
    	if (carryingFood == true) {
	        g.setColor(Color.MAGENTA);
	        g.fillRect(xPos, yPos, 20, 20);
	        g.drawString(currentDish, xPos, yPos);
	        if(!foodOnTable.isEmpty()) {
    			for(ServedFood served : foodOnTable) {
    				g.drawString(served.food, served.xPos, served.yPos);
    			}
    		}
	        
    	}
    	else {
    		g.setColor(Color.MAGENTA);
    		if(!foodOnTable.isEmpty()) {
    			for(ServedFood served : foodOnTable) {
    				g.drawString(served.food, served.xPos, served.yPos);
    			}
    		}
	        g.fillRect(xPos, yPos, 20, 20);
    	}
    }
    public boolean isPresent() {
        return true;
    }
    public void DoBringToTable(Customer customer, int table) {
    	this.xTable = tableSpawnX + (tableOffSetX * table);
    	this.yTable = tableSpawnY;
    	customer.getGui().DoGoToSeat(xTable, yTable);
        xDestination = xTable + 20;
        yDestination = yTable - 20;
       
    }
    public void DoGoToCustomer(int table) {
    	this.xTable = tableSpawnX + (tableOffSetX * table);
    	this.yTable = tableSpawnY;
        xDestination = xTable + 20;
        yDestination = yTable - 20;
    	
    }
    public void DoGoToCook(String choice) {
    	currentDish = (String) choice.subSequence(0,2);
    	currentDish = currentDish + '?';
    	carryingFood = true;
    	xDestination = cookX;
    	yDestination = cookY;
    }
    public void DoGoToCook() {
    	carryingFood = true;
    	xDestination = cookX;
    	yDestination = cookY;
    }
    public void DoGoToCashier() {
    	xDestination = cashierX;
    	yDestination = cashierY;
    }
    public void drawOrder(String s) {
    	carryingFood = true;
    	currentDish = (String) s.subSequence(0, 2);
    }
    public void DoCleanTable(int table) {
		this.xTable = tableSpawnX + (tableOffSetX * table);
    	this.yTable = tableSpawnY;
    	if(!foodOnTable.isEmpty()) {
    		for(ServedFood served : foodOnTable) {
    			if(served.xPos == xTable + 20 && served.yPos == yTable + 20) {
    				foodOnTable.remove(served);
    				break;
    			}
    		}
    	}
    }
    public void setHome(int iterate) {
    	xHome = xHome + 30 * iterate;
    	xPos = xHome;
    	xDestination = xPos;
    	yPos = yHome;
    	yDestination = yPos;
    }
    public void DoLeaveCustomer() {
        xDestination = xHome;
        yDestination = yHome;
    }
    public void DoGoSeatNew() {
    	xDestination = xCWaitArea;
    	yDestination = yCWaitArea;
    	seatingNew = true;
    }
    public boolean isBreak() {
    	if (wantsBreak == true) {
    		return wantsBreak;
    	}
    	return onBreak;
    }
    public void setBreak() {
    	wantsBreak = true;
		agent.msgWantsBreak();
    }
    public void enableBreak() {
    	wantsBreak = false;
    	onBreak = true;
    }
    public void endBreakSequence() {
    	wantsBreak = false;
    	onBreak = false;
    }
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
