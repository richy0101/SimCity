package restaurant.shehRestaurant;

import restaurant.shehRestaurant.interfaces.Cashier;

public class ShehWaiterNormalRole extends ShehWaiterRole {

	public ShehWaiterNormalRole(String name, Cashier ca, ShehCookRole co,
			ShehHostAgent h) {
		super(name, ca, co, h);
	}
}