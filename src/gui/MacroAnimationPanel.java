package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import city.BusAgent;
import city.gui.BusGui;
import city.helpers.Clock;

public class MacroAnimationPanel extends JPanel implements ActionListener, MouseListener {
       
	BufferedImage cityImage;
	BufferedImage cityImageTop;
	BufferedImage cityImageNight;
	BufferedImage cityImageNightTop;

	private final int WINDOWX = 835;
    private final int WINDOWY = 406;
   
    private final int DELAY = 5;

    private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
    ArrayList<Building> buildings = new ArrayList<Building>();
    BusAgent bus;
    BusGui busGui;
    
    //SimCityPanel
    protected SimCityGui city;
    protected Color background;
    protected Timer timer;

    public MacroAnimationPanel(SimCityGui city) {
    	this.city = city;
    	
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.LIGHT_GRAY);
        
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
    	//buildings = new ArrayList<Building>();

    //RESTAURANT
    	Building stackRestaurant = new Building(0, 240, 105, 120);
    	stackRestaurant.setName("StackRestaurant");
    	buildings.add(stackRestaurant);
    	
    	Building huangRestaurant = new Building(183, 6, 134, 80);
    	huangRestaurant.setName("HuangRestaurant");
    	buildings.add(huangRestaurant);
    	
    	Building nakamuraRestaurant = new Building(321, 6, 138, 77);
    	nakamuraRestaurant.setName("NakamuraRestaurant");
    	buildings.add(nakamuraRestaurant);
    	
    	Building phillipsRestaurant = new Building(729, 263, 107, 93);
    	phillipsRestaurant.setName("PhillipsRestaurant");
    	buildings.add(phillipsRestaurant);
    	
    	Building shehRestaurant = new Building(523, 326, 142, 74);
    	shehRestaurant.setName("ShehRestaurant");
    	buildings.add(shehRestaurant);
    	
    	Building tanRestaurant = new Building(185, 325, 158, 75);
    	tanRestaurant.setName("TanRestaurant");
    	buildings.add(tanRestaurant);
    
    //HOUSES
    	Building house1 = new Building(326, 196, 129, 99);
    	house1.setName("House1");
    	buildings.add(house1);
    	
    	Building house2 = new Building(330, 132, 145, 69);
    	house2.setName("House2");
    	buildings.add(house2);
    	
    	Building house3 = new Building(473, 133, 85, 73);
    	house3.setName("House3");
    	buildings.add(house3);
    	
    	Building house4 = new Building(564, 133, 104, 41);
    	house4.setName("House4");
    	buildings.add(house4);
    	
    	Building house5 = new Building(564, 178, 104, 117);
    	house5.setName("House5");
    	buildings.add(house5);

    	Building house6 = new Building(458, 210, 107, 86);
    	house6.setName("House6");
    	buildings.add(house6);
    	
    //MARKET
    	Building market1 = new Building(459, 8, 200, 70);
    	market1.setName("Market1");
    	buildings.add(market1);
    	
    	Building market2 = new Building(347, 329, 175, 71);
    	market2.setName("Market2");
    	buildings.add(market2);
    	
    //APARTMENT
    	
    //BANK 
    	Building bank = new Building(0, 0, 104, 214);
    	bank.setName("Bank");
    	buildings.add(bank);

    	addMouseListener(this);
    	
    	//CITY BACKGROUND
    	try {
        	cityImage = ImageIO.read(getClass().getResource("SIMCITYBOTTOM.png"));
        	cityImageTop = ImageIO.read(getClass().getResource("SIMCITYTOP.png"));
        	cityImageNight = ImageIO.read(getClass().getResource("SIMCITYBOTTOMNIGHT.png"));
        	cityImageNightTop = ImageIO.read(getClass().getResource("SIMCITYTOPNIGHT.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
		
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
		this.repaint();
		synchronized(buildings) {
			for(Building b : this.buildings) {
				if(b.hasBuildingPanel()) {
					b.getBuildingPanel().updateGui();
				}
			}
		}
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        
        //PLACED ONTOP FOR BUILDING REFERENCE PURPOSES
        
	    synchronized(buildings) {
	        for(int i = 0; i < buildings.size(); i++) {
	        	Building b = buildings.get(i);
	        	g2.fill(b);
	        }
        }
        
//        if(Clock.sharedInstance().getTime() < 10000) 
       	g2.drawImage(cityImage, 0, 0, null);
//        else 
//        	g2.drawImage(cityImageNight, 0, 0, null);
        
        synchronized(guis) {
	        for(Gui gui : guis) {
	            if (gui.isPresent()) {
	                gui.updatePosition();
	            }
	        }
        }

        synchronized(guis) {
	        for(Gui gui : guis) {
	            if (gui.isPresent()) {
	                gui.draw(g2);
	            }
	        }
        }
        
        //moveComponents();
        //drawComponents(g);
        
        //if(Clock.sharedInstance().getTime() < 10000) 
        	g2.drawImage(cityImageTop, 0, 0, null);
        //else 
        	//g2.drawImage(cityImageNightTop, 0, 0, null);
           
    }
    
    public ArrayList<Building> getBuildings() {
    	return buildings;
    }

    public void addGui(Gui gui) {
        guis.add(gui);
    }

    //if click on building, then building displayed
	public void mouseClicked(MouseEvent e) {
		synchronized(buildings) {
			for(int i = 0; i < buildings.size(); i++) {
				Building b = buildings.get(i);
				if(b.contains(e.getX(), e.getY())) {
					b.displayBuilding();
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void removeGui(Gui gui) {
		guis.remove(gui);
	}
}
