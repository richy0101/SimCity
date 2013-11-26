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
	double till = 10000;
	
	

	public StackRestaurant(String name) {
		super();
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
		return host;
	}
	
	public StackCashierAgent getCashier() {
		return cashier;
	}
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}

}
