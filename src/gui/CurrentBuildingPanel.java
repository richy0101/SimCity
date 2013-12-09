package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JSlider;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import bank.Bank;
import market.interfaces.Market;
import restaurant.Restaurant;

@SuppressWarnings("serial")
public class CurrentBuildingPanel extends JPanel {
	String type;
	Restaurant restaurant;
	Bank bank;
	Market market;
	private JLabel steakNumber;
	private JLabel chickenNumber;
	private JLabel pizzaNumber;
	private JLabel saladNumber;
	private JSlider saladSlider;
	private JSlider pizzaSlider;
	private JSlider chickenSlider;
	private JSlider steakSlider;
	
	public CurrentBuildingPanel(Object building) {
		super();
//		if(building.getClass().equals(restaurant.getClass())) {
//			building = restaurant;
//		}
		
		initializeRestaurant();
	}

	private void initializeRestaurant() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnCloseBuilding = new JButton("Close Building");
		btnCloseBuilding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeOpen();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnCloseBuilding, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCloseBuilding, -728, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnCloseBuilding, -10, SpringLayout.EAST, this);
		add(btnCloseBuilding);
		
		JLabel lblInventory = new JLabel("Inventory:");
		springLayout.putConstraint(SpringLayout.NORTH, lblInventory, 6, SpringLayout.SOUTH, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.WEST, lblInventory, 0, SpringLayout.WEST, btnCloseBuilding);
		add(lblInventory);
		
		JLabel lblSteak = new JLabel("Steak:");
		springLayout.putConstraint(SpringLayout.NORTH, lblSteak, 6, SpringLayout.SOUTH, lblInventory);
		springLayout.putConstraint(SpringLayout.WEST, lblSteak, 0, SpringLayout.WEST, btnCloseBuilding);
		add(lblSteak);
		
		JLabel lblChicken = new JLabel("Chicken:");
		springLayout.putConstraint(SpringLayout.WEST, lblChicken, 0, SpringLayout.WEST, btnCloseBuilding);
		add(lblChicken);
		
		JLabel lblPizza = new JLabel("Pizza:");
		springLayout.putConstraint(SpringLayout.WEST, lblPizza, 0, SpringLayout.WEST, btnCloseBuilding);
		add(lblPizza);
		
		JLabel lblSalad = new JLabel("Salad:");
		springLayout.putConstraint(SpringLayout.WEST, lblSalad, 0, SpringLayout.WEST, btnCloseBuilding);
		add(lblSalad);
		
		int beginningSteakMin = 0;
		int beginningSteakMax = 100;
		int beginningSteakStart = 50;
		steakSlider = new JSlider(beginningSteakMin, beginningSteakMax, beginningSteakStart);
		steakSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Steak", steakSlider.getValue());
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, lblChicken, 6, SpringLayout.SOUTH, steakSlider);
		springLayout.putConstraint(SpringLayout.NORTH, steakSlider, 7, SpringLayout.SOUTH, lblSteak);
		springLayout.putConstraint(SpringLayout.WEST, steakSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, steakSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(steakSlider);
		
		steakSlider.setMajorTickSpacing(5);
		steakSlider.setPaintTicks(true);
		
		int beginningChickenMin = 0;
		int beginningChickenMax = 100;
		int beginningChickenStart = 50;
		chickenSlider = new JSlider(beginningChickenMin, beginningChickenMax, beginningChickenStart);
		chickenSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Chicken", chickenSlider.getValue());
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, lblPizza, 6, SpringLayout.SOUTH, chickenSlider);
		springLayout.putConstraint(SpringLayout.NORTH, chickenSlider, 6, SpringLayout.SOUTH, lblChicken);
		springLayout.putConstraint(SpringLayout.WEST, chickenSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, chickenSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(chickenSlider);
		
		chickenSlider.setMajorTickSpacing(5);
		chickenSlider.setPaintTicks(true);
		
		int beginningPizzaMin = 0;
		int beginningPizzaMax = 100;
		int beginningPizzaStart = 50;
		pizzaSlider = new JSlider(beginningPizzaMin, beginningPizzaMax, beginningPizzaStart);
		pizzaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Pizza", pizzaSlider.getValue());
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, lblSalad, 6, SpringLayout.SOUTH, pizzaSlider);
		springLayout.putConstraint(SpringLayout.NORTH, pizzaSlider, 6, SpringLayout.SOUTH, lblPizza);
		springLayout.putConstraint(SpringLayout.WEST, pizzaSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, pizzaSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(pizzaSlider);
		
		pizzaSlider.setMajorTickSpacing(5);
		pizzaSlider.setPaintTicks(true);
		
		int beginningSaladMin = 0;
		int beginningSaladMax = 100;
		int beginningSaladStart = 50;
		saladSlider = new JSlider(beginningSaladMin, beginningSaladMax, beginningSaladStart);
		saladSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Salad", saladSlider.getValue());
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, saladSlider, 6, SpringLayout.SOUTH, lblSalad);
		springLayout.putConstraint(SpringLayout.WEST, saladSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, saladSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(saladSlider);
		
		saladSlider.setMajorTickSpacing(5);
		saladSlider.setPaintTicks(true);
		
		saladNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.NORTH, saladNumber, 0, SpringLayout.NORTH, lblSalad);
		springLayout.putConstraint(SpringLayout.EAST, saladNumber, 0, SpringLayout.EAST, btnCloseBuilding);
		add(saladNumber);
		
		pizzaNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.SOUTH, pizzaNumber, 0, SpringLayout.SOUTH, lblPizza);
		springLayout.putConstraint(SpringLayout.EAST, pizzaNumber, 0, SpringLayout.EAST, btnCloseBuilding);
		add(pizzaNumber);
		
		chickenNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.WEST, chickenNumber, 0, SpringLayout.WEST, saladNumber);
		springLayout.putConstraint(SpringLayout.SOUTH, chickenNumber, 0, SpringLayout.SOUTH, lblChicken);
		add(chickenNumber);
		
		steakNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.SOUTH, steakNumber, 0, SpringLayout.SOUTH, lblSteak);
		springLayout.putConstraint(SpringLayout.EAST, steakNumber, 0, SpringLayout.EAST, btnCloseBuilding);
		add(steakNumber);
		
	}
	
	public void msgChangeSteakInventory(int steakInventory) {
		steakSlider.setValue(steakInventory);
		steakNumber.setText(String.valueOf(steakInventory));
	}
	
	public void msgChangeChickenInventory(int chickenInventory) {
		chickenSlider.setValue(chickenInventory);
		chickenNumber.setText(String.valueOf(chickenInventory));
	}

	public void msgChangePizzaInventory(int pizzaInventory) {
		pizzaSlider.setValue(pizzaInventory);
		pizzaNumber.setText(String.valueOf(pizzaInventory));
	}

	public void msgChangeSaladInventory(int saladInventory) {
		saladSlider.setValue(saladInventory);
		saladNumber.setText(String.valueOf(saladInventory));
	}
}
