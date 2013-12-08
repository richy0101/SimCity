package restaurant.huangRestaurant.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import restaurant.huangRestaurant.HuangCookRole;




public class CookGui implements Gui {
	private HuangCookRole agent = null;
	private String currentDish;
    private int xPos = 320, yPos = 415;//default waiter position
    private int xDestination = 320, yDestination = 415;//default start position
    private int xHome = 320, yHome = 415;//Cook Home positions
    private int xCookArea = xHome - 40, yCookArea = yHome + 30;//Customer Waiting area.
    private int xPlateArea = xHome - 40, yPlateArea = yHome -25;
    public class CookingFood {;
    	String food;
    	int xPos;
    	int yPos;
    	int table;
    	CookingFood(String food, int table) {
    		this.food = food;
    		this.xPos = xCookArea + dishOffSetX * table;
    		this.yPos = yCookArea;
    		this.table = table;
    	}
    }
    public class PlatedFood {;
    	String food;
    	int xPos;
    	int yPos;
    	int table;
    	PlatedFood(String food, int table) {
    		this.food = food;
    		this.xPos = xPlateArea + dishOffSetX * table;
    		this.yPos = yPlateArea;
    		this.table = table;
    	}
    }
    private List<PlatedFood> pFood = new ArrayList<PlatedFood>();
    private List<CookingFood> cFood = new ArrayList<CookingFood>();
    public int xTable = 100;
    public int yTable = 200;
	private static final int dishOffSetX = 20;
	

	//private HostAgent host;
	
    public CookGui(HuangCookRole agent) {
        this.agent = agent;
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

        if (xPos == xDestination && yPos == yDestination & (xDestination == xTable + 20) & (yDestination == yTable - 20)) {
        	
        }
    }

    public void draw(Graphics2D g) {
    	g.setColor(Color.RED);
	    g.fillRect(xPos, yPos, 20, 20);
	    //Plate area
	    g.fillRect(xPlateArea, yPlateArea, 100, 20);
	    //cook area
	    g.fillRect(xCookArea, yCookArea, 100, 20);
	    
	    if(!cFood.isEmpty()) {
	    	for(CookingFood cf : cFood) {
    			g.drawString(cf.food, cf.xPos, cf.yPos);
    		}
    	}
	    if(!pFood.isEmpty()) {
	    	for(PlatedFood pf : pFood) {
    			g.drawString(pf.food, pf.xPos, pf.yPos);
    		}
    	}
    }
    public boolean isPresent() {
        return true;
    }
    public void DoRemovePlatedDish(int table) {
    	for(PlatedFood pf: pFood) {
    		if (pf.table == table) {
    			pFood.remove(pf);
    			break;
    		}
    	}
    }
    public void DoPlateDish(int table) {
    	for(CookingFood cf: cFood) {
    		if (cf.table == table) {
    			PlatedFood pf = new PlatedFood(cf.food, cf.table);
    			pFood.add(pf);
    			cFood.remove(cf);
    			break;
    		}
    	}
    }
    public void DoCookDish(String choice, int table) {
    	currentDish = (String) choice.subSequence(0,2);
    	CookingFood cf = new CookingFood(currentDish, table);
    	cFood.add(cf);
    }
    
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
