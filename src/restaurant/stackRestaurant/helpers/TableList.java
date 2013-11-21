package restaurant.stackRestaurant.helpers;

import java.awt.Point;
import java.util.ArrayList;

public class TableList {	
	@SuppressWarnings("serial")
	public static ArrayList<Point> tableLocations = new ArrayList<Point>() {
		{
            add(new Point(72, 45));
            add(new Point(72, 181));
            add(new Point(72, 317));
            add(new Point(200, 45));
            add(new Point(200, 181));
            add(new Point(200, 317));
            add(new Point(320, 45));
            add(new Point(320, 181));
            add(new Point(320, 317));
        }
    };
    
    public ArrayList<Point> getTables() {
    	return tableLocations;
    }
    
    public Point getLastTable() {
    	return getTables().get(getTables().size()-1);
    }
}
