package city.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import city.PersonAgent;
import city.TransportationRole;
import city.gui.PersonGui.CurrentAction;

public class TransportationGui implements Gui {
	private TransportationRole agent = null;
	private int xPos, yPos;
	private int xDestination, yDestination;

	int TopRow = 85;//105-12;
	int BottomRow = 325+10;
	int LeftCol = 135-15-1;
	int RightCol = 700-8+10;
	
	BufferedImage personLeft;
	BufferedImage personRight;
	BufferedImage personUp;
	BufferedImage personDown;
	private String info;
	
	//outer loop
	int outerTopLane= 83;
	int outerBottomLane= 353;
	int outerLeftLane= 108;
	int outerRightLane= 719;
	
	//inner left (IL) loop
	int ILTopLane= 127;
	int ILBottomLane= 308;
	int ILLeftLane= 152;
	int ILRightLane= 390;
	
	//inner right (IR) loop
	int IRTopLane= 127;
	int IRBottomLane= 308;
	int IRLeftLane= 435;
	int IRRightLane= 674;
	
	int Cross1X = 390, Cross1Y = 83;
	int Cross2X = 390, Cross2Y = 127;
	int Cross4X = 390, Cross4Y = 308;
	int Cross3X = 435, Cross3Y = 127;
	int Cross5X = 435, Cross5Y = 308;
	int Cross6X = 435, Cross6Y = 353;
	public enum CurrentLoop {InnerRight, InnerLeft, Outer};
	public enum CurrentAction {Travelling, Idle, BreakOut, BreakIn, BreakOver};
	CurrentAction currentAction = CurrentAction.Idle;
	public TransportationGui(TransportationRole agent, int startX, int startY, int destX, int destY) {
		this.agent = agent;
		xPos = startX;
		yPos = startY;
		xDestination = destX;
		yDestination = destY;
		
		try {
        	personLeft = ImageIO.read(getClass().getResource("GUICITYPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUICITYPersonRight.png"));
        	personUp = ImageIO.read(getClass().getResource("GUICITYPersonUp.png"));
        	personDown = ImageIO.read(getClass().getResource("GUICITYPersonDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
		
		currentAction = CurrentAction.Travelling;
	}
	
	@Override
	public void updatePosition() {
		if(currentAction == CurrentAction.BreakIn) {
			/**
			 * Outer Loop Logic
			 */
			if ((xPos == outerLeftLane) && (yPos != outerBottomLane)) { //at left, coming down
	            yPos++;
			}
			else if ((yPos == outerBottomLane) && (xPos != outerRightLane)) { //at bottom, going right
	            xPos++;
			}
	        else if ((xPos == outerRightLane) && (yPos != outerTopLane)) {//at right, going up
	            yPos--;
	        }
	        else if ((yPos == outerTopLane) && (xPos != outerLeftLane)) {//at top, going left
	            xPos--;
	        }
			/**
			 * Inner Loop Right Logic
			 */
			if ((xPos == IRLeftLane) && (yPos != IRBottomLane)) { //at left, coming down
	            yPos++;
			}
			else if ((yPos == IRBottomLane) && (xPos != IRRightLane)) { //at bottom, going right
	            xPos++;
			}
	        else if ((xPos == IRRightLane) && (yPos != IRTopLane)) {//at right, going up
	            yPos--;
	        }
	        else if ((yPos == IRTopLane) && (xPos != IRLeftLane)) {//at top, going left
	            xPos--;
	        }
			/**
			 * Inner Loop Left Logic
			 */
			if ((xPos == ILLeftLane) && (yPos != ILBottomLane)) { //at left, coming down
	            yPos++;
			}
			else if ((yPos == ILBottomLane) && (xPos != ILRightLane)) { //at bottom, going right
	            xPos++;
			}
	        else if ((xPos == ILRightLane) && (yPos != ILTopLane)) {//at right, going up
	            yPos--;
	        }
	        else if ((yPos == ILTopLane) && (xPos != ILLeftLane)) {//at top, going left
	            xPos--;
	        }
		}
		else {
			/**
			 * Outer Loop Logic
			 */
			if ((xPos == outerLeftLane) && (yPos != outerBottomLane)) { //at left, coming down
	            yPos++;
			}
			else if ((yPos == outerBottomLane) && (xPos != outerRightLane)) { //at bottom, going right
	            xPos++;
			}
	        else if ((xPos == outerRightLane) && (yPos != outerTopLane)) {//at right, going up
	            yPos--;
	        }
	        else if ((yPos == outerTopLane) && (xPos != outerLeftLane)) {//at top, going left
	            xPos--;
	        }
			/**
			 * Inner Loop Right Logic
			 */
			if ((xPos == IRLeftLane) && (yPos != IRBottomLane)) { //at left, coming down
	            yPos++;
			}
			else if ((yPos == IRBottomLane) && (xPos != IRRightLane)) { //at bottom, going right
	            xPos++;
			}
	        else if ((xPos == IRRightLane) && (yPos != IRTopLane)) {//at right, going up
	            yPos--;
	        }
	        else if ((yPos == IRTopLane) && (xPos != IRLeftLane)) {//at top, going left
	            xPos--;
	        }
			/**
			 * Inner Loop Left Logic
			 */
			if ((xPos == ILLeftLane) && (yPos != ILBottomLane)) { //at left, coming down
	            yPos++;
			}
			else if ((yPos == ILBottomLane) && (xPos != ILRightLane)) { //at bottom, going right
	            xPos++;
			}
	        else if ((xPos == ILRightLane) && (yPos != ILTopLane)) {//at right, going up
	            yPos--;
	        }
	        else if ((yPos == ILTopLane) && (xPos != ILLeftLane)) {//at top, going left
	            xPos--;
	        }
		}
		
//		//System.out.println("Updating Pos.");
//		if ((xPos < xDestination)){ //&& ((yPos<TopRow+20 && yPos>TopRow-20)||(yPos == BottomRow))) {
//			xPos+= 1;
//		}
//		else if (xPos > xDestination) {
//			xPos-= 1;
//		}
//		
//		if (yPos < yDestination) {
//			yPos+= 1;
//		}
//		else if (yPos > yDestination) {
//			yPos-= 1;
//		}
//		
//		if(yPos == yDestination && xPos == xDestination && currentAction == CurrentAction.Travelling) {
//			agent.msgActionComplete();
//			currentAction = CurrentAction.Idle;
//		}
	}

	@Override
	public void draw(Graphics2D g) {
		//System.out.println("Updating Pos.");
		if (xPos < xDestination) {
			g.drawImage(personRight, xPos, yPos, null);
		}
		else if (xPos > xDestination) {
			g.drawImage(personLeft,  xPos, yPos, null);
		}
		else if (yPos < yDestination) {
			g.drawImage(personDown, xPos, yPos, null);
		}
		else if (yPos > yDestination) {
			g.drawImage(personUp, xPos, yPos, null);
		}
		else {
			g.drawImage(personDown, xPos, yPos, null);
		}
		
		info = agent.getPersonAgent().getName() + "(" + agent.getState() + ")";
		g.setColor(Color.magenta);
		g.drawString(info, xPos, yPos);
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}
}