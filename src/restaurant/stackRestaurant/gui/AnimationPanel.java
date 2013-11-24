package restaurant.stackRestaurant.gui;

import gui.Gui;

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

import restaurant.stackRestaurant.helpers.Menu;
import restaurant.stackRestaurant.helpers.TableList;

@SuppressWarnings("serial")
public class AnimationPanel extends JPanel implements ActionListener {

	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
    BufferedImage restaurantImage;
    private final int DELAY = 10;
    private List<Gui> guis = new ArrayList<Gui>();
    private static AnimationPanel sharedInstance = null;
    
    private AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
 
    	Timer timer = new Timer(DELAY, this );
    	timer.start();
    	
    	try {
        	restaurantImage = ImageIO.read(getClass().getResource("stackRestaurant.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
    	
    }
    
    public static AnimationPanel sharedInstance() {
    	if(sharedInstance == null) {
    		sharedInstance = new AnimationPanel();
    	}
    	return sharedInstance;
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.drawImage(restaurantImage, 0, 0, null);

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
}
