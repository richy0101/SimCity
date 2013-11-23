package gui;

import home.LandlordRole;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.*;

import city.PersonAgent;
import agent.Role;
import bank.BankManagerRole;
import bank.BankTellerRole;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import restaurant.stackRestaurant.StackCookRole;
import restaurant.stackRestaurant.StackHostRole;

public class SimCityGui {

	//NEW STUFF
	JPanel buildingPanels;
	CardLayout cardLayout;

	private JFrame frame;
	private Map<String, Role> roles = new HashMap<String, Role>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimCityGui window = new SimCityGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimCityGui() {
		initialize();
		runSuperNorm();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() { //BUTTONS
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(0, 0, 1133, 855);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MacroAnimationPanel macroAnimationPanel = new MacroAnimationPanel();
		macroAnimationPanel.setBounds(5, 5, 827, 406);
		frame.getContentPane().add(macroAnimationPanel);
		
		MicroAnimationPanel microAnimationPanel = new MicroAnimationPanel();
		cardLayout = new CardLayout();
		microAnimationPanel.setLayout(cardLayout);
		
		//Creates building panel for each object
		ArrayList<Building> buildings = macroAnimationPanel.getBuildings();
		for ( int i=0; i<buildings.size(); i++ ) {
			Building b = buildings.get(i);
			BuildingPanel bp = new BuildingPanel( b, i, this );
			b.setBuildingPanel( bp );
			microAnimationPanel.add( bp, "" + i );
		}
		
		microAnimationPanel.setBounds(5, 425, 827, 406);
		frame.getContentPane().add(microAnimationPanel);
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, macroAnimationPanel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, macroAnimationPanel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, macroAnimationPanel, -6, SpringLayout.NORTH, microAnimationPanel);
		springLayout.putConstraint(SpringLayout.EAST, microAnimationPanel, 0, SpringLayout.EAST, macroAnimationPanel);
		springLayout.putConstraint(SpringLayout.NORTH, microAnimationPanel, 417, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, microAnimationPanel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, microAnimationPanel, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.EAST, macroAnimationPanel, -6, SpringLayout.WEST, tabbedPane);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 843, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Create Person", null, panel, null);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JButton btnPopulateCity = new JButton("Populate City");
		btnPopulateCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// script for populating city
				
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, btnPopulateCity, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, btnPopulateCity, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnPopulateCity, 249, SpringLayout.WEST, panel);
		panel.add(btnPopulateCity);
		
		JLabel lblName = new JLabel("Name:");
		sl_panel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblName);
		
		final JTextField nameTextField = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, lblName, 6, SpringLayout.NORTH, nameTextField);
		sl_panel.putConstraint(SpringLayout.NORTH, nameTextField, 6, SpringLayout.SOUTH, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, nameTextField, -10, SpringLayout.EAST, panel);
		panel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblOccupation = new JLabel("Occupation");
		sl_panel.putConstraint(SpringLayout.WEST, lblOccupation, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblOccupation);
		
		final JComboBox<String> occupationComboBox = new JComboBox<String>();
		sl_panel.putConstraint(SpringLayout.NORTH, lblOccupation, 4, SpringLayout.NORTH, occupationComboBox);
		sl_panel.putConstraint(SpringLayout.NORTH, occupationComboBox, 6, SpringLayout.SOUTH, nameTextField);
		sl_panel.putConstraint(SpringLayout.WEST, occupationComboBox, 0, SpringLayout.WEST, nameTextField);
		sl_panel.putConstraint(SpringLayout.EAST, occupationComboBox, 0, SpringLayout.EAST, btnPopulateCity);
		
		occupationComboBox.addItem("None");
		occupationComboBox.addItem("Bank Manager");
		occupationComboBox.addItem("Bank Teller");
		occupationComboBox.addItem("Market Seller");
		occupationComboBox.addItem("Landlord");
		occupationComboBox.addItem("Stack's Restaurant Host");
		occupationComboBox.addItem("Stack's Restaurant Waiter");
		occupationComboBox.addItem("Stack's Restaurant Cook");
		occupationComboBox.addItem("Stack's Restaurant Cashier");
		
