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
	private JLabel lblRegister;
	
	public CurrentBuildingPanel(Object building) {
		super();
		if(building instanceof Restaurant) {
			restaurant = (Restaurant) building;
		}
		
		initializeRestaurant();
	}

	private void initializeRestaurant() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		final JButton btnCloseBuilding = new JButton();
		if(restaurant != null) {
			if(restaurant.isOpen()) {
				btnCloseBuilding.setText("Close Building");
			}
			else {
				btnCloseBuilding.setText("Open Building");
			}
		}
		springLayout.putConstraint(SpringLayout.WEST, btnCloseBuilding, 10, SpringLayout.WEST, this);
		btnCloseBuilding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(restaurant != null && btnCloseBuilding.getText().contains("Close")) {
					restaurant.msgSetClosed();
					btnCloseBuilding.setText("Open Building");
				}
				else if(restaurant != null) {
					restaurant.msgSetOpen();
					btnCloseBuilding.setText("Close Building");
				}
			}
		});
		add(btnCloseBuilding);
		
		JLabel lblInventory = new JLabel("Inventory:");
		springLayout.putConstraint(SpringLayout.WEST, lblInventory, 10, SpringLayout.WEST, this);
		add(lblInventory);
		
		JLabel lblSteak = new JLabel("Steak:");
		springLayout.putConstraint(SpringLayout.WEST, lblSteak, 10, SpringLayout.WEST, this);
		add(lblSteak);
		
		JLabel lblChicken = new JLabel("Chicken:");
		springLayout.putConstraint(SpringLayout.WEST, lblChicken, 10, SpringLayout.WEST, this);
		add(lblChicken);
		
		JLabel lblPizza = new JLabel("Pizza:");
		springLayout.putConstraint(SpringLayout.WEST, lblPizza, 10, SpringLayout.WEST, this);
		add(lblPizza);
		
		JLabel lblSalad = new JLabel("Salad:");
		springLayout.putConstraint(SpringLayout.WEST, lblSalad, 10, SpringLayout.WEST, this);
		add(lblSalad);
		
		int beginningSteakMin = 0;
		int beginningSteakMax = 100;
		int beginningSteakStart = 50;
		steakSlider = new JSlider(beginningSteakMin, beginningSteakMax, beginningSteakStart);
		springLayout.putConstraint(SpringLayout.EAST, btnCloseBuilding, 0, SpringLayout.EAST, steakSlider);
		springLayout.putConstraint(SpringLayout.WEST, steakSlider, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, steakSlider, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblSteak, -6, SpringLayout.NORTH, steakSlider);
		springLayout.putConstraint(SpringLayout.SOUTH, steakSlider, -6, SpringLayout.NORTH, lblChicken);
		steakSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Steak", steakSlider.getValue());
					steakNumber.setText(String.valueOf(steakSlider.getValue()));
				}
			}
		});
		add(steakSlider);
		
		steakSlider.setMajorTickSpacing(5);
		steakSlider.setPaintTicks(true);
		
		int beginningChickenMin = 0;
		int beginningChickenMax = 100;
		int beginningChickenStart = 50;
		chickenSlider = new JSlider(beginningChickenMin, beginningChickenMax, beginningChickenStart);
		springLayout.putConstraint(SpringLayout.WEST, chickenSlider, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, chickenSlider, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblChicken, -6, SpringLayout.NORTH, chickenSlider);
		springLayout.putConstraint(SpringLayout.SOUTH, chickenSlider, -6, SpringLayout.NORTH, lblPizza);
		chickenSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Chicken", chickenSlider.getValue());
					chickenNumber.setText(String.valueOf(chickenSlider.getValue()));
				}
			}
		});
		add(chickenSlider);
		
		chickenSlider.setMajorTickSpacing(5);
		chickenSlider.setPaintTicks(true);
		
		int beginningPizzaMin = 0;
		int beginningPizzaMax = 100;
		int beginningPizzaStart = 50;
		pizzaSlider = new JSlider(beginningPizzaMin, beginningPizzaMax, beginningPizzaStart);
		springLayout.putConstraint(SpringLayout.WEST, pizzaSlider, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, pizzaSlider, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblPizza, -6, SpringLayout.NORTH, pizzaSlider);
		springLayout.putConstraint(SpringLayout.SOUTH, pizzaSlider, -6, SpringLayout.NORTH, lblSalad);
		pizzaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Pizza", pizzaSlider.getValue());
					pizzaNumber.setText(String.valueOf(pizzaSlider.getValue()));
				}
			}
		});
		add(pizzaSlider);
		
		pizzaSlider.setMajorTickSpacing(5);
		pizzaSlider.setPaintTicks(true);
		
		int beginningSaladMin = 0;
		int beginningSaladMax = 100;
		int beginningSaladStart = 50;
		saladSlider = new JSlider(beginningSaladMin, beginningSaladMax, beginningSaladStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnCloseBuilding, 16, SpringLayout.SOUTH, saladSlider);
		springLayout.putConstraint(SpringLayout.WEST, saladSlider, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, saladSlider, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, saladSlider, 302, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblSalad, -6, SpringLayout.NORTH, saladSlider);
		saladSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(restaurant != null) {
					restaurant.msgChangeFoodInventory("Salad", saladSlider.getValue());
					saladNumber.setText(String.valueOf(saladSlider.getValue()));
				}
			}
		});
		add(saladSlider);
		
		saladSlider.setMajorTickSpacing(5);
		saladSlider.setPaintTicks(true);
		
		saladNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.NORTH, saladNumber, 0, SpringLayout.NORTH, lblSalad);
		springLayout.putConstraint(SpringLayout.EAST, saladNumber, -10, SpringLayout.EAST, this);
		add(saladNumber);
		
		pizzaNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.NORTH, pizzaNumber, 0, SpringLayout.NORTH, lblPizza);
		springLayout.putConstraint(SpringLayout.EAST, pizzaNumber, -10, SpringLayout.EAST, this);
		add(pizzaNumber);
		
		chickenNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.NORTH, chickenNumber, 0, SpringLayout.NORTH, lblChicken);
		springLayout.putConstraint(SpringLayout.EAST, chickenNumber, -10, SpringLayout.EAST, this);
		add(chickenNumber);
		
		steakNumber = new JLabel("50");
		springLayout.putConstraint(SpringLayout.NORTH, steakNumber, 0, SpringLayout.NORTH, lblSteak);
		springLayout.putConstraint(SpringLayout.EAST, steakNumber, -10, SpringLayout.EAST, this);
		add(steakNumber);
		
		JLabel lblName = new JLabel("Name: " + restaurant.getName());
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, this);
		add(lblName);
		
		lblRegister = new JLabel("Register: " + restaurant.getTill());
		springLayout.putConstraint(SpringLayout.WEST, lblRegister, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblInventory, 6, SpringLayout.SOUTH, lblRegister);
		springLayout.putConstraint(SpringLayout.NORTH, lblRegister, 6, SpringLayout.SOUTH, lblName);
		add(lblRegister);
		
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
	
	public void msgChangeTillInformation(double till) {
		lblRegister.setText("Register: " + String.valueOf(till));
	}
}
