package restaurant.gui;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {

    private final int WINDOWX = 827;
    private final int WINDOWY = 406;
    BufferedImage restaurantImage;
    private final int RECTXPOS = 200; 
    private final int RECTYPOS = 250; 
    private final int RECTX = 50; 
    private final int RECTY = 50; 
    private Image bufferImage;
    private Dimension bufferSize;
    public Timer timer = new Timer(20,this);

    private List<Gui> guis = new ArrayList<Gui>();

    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
        
        bufferSize = this.getSize();
 
    	//Timer timer = new Timer(20, this );
    	timer.start();
    	
    	try {
        	restaurantImage = ImageIO.read(getClass().getResource("RestaurantImage.png"));
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

        //Clear the screen by painting a rectangle the size of the frame
        //g2.setColor(getBackground());
        //g2.fillRect(0, 0, WINDOWX, WINDOWY );
        g2.drawImage(restaurantImage, 0, 0, null);

        /*
        //tables
        g2.setColor(Color.ORANGE);
        g2.fillRect(RECTXPOS, RECTYPOS, RECTX, RECTY);
        g2.setColor(Color.ORANGE);
        g2.fillRect(RECTXPOS+60, RECTYPOS, RECTX, RECTY);
        g2.setColor(Color.ORANGE);
        g2.fillRect(RECTXPOS+120, RECTYPOS, RECTX, RECTY);
        //Cook Area
        g.setColor(Color.BLACK);
        g.fillRect(240, 50, 50, 30);
        g2.setColor(Color.PINK);
        g2.fillRect(290, 50, 50, 30);
        //Cashier
        g2.setColor(Color.RED);
        g2.fillRect(RECTXPOS-70, RECTYPOS-200, RECTX-10, RECTY-20);
        //Kitchen
        g2.setColor(Color.CYAN);
        g2.fillRect(RECTXPOS+140, RECTYPOS-245, RECTX-20, RECTY+25);
        //Customer waiting area
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 180, RECTX+20, RECTY+20);
        */
        synchronized(this.guis){
	        for(Gui gui : guis) {
	            if (gui.isPresent()) {
	                gui.updatePosition();
	            }
	        }
        }
        synchronized(this.guis){
	        for(Gui gui : guis) {
	            if (gui.isPresent()) {
	                gui.draw(g2);
	            }
	        }
        }
    }

    public void updateGui() {
        for(Gui gui : guis) {
            if (gui.isPresent())
                gui.updatePosition();
        }
	}
    
    public void addGui(CustomerGui gui) {
    	synchronized(this.guis){
    		guis.add(gui);
    	}
    }

    public void addGui(WaiterGui gui) {
    	synchronized(this.guis){
    		guis.add(gui);
    	}
    }
    
    public void addGui(CookGui gui) {
    	synchronized(this.guis){
    		guis.add(gui);
    	}
    }
    public void removeGui(Gui gui) {
    	synchronized(this.guis){
    		guis.remove(gui);
    	}
    }
}
