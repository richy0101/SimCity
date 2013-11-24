package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class BuildingPanel extends JPanel {
	Rectangle2D myRectangle;
	String myName;
	SimCityGui myCity;
	
	public BuildingPanel( Rectangle2D r, int i, SimCityGui sc ) {
		myRectangle = r;
		myName = "" + i;
		myCity = sc;
		
		setBackground( Color.DARK_GRAY );
		setMinimumSize( new Dimension( 500, 250 ) ); //DO WE NEED MIN/MAX SIZE? WHAT IS PURPOSE?
		setMaximumSize( new Dimension( 500, 250 ) );
		setPreferredSize( new Dimension( 500, 250 ) );
		
		JLabel j = new JLabel( myName );
		add( j );
	}
	
	public String getName() {
		return myName;
	}

	public void displayBuildingPanel() {
		myCity.displayBuildingPanel( this );
	}

}