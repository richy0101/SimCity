package city.gui;

import gui.Gui;
import city.CarAgent;
import city.helpers.Coordinate;
import city.helpers.Directory;
import city.helpers.DrivewayHelper;

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
	int xDestination;// = 20;
	int yDestination;// = 20;
	int TopRow = 10;
	int BottomRow = 10;
	int LeftCol = 10;
	int RightCol = 10;
	int topTopLane = 93;
	int topBottomLane = 110;
	int bottomTopLane = 320;
	int bottomBottomLane = 335;
	int midLeftLane = 400;
	int midRightLane = 418;
	int leftLeftLane = 119;
	int leftRightLane = 136;
	int rightLeftLane = 685;
	int rightRightLane = 702;
	BufferedImage carUpImage;
	BufferedImage carDownImage;
	BufferedImage carLeftImage;
	BufferedImage carRightImage;
	boolean driveRequest= false;
	boolean parkRequest= true;
	boolean isPresent=false;
	
	private CarAgent agent = null;
	
	public CarGui(CarAgent agent, String currentLocation) {
        this.agent = agent;
        isPresent = true;
        System.out.println("currentloc is "+ currentLocation);
        //System.out.println("corresponding xCoord is "+ Directory.sharedInstance().locationDirectory.get(currentLocation).xCoordinate);
        System.out.println("corresponding xCoord is "+ getX(currentLocation));
        System.out.println("corresponding yCoord is "+ getY(currentLocation));
        xPos = getX(currentLocation);
		yPos = getY(currentLocation); //Directory.sharedInstance().locationDirectory.get(currentLocation).yCoordinate;

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
		Directory.sharedInstance().getCityGui().getMacroAnimationPanel().addGui(this);

    }
	
	/**
	 * Received Messages/Actions
	 */
	public void DoGoTo(String place){
		isPresent = true;
		destination = place;
		driveRequest = true;
	
		if(driveRequest){
			System.out.println("all good here");}
		
			xDestination = getX(destination);
			yDestination = getY(destination);
			
			System.out.println("destination ="+place+" "+xDestination+" "+yDestination);

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
		return DrivewayHelper.sharedInstance().locationDirectory.get(Destination).xCoordinate;
	}
	
	public int getY(String Destination){
		return DrivewayHelper.sharedInstance().locationDirectory.get(Destination).yCoordinate;
	}
	
	
	@Override
	public void updatePosition() {		
		if(driveRequest==true){//(parkRequest)){
		//car going Clockwise

		if ((xPos == leftRightLane) && (yPos != topBottomLane)) //at left, going up
            yPos--;
		else if ((yPos == topBottomLane) && (xPos != rightLeftLane)) //at top, going right
            xPos++;
		else if ((xPos == rightLeftLane) && (yPos != bottomTopLane)) //at right, going down
            yPos++;
		else if ((yPos == bottomTopLane) && (xPos != leftRightLane)) //at bottom, going left
            xPos--;
		
		
		/*
			if a car is in the leftRightLane & not the bottomTopLane,
			yPos-- to go up
			if a car is in the topBottom lane & not the leftRightLane, 
			xPos++ to go right //at intersection force to stop and then make him turn right if (destination is in Westside)
			if a car is in the rightLeftLane & not the 
		*/	
		}
			//agent.msgAtDestination();
		if((xPos== getX(destination)) && (yPos==getY(destination))) //leftcol
			agent.msgAtDestination();
		
		
		/*
		 * Likely that getX & getY of destination won't be accurate on each side of the map. One way to do this is to use a range.
		 * Alternatively, it might be better to just map it out.
		 */
		

	}

	@Override
	public void draw(Graphics2D g) { //need to account for x and y position
		//clockwise
		if(isPresent){
			
			//this is only for inner loop. need to make it work for entire city.
		if (yPos==topBottomLane)
			g.drawImage(carRightImage, xPos, yPos, null);
		else if (xPos==rightLeftLane)
			g.drawImage(carDownImage, xPos, yPos, null);
		if(yPos==bottomTopLane)
			g.drawImage(carLeftImage, xPos, yPos, null);
		else if (xPos==leftRightLane)
			g.drawImage(carUpImage, xPos, yPos, null);

		}
		
		//if (xPos>0){ //to deal with any cars that spawn off the road
		//	g.drawImage(carRightImage, xPos, yPos, null);
		//}
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
