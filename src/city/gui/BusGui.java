package city.gui;

import gui.Gui;

import java.awt.Graphics2D;

import city.BusAgent;
import city.helpers.Coordinate;
import city.helpers.Directory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class BusGui implements Gui {
	/**
	 * Data
	 */
	
	String destination;
	int xPos = 10;
	int yPos = 10;
	int xDestination = 20;
	int yDestination = 20;
	BufferedImage busImage;
	
	private BusAgent agent = null;
	
	/**
	 * Received Messages/Actions
	 */
	public void DoGoTo(String place){
		destination = place;
				
			xDestination = getX(destination);
			yDestination = getY(destination);
			
			/*
			System.out.println("carGui: in DoGoTo function, simulating car running.");
			System.out.println("carGui: approaching destination.");
			*/
			
	}
	
	public void DoParkBus(){ //might need in case bus ever stops operating eg at night
		System.out.println("busGui: Parking bus.");
		//send msg to animation panel to remove gui from the list
	}

	/**
	 * Utilities
	 */
	
	public int getX(String Destination){
		return Directory.sharedInstance().locationDirectory.get(Destination).xCoordinate;
	}
	
	public int getY(String Destination){
		return Directory.sharedInstance().locationDirectory.get(Destination).yCoordinate;
	}
	
	public BusGui(BusAgent agent) {
        this.agent = agent;
        
        //check the x/y coord on the grid to know which side it's on and outputs appropriate car image.
        try {
        	busImage = ImageIO.read(getClass().getResource("busLeft.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ busGui");
        }
    }
	
	
	@Override
	public void updatePosition() {		
		
		//if not at proper position, then keep driving clockwise/ counter clockwise;
		//if at one of 4 corners, update carImage accordingly to down right left or up
		//once reached proper coords:
		agent.msgAtDestination();

	}

	@Override
	public void draw(Graphics2D g) { //need to account for x and y position
		 g.drawImage(busImage, 0, 0, null);

	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	

}

