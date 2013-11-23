package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MacroAnimationPanel extends JPanel implements ActionListener, MouseListener {
       
	BufferedImage cityImage;
	BufferedImage cityImageTop;
	BufferedImage cityImageNight;
	BufferedImage cityImageNightTop;

	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
   
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();
    ArrayList<Building> buildings;

    public MacroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.green);
        
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
    	buildings = new ArrayList<Building>();
    	
    	//Adding building blocks
    	Building b = new Building(0, 0, 10, 10);
    	buildings.add(b);
    	
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
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        
        //paint all building squares black
        for(int i = 0; i < buildings.size(); i++) {
        	Building b = buildings.get(i);
        	g2.fill(b);
        }

        g2.drawImage(cityImage, 0, 0, null);
        
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
        
        g2.drawImage(cityImageTop, 0, 0, null);
        
    }
    
    public ArrayList<Building> getBuildings() {
    	return buildings;
    }

    public void addGui(Gui gui) {
        guis.add(gui);
    }

    //if click on building, then building displayed
	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i < buildings.size(); i++) {
			Building b= buildings.get(i);
			if(b.contains(e.getX(), e.getY())) {
				b.displayBuilding();
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
}
