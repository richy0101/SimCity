package restaurant.nakamuraRestaurant.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import gui.BuildingPanel;
import gui.Gui;
import gui.SimCityGui;

public class NakamuraRestaurantAnimationPanel extends BuildingPanel implements ActionListener {

	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
    private static final int xTable1 = 126;
    private static final int yTable1 = 286;
    private static final int xTable2 = 286;
    private static final int yTable2 = 286;
    private static final int xTable3 = 455;
    private static final int yTable3 = 286;
    private static final int xTable4 = 608;
    private static final int yTable4 = 286;
    //private static final int TableSize = 50;
    
    private static final int xWaiting = 669;
    private static final int yWaiting = 131;
    private static final int WaitingSize = 25;
    
    private static final int xKitchen = 56;
    private static final int yKitchen = 57;
    //private static final int xKitchenSize = 120;
    //private static final int yKitchenSize = 200;

    private static final int xCooking = 55;
    private static final int yCooking = 55;
    private static final int xCookingSize = 25;
    private static final int yCookingSize = 50;

    private static final int xPlating = 86;
    private static final int yPlating = 137;
    private static final int xPlatingSize = 25;
    private static final int yPlatingSize = 50;
    
    private Image bufferImage;
    private Dimension bufferSize;
    
    BufferedImage restaurantImage;

    private List<Gui> guis = new ArrayList<Gui>();

	
    public NakamuraRestaurantAnimationPanel(Rectangle2D r, int i, SimCityGui sc) {
    	super(r, i, sc);
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
    	try {
        	restaurantImage = ImageIO.read(getClass().getResource("nakamuraRestaurant.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
 
    	Timer timer = new Timer(20, this );
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.drawImage(restaurantImage, 0, 0, null);

        //Here are the tables
        //g2.setColor(Color.ORANGE);
        /*
        g2.fillRect(xTable, yTable, TableSize, TableSize);
        g2.fillRect(xTable + 150, yTable, TableSize, TableSize);
        g2.fillRect(xTable, yTable + 100, TableSize, TableSize);
        g2.fillRect(xTable + 150, yTable + 100, TableSize, TableSize);//200 and 250 need to be table params
       
        //Customer waiting area
        g2.fillRect(xWaiting, yWaiting, WaitingSize, WaitingSize);
        g2.fillRect(xWaiting, yWaiting + 35, WaitingSize, WaitingSize);
        g2.fillRect(xWaiting, yWaiting + 70, WaitingSize, WaitingSize);
        g2.fillRect(xWaiting, yWaiting + 105, WaitingSize, WaitingSize);
        g2.fillRect(xWaiting, yWaiting + 140, WaitingSize, WaitingSize);
        
        //Kitchen
        g2.setColor(Color.GRAY);
        g2.fillRect(xKitchen, yKitchen, xKitchenSize, yKitchenSize);
        g2.setColor(Color.ORANGE);
        g2.fillRect(xCooking, yCooking, xCookingSize, yCookingSize);
        g2.fillRect(xPlating, yPlating, xPlatingSize, yPlatingSize);
        
        */
        
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
    }
	
	public void updateGui() {
        for(Gui gui : guis) {
            if (gui.isPresent())
                gui.updatePosition();
        }
	}

    public void addGui(Gui gui) {
        guis.add(gui);
    }
    
    public void removeGui(Gui gui) {
    	guis.remove(gui);
    }

	public void displayBuildingPanel() {
		myCity.displayBuildingPanel(this);	
		
	}
}
