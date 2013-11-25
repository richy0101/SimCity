package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public abstract class BuildingPanel extends JPanel implements ActionListener{
	Rectangle2D myRectangle;
	String myName;
	SimCityGui myCity;
	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
    private final int DELAY = 10;
	
	public BuildingPanel( Rectangle2D r, int i, SimCityGui sc) {
		myRectangle = r;
		myName = "" + i;
		myCity = sc;
		
//		setBackground(Color.BLUE);
		//setMinimumSize( new Dimension( 500, 250 ) ); //DO WE NEED MIN/MAX SIZE? WHAT IS PURPOSE?
		//setMaximumSize( new Dimension( 500, 250 ) );
		//setPreferredSize( new Dimension( 500, 250 ) );
		
		JLabel j = new JLabel( myName );
		add( j );
		

    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
 
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
	}
	
	public abstract void paintComponent(Graphics g);
	
	public String getName() {
		return myName;
	}

	public void displayBuildingPanel() {
		myCity.displayBuildingPanel( this );
	}

}