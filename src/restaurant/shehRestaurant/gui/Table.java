package restaurant.shehRestaurant.gui;

import restaurant.shehRestaurant.CustomerAgent;


public class Table {
	CustomerAgent occupiedBy;
	int tableNumber;

	public Table(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public void setOccupant(CustomerAgent cust) {
		occupiedBy = cust;
	}

	public void setUnoccupied() {
		occupiedBy = null;
	}

	public CustomerAgent getOccupant() {
		return occupiedBy;
	}

	public boolean isOccupied() {
		return occupiedBy != null;
	}

	public String toString() {
		return "table " + tableNumber;
	}
	
	public int getTableNumber() {
		return tableNumber;
	}
}