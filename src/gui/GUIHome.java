package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class GUIHome extends BuildingPanel {
	
	List<Gui> guis = new ArrayList<Gui>();
    BufferedImage homeImage;

	public GUIHome( Rectangle2D r, int i, SimCityGui sc) {
		super(r, i, sc);
    	
    	try {
        	homeImage = ImageIO.read(getClass().getResource("Home.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

	
	public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.drawImage(homeImage, 0, 0, null);
        
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
	
	public void addGui(Gui gui) {
		guis.add(gui);
	}
	
	public void displayBuildingPanel() {
		myCity.displayBuildingPanel( this );
	}

}