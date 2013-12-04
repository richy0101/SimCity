package restaurant.shehRestaurant.gui;

import javax.swing.*;

import gui.BuildingPanel;
import gui.Gui;
import gui.SimCityGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ShehRestaurantAnimationPanel extends BuildingPanel implements ActionListener {

    private final int WINDOWX = 827;
    private final int WINDOWY = 406;
    
    private final int FIRSTCOLUMNTABLES = 100;
    private final int SECONDCOLUMNTABLES = 200;
    private final int THIRDCOLUMNTABLES = 300;
    
    private final int FIRSTROWTABLES = 250;
    private final int TABLELENGTH = 50;
    private final int ORIGIN = 0;
    
    private final int CHEFSKITCHEN = 400; 
    private final int CHEFSPLATING = 200;
    private final int CHEFSCOOKING = 100;
    
    private final int speed = 8;
    
    private Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    public ShehRestaurantAnimationPanel(Rectangle2D r, int i, SimCityGui sc) {
    	super(r, i, sc);
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(speed, this );
    	timer.start();
    	
    	//IMAGE
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(ORIGIN, ORIGIN, WINDOWX, WINDOWY );

        //Here is the table
        g2.setColor(Color.ORANGE);
        g2.fillRect(SECONDCOLUMNTABLES, FIRSTROWTABLES, TABLELENGTH, TABLELENGTH);//TABLE1
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(FIRSTCOLUMNTABLES, FIRSTROWTABLES, TABLELENGTH, TABLELENGTH);//TABLE2
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(THIRDCOLUMNTABLES, FIRSTROWTABLES, TABLELENGTH, TABLELENGTH);//TABLE3
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(CHEFSKITCHEN, CHEFSPLATING, TABLELENGTH, TABLELENGTH);
        
        g2.setColor(Color.GRAY);
        g2.fillRect(CHEFSKITCHEN, CHEFSCOOKING, TABLELENGTH, TABLELENGTH);

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

    public void addGui(CookGui cookGui) {
        guis.add(cookGui);
    }
    
    public void addGui(CustomerGui customerGui) {
    	guis.add(customerGui);
    }

    public void addGui(HostGui gui) {
        guis.add(gui);
    }
    
    public void addGui(WaiterGui gui) {
    	guis.add(gui);
    }
    
    public void displayBuildingPanel() {
        myCity.displayBuildingPanel(this);
    }

	@Override
	public void addGui(Gui gui) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGui() {
		// TODO Auto-generated method stub
		
	}
}