package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;

public abstract class BuildingPanel extends JPanel implements ActionListener{
	Rectangle2D myRectangle;
	String myName;
	SimCityGui myCity;
	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
	
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
	}
	
	public abstract void paintComponent(Graphics g);
	
	public abstract void addGui(Gui gui);
	
	public String getName() {
		return myName;
	}

	public abstract void displayBuildingPanel();

}