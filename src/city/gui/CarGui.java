package city.gui;

import gui.Gui;
import city.CarAgent;
import city.helpers.Coordinate;
import city.helpers.Directory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class CarGui implements Gui {
	/**
	 * Data
	 */
	
	String destination;
	int xPos = 10;
	int yPos = 10;
	int xDestination = 20;
	int yDestination = 20;
	int TopRow = 10;
	int BottomRow = 10;
	int LeftCol = 10;
	int RightCol = 10;
	BufferedImage carUpImage;
	BufferedImage carDownImage;
	BufferedImage carLeftImage;
	BufferedImage carRightImage;
	boolean driveRequest= false;
	boolean isPresent=false;
	
	private CarAgent agent = null;
	
	public CarGui(CarAgent agent, String currentLocation) {
        this.agent = agent;
        isPresent = true;
        System.out.println("currentloc is "+ currentLocation);
        System.out.println("corresponding xCoord is "+ Directory.sharedInstance().locationDirectory.get(currentLocation).xCoordinate);
        System.out.println("corresponding yCoord is "+ Directory.sharedInstance().locationDirectory.get(currentLocation).yCoordinate);
        xPos = 135;//Directory.sharedInstance().locationDirectory.get(currentLocation).xCoordinate;
		yPos = 200;//Directory.sharedInstance().locationDirectory.get(currentLocation).yCoordinate;

        //check the x/y coord on the grid to know which side it's on and outputs appropriate car image.
        try {
        	carLeftImage = ImageIO.read(getClass().getResource("carLeft.png"));
        	carRightImage = ImageIO.read(getClass().getResource("carRight.png"));
        	carUpImage = ImageIO.read(getClass().getResource("carUp.png"));
        	carDownImage = ImageIO.read(getClass().getResource("carDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ carGui");
        }
    }
	
	/**
	 * Received Messages/Actions
	 */
	public void DoGoTo(String place){
		driveRequest = true;
		isPresent = true;
		destination = place;
				
			xDestination = getX(destination);
			yDestination = getY(destination);
			
			/*
			System.out.println("carGui: in DoGoTo function, simulating car running.");
			System.out.println("carGui: approaching destination.");
			*/
			
	}
	
	public void DoParkCar(){
		driveRequest = false;
		isPresent = false;
		System.out.println("carGui: Parking car underground.");
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
	
	
	@Override
	public void updatePosition() {		
		
		if(driveRequest){
		//car going Clockwise
		if ((xPos == LeftCol) && (yPos != BottomRow)) //at left, going up
            yPos--;
		else if ((yPos == TopRow) && (xPos != LeftCol)) //at top, going right
            xPos++;
		else if ((xPos == RightCol) && (yPos != TopRow)) //at right, going down
            yPos++;
		else if ((yPos == BottomRow) && (xPos != RightCol)) //at bottom, going left
            xPos--;
		
		if((xPos== getX(destination)) && (yPos==getY(destination))) //leftcol
			agent.msgAtDestination();
		}
		
		/*
		 * Likely that getX & getY of destination won't be accurate on each side of the map. One way to do this is to use a range.
		 * Alternatively, it might be better to just map it out.
		 */
		

	}

	@Override
	public void draw(Graphics2D g) { //need to account for x and y position
		//clockwise
		if(isPresent){
		
		if (xPos>0){
			g.drawImage(carUpImage, xPos, yPos, null);
		}
			
		if (yPos==TopRow)
			g.drawImage(carRightImage, xPos, yPos, null);
		else if (xPos==RightCol)
			g.drawImage(carDownImage, xPos, yPos, null);
		if(yPos==BottomRow)
			g.drawImage(carLeftImage, xPos, yPos, null);
		else if (xPos==LeftCol)
			g.drawImage(carUpImage, xPos, yPos, null);

		}
	}

	public void setPresentFalse() {
		isPresent = false;
	}
	public void setPresentTrue() {
		isPresent = true;
	}
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return isPresent;
	}
	/*
	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}*/
	

}
