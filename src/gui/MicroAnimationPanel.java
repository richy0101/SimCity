package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
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
   
    BufferedImage restaurantImage;
   
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();
    
    //public HashMap<String, CityCard> cards = new HashMap<String, CityCard>();
    
    public MicroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
 
        //addMouseListener(this);
        
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
    	try {
        	restaurantImage = ImageIO.read(getClass().getResource("stackRestaurant.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
        /*
    	cards.put("null", new CityCard(this, Color.RED));
    	cards.put("House1", new CityCard(this, Color.RED));
        	
       	CardLayout layout = new CardLayout();
       	this.setLayout(layout);
       	for(String key: cards.keySet()) {
       		this.add(cards.get(key), key);
       	}
        	
       	layout.show(this, "null"); 	
       	*/
    }
        
    /*
    private void add(CityCard cityCard, String key) {
		// TODO Auto-generated method stub
		
	}

    
	public boolean addView(CityCard panel, String key) {
    	if(cards.containsKey(key))
    		return false;
    	cards.put(key, panel);
    	this.add(cards.get(key), key);
    	return true;
    }
    
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

}
