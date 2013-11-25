package city.gui;

import gui.Gui;
import city.BusAgent;
import city.helpers.Directory;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BusGui implements Gui {
	/**
	 * Data
	 */
	String destination;
	//start at top left
	int xPos = 110;
	int yPos = 73;
	
	int xDestination = 20;
	int yDestination = 20;
	
	int TopRow = 105;
	int BottomRow = 325;
	int LeftCol = 135;
	int RightCol = 700;
	
	private BusAgent agent = null;
	
	BufferedImage busUpImage;
	BufferedImage busDownImage;
	BufferedImage busLeftImage;
	BufferedImage busRightImage;
	
	/**
	 * Received Messages/Actions
	 */
	public void DoGoTo(String place){
		destination = place;
		
		if (destination.equals("StackRestaurant")){
			//sets xDestination & yDestination
			//need to figure out how to travel on road
			System.out.println("carGui: in DoGoTo function, simulating car running.");
			System.out.println("carGui: approaching destination.");
			//agent.msgAtDestination(); //hack
			
		}
	}
	
	public void DoParkBus(){
		System.out.println("busGui: Parking bus.");
		//may not be necessary for bus system
		//send msg to animation panel to remove gui from the list
	}

	/**
	 * Utilities
	 */
	/*
	public int getX(String Destination){
		return Directory.sharedInstance().locationDirectory.get(Destination).xCoordinate;
	}
	
	public int getY(String Destination){
		return Directory.sharedInstance().locationDirectory.get(Destination).yCoordinate;
	}*/
	
	public BusGui(BusAgent agent) {
        this.agent = agent;
        
        try {
        	busLeftImage = ImageIO.read(getClass().getResource("busLeft.png"));
        	busRightImage = ImageIO.read(getClass().getResource("busRight.png"));
        	busUpImage = ImageIO.read(getClass().getResource("busUp.png"));
        	busDownImage = ImageIO.read(getClass().getResource("busDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ busGui");
        }
    }
	
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
		//Counter Clockwise motion
		if ((xPos == LeftCol) && (yPos != BottomRow)) //at left, coming down
            yPos++;
		else if ((yPos == BottomRow) && (xPos != RightCol)) //at bottom, going right
            xPos++;
        else if ((xPos == RightCol) && (yPos != TopRow)) //at right, going up
            yPos--;
        else if ((yPos == TopRow) && (xPos != LeftCol)) //at top, going left
            xPos--;
		
		if((xPos==171) && (yPos==361))
			agent.msgAtStopOne();
		else if((xPos==110) && (yPos==73))
			agent.msgAtStopTwo();
		else if((xPos==610) && (yPos==73))
			agent.msgAtStopThree();
		else if((xPos==675) && (yPos==356))
			agent.msgAtStopFour();
		
		
		//account for roads

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
		//counter clockwise
		if(yPos==BottomRow)
			g.drawImage(busRightImage, xPos, yPos, null);
		else if (xPos==RightCol)
			g.drawImage(busUpImage, xPos, yPos, null);
		else if (yPos==TopRow)
			g.drawImage(busLeftImage, xPos, yPos, null);
		else if (xPos==LeftCol)
			g.drawImage(busDownImage, xPos, yPos, null);

	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	

}