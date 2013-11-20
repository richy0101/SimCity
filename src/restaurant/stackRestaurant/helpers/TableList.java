package restaurant.stackRestaurant.helpers;

import java.awt.Point;
import java.util.ArrayList;

public class TableList {
	
	private Point originTable = new Point(50,50);
	private static final int MAXTABLES = 35;
	
	@SuppressWarnings("serial")
	public static ArrayList<Point> tableLocations = new ArrayList<Point>() {
		{
            add(new Point(50, 50));
            add(new Point(150, 50));
            add(new Point(250, 50));
        }
    };
    
    public ArrayList<Point> getTables() {
    	return tableLocations;
    }
    
    public Point getLastTable() {
    	return getTables().get(getTables().size()-1);
    }
    
    public void addTable() {
    	if(getTables().size() < MAXTABLES) {
    		if(tableLocations.size()%5 == 0) {
    			tableLocations.add(new Point((int)originTable.getX(), (int)getLastTable().getY() + 100));
    		}
    		else {
    			tableLocations.add(new Point((int)getLastTable().getX() + 100, (int)getLastTable().getY()));
    		}
    	}
    }

}
