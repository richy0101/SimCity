package city.gui;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


import city.TransportationRole;

import city.helpers.WalkLoopHelper;

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
	
	int Cross1X = 390, Cross1Y = 83; //390
	int Cross2X = 390, Cross2Y = 127;
	int Cross4X = 390, Cross4Y = 308;
	int Cross3X = 435, Cross3Y = 127; //435
	int Cross5X = 435, Cross5Y = 308;
	int Cross6X = 435, Cross6Y = 353;
	public enum Direction {ToTheLeft, ToTheRight, ToTheTop, ToTheBottom, EqualLeft, EqualRight, ItsNotAboutTheMoney, EqualBottom, EqualTop};
	Direction directionX;
	Direction directionY;
	public enum Loop {InnerRight, InnerLeft, Outer};
	Loop currentLoop;
	Loop destinationLoop;
	public enum CurrentAction {Travelling, Idle, 
		BreakOut, BreakIn, BreakOver, BreakInFromTop, BreakInFromBottom, BreakOutFromTop, BreakOutFromBottom
		};
	CurrentAction currentAction;
	public TransportationGui(TransportationRole agent, String startLocation, String destinationLocation) {
		this.agent = agent;
		//System.err.println(WalkLoopHelper.sharedInstance.getCoordinateEvaluator().get(startLocation).xCoordinate + " " + WalkLoopHelper.sharedInstance.getCoordinateEvaluator().get(startLocation).yCoordinate);
		xPos = WalkLoopHelper.sharedInstance.getCoordinateEvaluator().get(startLocation).xCoordinate;
		yPos = WalkLoopHelper.sharedInstance.getCoordinateEvaluator().get(startLocation).yCoordinate;
		xDestination = WalkLoopHelper.sharedInstance.getCoordinateEvaluator().get(destinationLocation).xCoordinate;
		yDestination = WalkLoopHelper.sharedInstance.getCoordinateEvaluator().get(destinationLocation).yCoordinate;
		
		/**
		 * Set starting loop
		 */
		if(WalkLoopHelper.sharedInstance.getloopEvaluator().get(startLocation).contains("Right")) {
			currentLoop = Loop.InnerRight;
		}
		else if(WalkLoopHelper.sharedInstance.getloopEvaluator().get(startLocation).contains("Left")) {
			currentLoop = Loop.InnerLeft;
		}
		else if(WalkLoopHelper.sharedInstance.getloopEvaluator().get(startLocation).contains("Out")) {
			currentLoop = Loop.Outer;
		}
		/**
		 * Set destination loop
		 */
		if(WalkLoopHelper.sharedInstance.getloopEvaluator().get(destinationLocation).contains("Right")) {
			destinationLoop = Loop.InnerRight;
		}
		else if(WalkLoopHelper.sharedInstance.getloopEvaluator().get(destinationLocation).contains("Left")) {
			destinationLoop = Loop.InnerLeft;
		}
		else if(WalkLoopHelper.sharedInstance.getloopEvaluator().get(destinationLocation).contains("Out")) {
			destinationLoop = Loop.Outer;
		}
		/**
		 * Load assets
		 */
		try {
        	personLeft = ImageIO.read(getClass().getResource("GUICITYPersonLeft.png"));
        	personRight = ImageIO.read(getClass().getResource("GUICITYPersonRight.png"));
        	personUp = ImageIO.read(getClass().getResource("GUICITYPersonUp.png"));
        	personDown = ImageIO.read(getClass().getResource("GUICITYPersonDown.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Person assets");
        }
		evaluateNextMove();
	}
	
	@Override
	public void updatePosition() {
		if (currentAction != CurrentAction.Idle) {
			if (xPos == xDestination && yPos == yDestination) {
				agent.msgActionComplete();
				currentAction = CurrentAction.Idle;
				return;
			}
			/*
			if(currentAction == CurrentAction.BreakOut){
				System.out.println("WANT TO BREAK OUT");
			}
			
			if(currentAction == CurrentAction.BreakOutFromTop){
				System.out.println("WANT TO BREAK OUT FROM TOP");
			}
			
			if(currentAction == CurrentAction.BreakOutFromBottom){
				System.out.println("WANT TO BREAK OUT FROM BOTTOM");
			}*/
			
			/**
			 * Breaking Out Block
			 */
			if (currentAction == CurrentAction.BreakOutFromTop && !doneBreaking(Cross1X, Cross1Y)) {
				yPos--;
				return;
			}
			else if(currentAction == CurrentAction.BreakOutFromTop && doneBreaking(Cross1X, Cross1Y)){
				//System.out.println("broken out");
				currentLoop = Loop.Outer;
				evaluateNextMove();
				return;
			}
			if (currentAction == CurrentAction.BreakOutFromBottom && !doneBreaking(Cross6X, Cross6Y)) {
				yPos++;
				return;
			}
			else if(currentAction == CurrentAction.BreakOutFromBottom && doneBreaking(Cross6X, Cross6Y)) {
				//System.out.println("broken out");
				currentLoop = Loop.Outer;
				evaluateNextMove();
				return;
			}
			if(currentAction == CurrentAction.BreakOut && (xPos == Cross2X && yPos == Cross2Y)) {
				currentAction = CurrentAction.BreakOutFromTop;
				return;
			}
			else if (currentAction == CurrentAction.BreakOut && (xPos == Cross5X && yPos == Cross5Y)) {
				currentAction = CurrentAction.BreakOutFromBottom;
				return;
			}
			/**
			 * End of Breaking Out Block
			 */
			/**
			 * Breaking in Block
			 */
			if (currentAction == CurrentAction.BreakInFromTop && !doneBreaking(Cross2X, Cross2Y)) {
				yPos++;
			}
			else if(currentAction == CurrentAction.BreakInFromTop && doneBreaking(Cross2X, Cross2Y)) {
				//System.out.println("broken out");
				currentLoop = Loop.InnerLeft;
				evaluateNextMove();
				return;
			}
			if (currentAction == CurrentAction.BreakInFromBottom && !doneBreaking(Cross5X, Cross5Y)) {
				yPos--;
			}
			else if(currentAction == CurrentAction.BreakInFromBottom && doneBreaking(Cross5X, Cross5Y)) {
				//System.out.println("broken in to 5");
				currentLoop = Loop.InnerRight;
				evaluateNextMove();
				return;
			}
			if(currentAction == CurrentAction.BreakIn && (xPos == Cross1X && yPos == Cross1Y)) {
				currentAction = CurrentAction.BreakInFromTop;
				return;
			}
			else if (currentAction == CurrentAction.BreakIn && (xPos == Cross6X && yPos == Cross6Y)) {
				currentAction = CurrentAction.BreakInFromBottom;
				return;
			}
			/**
			 * End of Breaking In block
			 */
			ContinueLooping();
		}
			
	}
	private boolean doneBreaking(int x, int y) {
		if (xPos == x && yPos == y) {
			System.out.println("DONE BREAKING");
			return true;
		}
		else {
			return false;
		}
	}
	private void ContinueLooping() {
		/**
		 * General Logic
		 */
		if (directionX == Direction.ToTheLeft && directionY == Direction.ToTheBottom && xPos <= 420) {
			loopCounterClockwise();
		}
		else if (directionX == Direction.ToTheLeft && directionY == Direction.ToTheBottom && xPos >= 420) {
			loopClockwise();
		}
		else if (directionX == Direction.ToTheLeft && directionY == Direction.ToTheTop && xPos <= 420) {
			loopClockwise();
		}
		else if (directionX == Direction.ToTheLeft && directionY == Direction.ToTheTop && xPos >= 420) {
			loopCounterClockwise();
		}
		else if (directionX == Direction.ToTheRight && directionY == Direction.ToTheBottom && xPos <= 420) {
			loopCounterClockwise();
		}
		else if (directionX == Direction.ToTheRight && directionY == Direction.ToTheBottom && xPos >= 420) {
			loopClockwise();
		}
		else if (directionX == Direction.ToTheRight && directionY == Direction.ToTheTop && xPos <= 420) {
			loopClockwise();
		}
		else if (directionX == Direction.ToTheRight && directionY == Direction.ToTheTop && xPos >= 420) {
			loopCounterClockwise();
		}
		/**
		 * Top to Down cases from Left and right lanes , xPos = xDestination
		 */
		else if (directionX == Direction.EqualRight && directionY == Direction.ToTheBottom) { //From right side to bottom
			loopClockwise();
		}
		else if (directionX == Direction.EqualLeft && directionY == Direction.ToTheBottom) { //From Left side To bottom
			loopCounterClockwise();
		}
		else if (directionX == Direction.EqualRight && directionY == Direction.ToTheTop) { //From right side to Top
			loopCounterClockwise();
		}
		else if (directionX == Direction.EqualLeft && directionY == Direction.ToTheTop) { //From Left side To Top
			loopClockwise();
		}
		/**
		 * yPos = yDestination
		 */
		else if (directionY == Direction.EqualTop && directionX == Direction.ToTheLeft) { //From Top to Left 
			loopCounterClockwise();
		}
		else if (directionY == Direction.EqualTop && directionX == Direction.ToTheRight) { //From Top to Right
			loopClockwise();
		}
		else if (directionY == Direction.EqualBottom && directionX == Direction.ToTheLeft) { //From bottom to left
			loopClockwise();
		}
		else if (directionY == Direction.EqualBottom && directionX == Direction.ToTheRight) { //From bottom to right
			loopCounterClockwise();
		}
	}
	private void loopClockwise() {
		/**
		 * Outer Loop Logic
		 */
		if (currentLoop == Loop.Outer) {
			if ((xPos == outerLeftLane) && (yPos != outerTopLane)) { //at left, going up
	            yPos--;
			}
			else if ((yPos == outerBottomLane) && (xPos != outerLeftLane)) { //at bottom, going left
	            xPos--;
			}
	        else if ((xPos == outerRightLane) && (yPos != outerBottomLane)) {//at right, going down
	            yPos++;
	        }
	        else if ((yPos == outerTopLane) && (xPos != outerRightLane)) {//at top, going right
	            xPos++;
	        }
		}
		/**
		 * Inner Loop Right Logic
		 */
		else if (currentLoop == Loop.InnerRight) {
			if ((xPos == IRLeftLane) && (yPos != IRTopLane)) { //at left, going up
	            yPos--;
			}
			else if ((yPos == IRBottomLane) && (xPos != IRLeftLane)) { //at bottom, going left
	            xPos--;
			}
	        else if ((xPos == IRRightLane) && (yPos != IRBottomLane)) {//at right, going down
	            yPos++;
	        }
	        else if ((yPos == IRTopLane) && (xPos != IRRightLane)) {//at top, going right
	            xPos++;
	        }
		}
		/**
		 * Inner Loop Left Logic
		 */
		else if (currentLoop == Loop.InnerLeft) {
			if ((xPos == ILLeftLane) && (yPos != ILTopLane)) { //at left, going up
	            yPos--;
			}
			else if ((yPos == ILBottomLane) && (xPos != ILLeftLane)) { //at bottom, going left
	            xPos--;
			}
	        else if ((xPos == ILRightLane) && (yPos != ILBottomLane)) {//at right, going down
	            yPos++;
	        }
	        else if ((yPos == ILTopLane) && (xPos != ILRightLane)) {//at top, going right
	            xPos++;
	        }
		}
	}
	
	private void loopCounterClockwise() {
		/**
		 * Outer Loop Logic
		 */
		if (currentLoop == Loop.Outer) {
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
		}
		/**
		 * Inner Loop Right Logic
		 */
		else if (currentLoop == Loop.InnerRight) {
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
		}
		/**
		 * Inner Loop Left Logic
		 */
		else if (currentLoop == Loop.InnerLeft) {
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
	}
	private void evaluateNextMove() {
		/**
		 * Update Directions block
		 */
		if (xPos < xDestination) {
			directionX = Direction.ToTheRight;
		}
		else if (xPos > xDestination) {
			directionX = Direction.ToTheLeft;
		}
		else if (xPos == xDestination && xPos >= 420) {
			directionX = Direction.EqualRight;
		}
		else if (xPos == xDestination && xPos < 420) {
			directionX = Direction.EqualLeft;
		}
		if (yPos < yDestination) {
			directionY = Direction.ToTheBottom;
		}
		else if (yPos > yDestination) {
			directionY = Direction.ToTheTop;
		}
		else if (yPos == yDestination && yPos >= 203) {
			directionY = Direction.EqualBottom;
		}
		else if (yPos == yDestination && yPos < 203) {
			directionY = Direction.EqualTop;
		}
		if(currentLoop == destinationLoop) {
			currentAction = CurrentAction.Travelling;
			ContinueLooping();
		}
		else if (destinationLoop == Loop.InnerLeft || destinationLoop == Loop.InnerRight) {
			currentAction = CurrentAction.BreakIn;
		}
		else if (destinationLoop == Loop.Outer) {
			currentAction = CurrentAction.BreakOut;
		}
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