//		occupationComboBox.addItem("Sheh's Restaurant Host");
//		occupationComboBox.addItem("Sheh's Restaurant Waiter");
//		occupationComboBox.addItem("Sheh's Restaurant Cook");
//		occupationComboBox.addItem("Sheh's Restaurant Cashier");
//		
//		occupationComboBox.addItem("Philips's Restaurant Host");
//		occupationComboBox.addItem("Philips's Restaurant Waiter");
//		occupationComboBox.addItem("Philips's Restaurant Cook");
//		occupationComboBox.addItem("Philips's Restaurant Cashier");
//		
//		occupationComboBox.addItem("Tan's Restaurant Host");
//		occupationComboBox.addItem("Tan's Restaurant Waiter");
//		occupationComboBox.addItem("Tan's Restaurant Cook");
//		occupationComboBox.addItem("Tan's Restaurant Cashier");
//		
//		occupationComboBox.addItem("Huang's Restaurant Host");
//		occupationComboBox.addItem("Huang's Restaurant Waiter");
//		occupationComboBox.addItem("Huang's Restaurant Cook");
//		occupationComboBox.addItem("Huang's Restaurant Cashier");
//		
//		occupationComboBox.addItem("Nakamura's Restaurant Host");
//		occupationComboBox.addItem("Nakamura's Restaurant Waiter");
//		occupationComboBox.addItem("Nakamura's Restaurant Cook");
//		occupationComboBox.addItem("Nakamura's Restaurant Cashier");
		
		roles.put("Bank Manager", new BankManagerRole());
		roles.put("Bank Teller", new BankTellerRole());
		roles.put("Market Seller", new BankManagerRole());
		roles.put("Landlord", new LandlordRole());
		roles.put("Stack's Restaurant Host", new StackHostRole());
		roles.put("Stack's Restaurant Waiter", new BankManagerRole());
		roles.put("Stack's Restaurant Cook", new BankManagerRole());
		roles.put("Stack's Restaurant Cashier", new BankManagerRole());
		
		panel.add(occupationComboBox);
		
		final JComboBox<String> transportationComboBox = new JComboBox<String>();
		sl_panel.putConstraint(SpringLayout.NORTH, transportationComboBox, 6, SpringLayout.SOUTH, occupationComboBox);
		sl_panel.putConstraint(SpringLayout.WEST, transportationComboBox, 0, SpringLayout.WEST, nameTextField);
		sl_panel.putConstraint(SpringLayout.EAST, transportationComboBox, 0, SpringLayout.EAST, btnPopulateCity);
		
		transportationComboBox.addItem("None");
		transportationComboBox.addItem("Owns A Car");
		transportationComboBox.addItem("Takes The Bus");
		
		panel.add(transportationComboBox);
		
		JLabel lblTransportation = new JLabel("Transportation");
		sl_panel.putConstraint(SpringLayout.NORTH, lblTransportation, 4, SpringLayout.NORTH, transportationComboBox);
		sl_panel.putConstraint(SpringLayout.WEST, lblTransportation, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblTransportation);
		
		JLabel lblInitialFunds = new JLabel("Initial Funds");
		sl_panel.putConstraint(SpringLayout.WEST, lblInitialFunds, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblInitialFunds);
		
		final JLabel lblMoney = new JLabel("$5000");
		sl_panel.putConstraint(SpringLayout.NORTH, lblInitialFunds, 0, SpringLayout.NORTH, lblMoney);
		sl_panel.putConstraint(SpringLayout.EAST, lblMoney, -10, SpringLayout.EAST, panel);
		panel.add(lblMoney);
		
		int beginningFundsMin = 0;
		int beginningFundsMax = 10000;
		int beginningFundsStart = 5000;
		final JSlider initialFundsSlider = new JSlider(beginningFundsMin, beginningFundsMax, beginningFundsStart);
		initialFundsSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblMoney.setText("$"+initialFundsSlider.getValue());
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, initialFundsSlider, 6, SpringLayout.SOUTH, lblInitialFunds);
		sl_panel.putConstraint(SpringLayout.WEST, initialFundsSlider, 0, SpringLayout.WEST, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, initialFundsSlider, 0, SpringLayout.EAST, btnPopulateCity);
		
		initialFundsSlider.setMajorTickSpacing(1000);
		initialFundsSlider.setMinorTickSpacing(200);
		initialFundsSlider.setPaintTicks(true);
		
		panel.add(initialFundsSlider);
		
		JLabel lblAggressiveness = new JLabel("Aggressiveness");
		sl_panel.putConstraint(SpringLayout.NORTH, lblAggressiveness, 6, SpringLayout.SOUTH, initialFundsSlider);
		sl_panel.putConstraint(SpringLayout.WEST, lblAggressiveness, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblAggressiveness);
		
		final JLabel lblAggressivenessMeter = new JLabel("2");
		sl_panel.putConstraint(SpringLayout.NORTH, lblAggressivenessMeter, 0, SpringLayout.NORTH, lblAggressiveness);
		sl_panel.putConstraint(SpringLayout.EAST, lblAggressivenessMeter, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(lblAggressivenessMeter);
		
		int beginningAggressivenessMin = 1;
		int beginningAggressivenessMax = 3;
		int beginningAggressivenessStart = 2;
		final JSlider aggressivenessSlider = new JSlider(beginningAggressivenessMin, beginningAggressivenessMax, beginningAggressivenessStart);
		aggressivenessSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblAggressivenessMeter.setText(aggressivenessSlider.getValue()+"");
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, aggressivenessSlider, 6, SpringLayout.SOUTH, lblAggressiveness);
		sl_panel.putConstraint(SpringLayout.WEST, aggressivenessSlider, 0, SpringLayout.WEST, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, aggressivenessSlider, 249, SpringLayout.WEST, panel);
		
		aggressivenessSlider.setMajorTickSpacing(1);
		aggressivenessSlider.setPaintTicks(true);
		
		panel.add(aggressivenessSlider);
		
		JLabel lblHousing = new JLabel("Housing");
		sl_panel.putConstraint(SpringLayout.WEST, lblHousing, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblHousing);
		
		final JComboBox<String> housingComboBox = new JComboBox<String>();
		sl_panel.putConstraint(SpringLayout.NORTH, lblMoney, 6, SpringLayout.SOUTH, housingComboBox);
		sl_panel.putConstraint(SpringLayout.NORTH, lblHousing, 4, SpringLayout.NORTH, housingComboBox);
		sl_panel.putConstraint(SpringLayout.NORTH, housingComboBox, 6, SpringLayout.SOUTH, transportationComboBox);
		sl_panel.putConstraint(SpringLayout.WEST, housingComboBox, 0, SpringLayout.WEST, nameTextField);
		sl_panel.putConstraint(SpringLayout.EAST, housingComboBox, 0, SpringLayout.EAST, btnPopulateCity);
		
		housingComboBox.addItem("None");
		housingComboBox.addItem("Owns A House");
		housingComboBox.addItem("Owns An Apartment");
		housingComboBox.addItem("Rents An Apartment");
		
		panel.add(housingComboBox);
		
		
		JButton btnCreatePerson = new JButton("Create Person");
		btnCreatePerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nameTextField.getText() != "" &&
						occupationComboBox.getSelectedItem() != "None" &&
						transportationComboBox.getSelectedItem() != "None" &&
								housingComboBox.getSelectedItem() != "None") {
					PersonAgent person = new PersonAgent(roles.get(occupationComboBox.getSelectedItem()),
							nameTextField.getText(),
							aggressivenessSlider.getValue(),
							(double)initialFundsSlider.getValue(),
							(String)housingComboBox.getSelectedItem(),
							(String)transportationComboBox.getSelectedItem());
//					do more stuff here
					
				}
					
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, btnCreatePerson, 6, SpringLayout.SOUTH, aggressivenessSlider);
		sl_panel.putConstraint(SpringLayout.WEST, btnCreatePerson, 0, SpringLayout.WEST, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, btnCreatePerson, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(btnCreatePerson);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Current Building", null, panel_1, null);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		JLabel lblWorkers = new JLabel("Workers");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblWorkers, 10, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblWorkers, 10, SpringLayout.WEST, panel_1);
		panel_1.add(lblWorkers);
	}
	private void runSuperNorm() {
		String a = "Test Bank 1";
		String b = "Test Home 1";
		String name = "Test Person 1";
		Role role = new BankManagerRole();
		PersonAgent p = new PersonAgent(role, a , b, name);
		role.setPerson(p);
		p.msgWakeUp();
		//Example Code
		//Instantiate directory to have Stack restaurant in it. 
		//Instantiate 1 person to go to stack restaurant. give it an arbitrary name for job and home and role.
		//Something needs to call this person's msgWakeUp. And scenario should run from there. Person should wake up eat and then idle.
		//change boolean Cook in person.Decide eat to false to make person go to a restaurant.
	}
	
	public void displayBuildingPanel( BuildingPanel bp ) { //How is this tied in with the Micro Panel?
		//System.out.println("abc");
		System.out.println("Accessing " + bp.getName() + " for MicroAnimation Panel." );
		cardLayout.show( buildingPanels, bp.getName() );
	}
}
