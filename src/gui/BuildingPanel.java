package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;

public abstract class BuildingPanel extends JPanel implements ActionListener{
	Rectangle2D myRectangle;
	String myName;
	protected SimCityGui myCity;
	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
	
	public BuildingPanel( Rectangle2D r, int i, SimCityGui sc) {
		myRectangle = r;
		myName = "" + i;
		myCity = sc;
		
		if(i == 0) {
			myName = "Stack's Restaurant (1)";
		}
		if(i == 1) {
			myName = "Huang's Restaurant (2)";
		}
		if(i == 2) {
			myName = "Nakamura Restaurant (3)";
		}
		if(i == 3) {
			myName = "Phillips Restaurant (4)";
		}
		if(i == 5) {
			myName = "Tan's Restaurant (6)";
		}
		if(i == 4) {
			myName = "Sheh's Restaurant (5)";
		}
		if(i == 14) {
			myName = "Bank";
		}
		if(i == 12) {
			myName = "Market (1)";
		}
		if(i == 13) {
			myName = "Market (2)";
		}
		if(i == 6) {
			myName = "House (1)";
		}
		if(i == 11) {
			myName = "House (2)";
		}
		if(i == 10) {
			myName = "House (3)";
		}
		if(i == 9) {
			myName = "House (4)";
		}
		if(i == 8) {
			myName = "House (5)";
		}
		if(i == 7) {
			myName = "House (6)";
		}
		
		JLabel j = new JLabel( myName );
		j.setForeground(Color.white);
		add( j );
		

    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
	}
	
	public abstract void paintComponent(Graphics g);
	
	public abstract void addGui(Gui gui);
	
	public abstract void updateGui();
	
	public String getName() {
		return myName;
	}

	public abstract void displayBuildingPanel();

}