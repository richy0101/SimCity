package city.gui;

import gui.Gui;
import city.CarAgent;

import java.awt.Color;
import java.awt.Graphics2D;

public class CarGui implements Gui {
	/**
	 * Data
	 */
	String destination;
	int xPos = 10;
	int yPos = 10;
	int xDestination = 20;
	int yDestination = 20;
	
	private CarAgent agent = null;
	
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
			agent.msgAtDestination(); //hack
			
		}
	}
	
	public void DoParkCar(){
		System.out.println("carGui: Parking car.");
		//not sure how to make it disappear. Making it transparent probably isn't a good solution
	}

	/**
	 * Utilities
	 */
	public CarGui(CarAgent agent) {
        this.agent = agent;
    }
	
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
		//account for roads

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		 g.setColor(Color.YELLOW);
	     g.fillRect(xPos, yPos, 25, 25);

	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
