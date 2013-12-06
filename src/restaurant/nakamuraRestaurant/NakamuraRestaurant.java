package restaurant.nakamuraRestaurant;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;
import city.PersonAgent;


public class NakamuraRestaurant extends Restaurant {

	private String name;
	NakamuraHostRole host;
	NakamuraCashierRole cashier;
	double till = 10000;
	
	

	public NakamuraRestaurant(String name) {
		super();
		this.name = name;	
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public NakamuraHostRole getHost() {
		return host;
	}
	
	public NakamuraCashierRole getCashier() {
		return cashier;
	}
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}

}
