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

public class CurrentBuildingPanel extends JPanel {
	String type;
	public CurrentBuildingPanel(String type) {
		this.type = type;
//		if(type.contains("restaurant")) {
			initializeRestaurant();
//		}
	}
	
	private void initializeRestaurant() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnCloseBuilding = new JButton("Close Building");
		btnCloseBuilding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		JSlider steakSlider = new JSlider(beginningSteakMin, beginningSteakMax, beginningSteakStart);
		steakSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, lblChicken, 6, SpringLayout.SOUTH, steakSlider);
		springLayout.putConstraint(SpringLayout.NORTH, steakSlider, 7, SpringLayout.SOUTH, lblSteak);
		springLayout.putConstraint(SpringLayout.WEST, steakSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, steakSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(steakSlider);
		
		int beginningChickenMin = 0;
		int beginningChickenMax = 100;
		int beginningChickenStart = 50;
		JSlider chickenSlider = new JSlider(beginningChickenMin, beginningChickenMax, beginningChickenStart);
		chickenSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, lblPizza, 6, SpringLayout.SOUTH, chickenSlider);
		springLayout.putConstraint(SpringLayout.NORTH, chickenSlider, 6, SpringLayout.SOUTH, lblChicken);
		springLayout.putConstraint(SpringLayout.WEST, chickenSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, chickenSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(chickenSlider);
		
		int beginningPizzaMin = 0;
		int beginningPizzaMax = 100;
		int beginningPizzaStart = 50;
		JSlider pizzaSlider = new JSlider(beginningPizzaMin, beginningPizzaMax, beginningPizzaStart);
		pizzaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, lblSalad, 6, SpringLayout.SOUTH, pizzaSlider);
		springLayout.putConstraint(SpringLayout.NORTH, pizzaSlider, 6, SpringLayout.SOUTH, lblPizza);
		springLayout.putConstraint(SpringLayout.WEST, pizzaSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, pizzaSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(pizzaSlider);
		
		int beginningSaladMin = 0;
		int beginningSaladMax = 100;
		int beginningSaladStart = 50;
		JSlider saladSlider = new JSlider(beginningSaladMin, beginningSaladMax, beginningSaladStart);
		saladSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, saladSlider, 6, SpringLayout.SOUTH, lblSalad);
		springLayout.putConstraint(SpringLayout.WEST, saladSlider, 0, SpringLayout.WEST, btnCloseBuilding);
		springLayout.putConstraint(SpringLayout.EAST, saladSlider, 0, SpringLayout.EAST, btnCloseBuilding);
		add(saladSlider);
		
		JLabel saladNumber = new JLabel("0");
		springLayout.putConstraint(SpringLayout.NORTH, saladNumber, 0, SpringLayout.NORTH, lblSalad);
		springLayout.putConstraint(SpringLayout.EAST, saladNumber, 0, SpringLayout.EAST, btnCloseBuilding);
		add(saladNumber);
		
		JLabel pizzaNumber = new JLabel("0");
		springLayout.putConstraint(SpringLayout.SOUTH, pizzaNumber, 0, SpringLayout.SOUTH, lblPizza);
		springLayout.putConstraint(SpringLayout.EAST, pizzaNumber, 0, SpringLayout.EAST, btnCloseBuilding);
		add(pizzaNumber);
		
		JLabel chickenNumber = new JLabel("0");
		springLayout.putConstraint(SpringLayout.WEST, chickenNumber, 0, SpringLayout.WEST, saladNumber);
		springLayout.putConstraint(SpringLayout.SOUTH, chickenNumber, 0, SpringLayout.SOUTH, lblChicken);
		add(chickenNumber);
		
		JLabel steakNumber = new JLabel("0");
		springLayout.putConstraint(SpringLayout.SOUTH, steakNumber, 0, SpringLayout.SOUTH, lblSteak);
		springLayout.putConstraint(SpringLayout.EAST, steakNumber, 0, SpringLayout.EAST, btnCloseBuilding);
		add(steakNumber);
		
	}
}
