package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import restaurant.Restaurant;

public class MicroAnimationPanel extends JPanel implements ActionListener/*, MouseListener*/ {
	private final int WINDOWX = 835;
    private final int WINDOWY = 400;
    
	Rectangle2D myRectangle;
	String myName;
	SimCityGui myCity;
   
    BufferedImage restaurantImage;
   
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();
    
    public HashMap<String, CityCard> cards = new HashMap<String, CityCard>();
  
    public MicroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.BLUE);
 
        //addMouseListener(this);
        
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
    	/*
    	try { //STACK RESTAURANT BACKGROUND
        	restaurantImage = ImageIO.read(getClass().getResource("stackRestaurant.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        } 
        */
    }
    
	public MicroAnimationPanel( Rectangle2D r, int i, SimCityGui sc ) {
		myRectangle = r;
		myName = "" + i;
		myCity = sc;
		
		setBackground( Color.RED );
		setMinimumSize( new Dimension( 500, 250 ) ); //DO WE NEED MIN/MAX SIZE? WHAT IS PURPOSE?
		setMaximumSize( new Dimension( 500, 250 ) );
		setPreferredSize( new Dimension( 500, 250 ) );
		
		JLabel j = new JLabel( myName );
		add( j );
	}
  
	public boolean addView(CityCard panel, String key) {
    	if(cards.containsKey(key))
    		return false;
    	cards.put(key, panel);
    	this.add(cards.get(key), key);
    	return true;
    }
	
	/*
    public void setView(String key) {
    	if (cards.containsKey(key)){ 
    		layout.show(this,key);
    	}
    }
    
    public boolean contains() {
    	for(CityComponent c: statics) {
    		if(c.contains(arg0.getX(), arg0.getY())) {
    			city.view.setView(c.ID);
    		}
    	}
    }
    */

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        //g2.drawImage(restaurantImage, 0, 0, null);
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
    
	public String getName() {
		return myName;
	}

	public void displayBuildingPanel() {
		myCity.displayBuildingPanel( this );
		
	}
	
	public void displayMicroAnimationPanel() {
		myCity.displayMicroAnimationPanel( this ); 
	}
}
