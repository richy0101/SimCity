package restaurant.shehRestaurant;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import restaurant.Restaurant;
import restaurant.shehRestaurant.*;
import city.PersonAgent;


public class ShehRestaurant extends Restaurant {

	private String name;
	ShehHostAgent host;
	ShehCashierAgent cashier;
	double till = 10000;
	
	

	public ShehRestaurant(String name) {
		super();
		this.name = name;
		host = new ShehHostAgent();
		cashier = new ShehCashierAgent();
		host.startThread();
		cashier.startThread();	
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public ShehHostAgent getHost() {
		return host;
	}
	
	public ShehCashierAgent getCashier() {
		return cashier;
	}
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}

}
