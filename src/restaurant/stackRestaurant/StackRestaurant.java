package restaurant.stackRestaurant;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;
import city.PersonAgent;


public class StackRestaurant extends Restaurant {

	private String name;
	StackHostAgent host;
	StackCashierAgent cashier;
	
	public StackRestaurant(String name) {
		this.name = name;
		host = new StackHostAgent();
		cashier = new StackCashierAgent();
		host.startThread();
		cashier.startThread();
		
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public StackHostAgent getHost() {
		// TODO Auto-generated method stub
		return host;
	}

}
