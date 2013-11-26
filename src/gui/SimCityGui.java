package gui;

import home.LandlordRole;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.*;

import city.BusAgent;
import city.PersonAgent;
import city.TransportationRole;
import city.gui.BusGui;
import city.helpers.Clock;
import city.helpers.Directory;
import city.helpers.XMLReader;
import agent.Role;
import bank.BankCustomerRole;
import bank.BankManagerAgent;
import bank.BankTellerRole;
import bank.gui.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import market.MarketCustomerRole;
import market.MarketRole;
import restaurant.stackRestaurant.StackCookRole;
import restaurant.stackRestaurant.StackHostAgent;
import restaurant.stackRestaurant.StackWaiterNormalRole;
import restaurant.stackRestaurant.StackWaiterRole;
import restaurant.stackRestaurant.StackWaiterSharedRole;
import restaurant.stackRestaurant.gui.StackRestaurantAnimationPanel;

public class SimCityGui {
    
	//NEW STUFF
	JPanel buildingPanels;
	Random rand = new Random();
	CardLayout cardLayout;
	MacroAnimationPanel macroAnimationPanel;
	private JFrame frame;
	private Map<String, String> roles = new HashMap<String, String>();
	private HashMap<String, CityCard> cards = new HashMap<String, CityCard>();
	BusAgent bus;
	BusAgent bus2;
	BusGui busGui;
	BusGui busGui2;
	
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
		Directory.sharedInstance().setCityGui(this);
		runSuperNorm();
	}
    
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() { //BUTTONS
		 
        roles.put("Bank Teller", "BankTeller");
        roles.put("Market 1 Seller", "Market1");
        roles.put("Market 2 Seller", "Market2");
        roles.put("Stack's Restaurant Waiter Normal", "StackWaiterNormal");
        roles.put("Stack's Restaurant Waiter Shared", "StackWaiterShared");
        roles.put("Stack's Restaurant Cook", "StackCook");
        
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(0, 0, 1133, 855);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		macroAnimationPanel = new MacroAnimationPanel(this);
		macroAnimationPanel.setBounds(5, 5, 827, 406);
		frame.getContentPane().add(macroAnimationPanel);
		
		MicroAnimationPanel microAnimationPanel = new MicroAnimationPanel();
		buildingPanels = new JPanel();
		cardLayout = new CardLayout();
        //		microAnimationPanel.setLayout(cardLayout);
		buildingPanels.setLayout(cardLayout);
		
		
		//TAKES SQUARES FROM MACRO AND TURNS INTO PANELS IN MICRO
		ArrayList<Building> buildings = macroAnimationPanel.getBuildings();
		for ( int i=0; i<buildings.size(); i++ ) {
			Building b = buildings.get(i);
            //			BuildingPanel ma = new GUIHome(b, i ,this);
			
			
			BuildingPanel ma = null;
			
			
			if(b.getName().toLowerCase().contains("house")) {
				b.setBuildingPanel(new GUIHome( b, i, this ));
			}
			else if(b.getName().toLowerCase().contains("market")) {
				b.setBuildingPanel(new GUIMarket( b, i, this ));
			}
			else if(b.getName().toLowerCase().contains("bank")) {
				b.setBuildingPanel(new GUIBank( b, i, this ));
			}
			else if(b.getName().toLowerCase().contains("stack")) {
				b.setBuildingPanel(new StackRestaurantAnimationPanel(b, i, this));
            }
			else {//if(b.getName().toLowerCase().contains("stack")) {
				b.setBuildingPanel(new GUIMarket( b, i, this ));
			}
            
            //			b.setMicroAnimationPanel( ma );
            //			b.setBuildingPanel( ma );
            //			microAnimationPanel.add( ma, "" + i );
			buildingPanels.add( b.myBuildingPanel, "" + i );
		}
		
		buildingPanels.setBounds(5, 425, 827, 406);
		frame.getContentPane().add(buildingPanels);
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, macroAnimationPanel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, macroAnimationPanel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, macroAnimationPanel, -6, SpringLayout.NORTH, buildingPanels);
		springLayout.putConstraint(SpringLayout.EAST, buildingPanels, 0, SpringLayout.EAST, macroAnimationPanel);
		springLayout.putConstraint(SpringLayout.NORTH, buildingPanels, 417, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, buildingPanels, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, buildingPanels, -10, SpringLayout.SOUTH, frame.getContentPane());
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
		
		final JButton btnPopulateCity = new JButton("Populate City");
		btnPopulateCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				XMLReader reader = new XMLReader();
				ArrayList<PersonAgent> people = reader.initializePeople();
				for(PersonAgent person : people) {
					person.startThread();
				}
				btnPopulateCity.setEnabled(false);
				
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
		occupationComboBox.addItem("Bank Teller");
		occupationComboBox.addItem("Market 1 Seller");
		occupationComboBox.addItem("Market 2 Seller");
		occupationComboBox.addItem("Stack's Restaurant Waiter Normal");
		occupationComboBox.addItem("Stack's Restaurant Waiter Shared");
		occupationComboBox.addItem("Stack's Restaurant Cook");
		
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
		
		housingComboBox.addItem("House1");
		housingComboBox.addItem("House2");
		housingComboBox.addItem("House3");
		housingComboBox.addItem("House4");
		housingComboBox.addItem("House5");
		housingComboBox.addItem("House6");
		
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
		/** 
		 * Start of Hard Code Scenario
		 * 
		 */
//		String a = "Test Bank 1";
//		String b = "House1";
//		String name = "Test Person 1";
//		Role role;
//		if(rand.nextInt()%2 == 0) {
//			role = new StackWaiterSharedRole("StackRestaurant");
//		}
//		else {
//			role = new StackWaiterNormalRole("StackRestaurant");
//		}
//		PersonAgent p = new PersonAgent(role, a , b, name);
//		role.setPerson(p);
//		p.stopThread();
//		p.msgWakeUp();
//		p.startThread();
//	
//		String a2 = "StackRestaurant";
//		String b2 = "House2";
//		String name2 = "Test Person 2";
//		Role role2;
//		if(rand.nextInt()%2 == 0) {
//			role2 = new StackWaiterSharedRole("StackRestaurant");
//		}
//		else {
//			role2 = new StackWaiterNormalRole("StackRestaurant");
//		}
//		PersonAgent p2 = new PersonAgent(role2, a2 , b2, name2);
//		role2.setPerson(p2);
//		p2.msgGoWork();
//
//		String a3 = "StackRestaurant";
//		String b3 = "House3";
//		String name3 = "Test Person 3";
//		Role role3 = new StackCookRole("StackRestaurant");
//		PersonAgent p3 = new PersonAgent(role3, a3 , b3, name3);
//		role3.setPerson(p3);
//		p3.msgGoWork();
//
//		String a4 = "Bank";
//		String b4 = "House4";
//		String name4 = "Test Person 4";
//		Role role4 = new BankTellerRole("Bank");
//		PersonAgent p4 = new PersonAgent(role4, a4 , b4, name4);
//		role4.setPerson(p4);
//		p4.msgGoWork();
//		
//		String a5 = "Bank";
//		String b5 = "House5";
//		String name5 = "BankLoanPerson5";
//		Role role5 = new BankTellerRole("Bank");
//		PersonAgent p5 = new PersonAgent(role5, a5 , b5, name5);
//		role5.setPerson(p5);
//		p5.msgTestWakeUp();
//        
//		MarketRole role6 = new MarketRole("Market1");
//		Directory.sharedInstance().marketDirectory.get("Market1").setWorker(role6);
//		
//		String a6 = "Market1";
//		String b6 = "House1";
//		String name6 = "Test Person 6";
//		PersonAgent p6 = new PersonAgent(role6, a6 , b6, name6);
//		role6.setPerson(p6);
//		p6.msgGoWork();
//		
//		String a7 = "Test Bank 5";
//		String b7 = "House2";
//		String name7 = "MarketGoerPerson";
//		Role role7 = new BankTellerRole("Bank");
//		PersonAgent p7 = new PersonAgent(role7, a7 , b7, name7);
//		role7.setPerson(p7);
//		p7.msgTestWakeUp();
//		
//		bus = new BusAgent(1);
//		busGui = new BusGui(bus,1); //agent, starting StopNumber
//		bus.setGui(busGui);
//		macroAnimationPanel.addGui(busGui);
//		bus.startThread();
//		
//		
//		bus2 = new BusAgent(2);
//		busGui2 = new BusGui(bus2,2);
//		bus2.setGui(busGui2);
//		macroAnimationPanel.addGui(busGui2);
//		bus2.startThread();
		
		/**
		 End of Hard Code SuperNorm
		 */
		/**
		 * Start of Fresh SuperNorm = All agents wake up and do normal stuff.
		 */
		String a = "StackRestaurant";
		String b = "House1";
		String name = "Test Person 1";
		Role role;
		if(rand.nextInt()%2 == 0) {
			role = new StackWaiterSharedRole("StackRestaurant");
		}
		else {
			role = new StackWaiterNormalRole("StackRestaurant");
		}
		PersonAgent p = new PersonAgent(role, a , b, name);
//		p.msgWakeUp();
		role.setPerson(p);
		p.startThread();
	
		String a2 = "StackRestaurant";
		String b2 = "House2";
		String name2 = "Test Person 2";
		Role role2;
		if(rand.nextInt()%2 == 0) {
			role2 = new StackWaiterSharedRole("StackRestaurant");
		}
		else {
			role2 = new StackWaiterNormalRole("StackRestaurant");
		}
		PersonAgent p2 = new PersonAgent(role2, a2 , b2, name2);
		role2.setPerson(p2);
//		p2.msgWakeUp();
		p2.startThread();

		String a3 = "StackRestaurant";
		String b3 = "House3";
		String name3 = "Test Person 3";
		Role role3 = new StackCookRole("StackRestaurant");
		PersonAgent p3 = new PersonAgent(role3, a3 , b3, name3);
		role3.setPerson(p3);
//		p3.msgWakeUp();
		p3.startThread();

		String a4 = "Bank";
		String b4 = "House4";
		String name4 = "Test Person 4";
		Role role4 = new BankTellerRole("Bank");
		PersonAgent p4 = new PersonAgent(role4, a4 , b4, name4);
		role4.setPerson(p4);
//		p4.msgWakeUp();
		p4.startThread();
		
		String a5 = "Bank";
		String b5 = "House5";
		String name5 = "Test Person 5";
		Role role5 = new BankTellerRole("Bank");
		PersonAgent p5 = new PersonAgent(role5, a5 , b5, name5);
		role5.setPerson(p5);
//		p5.msgWakeUp();
		p5.startThread();
        
		MarketRole role6 = new MarketRole("Market1");
		Directory.sharedInstance().marketDirectory.get("Market1").setWorker(role6);
		
		String a6 = "Market1";
		String b6 = "House6";
		String name6 = "Test Person 6";
		PersonAgent p6 = new PersonAgent(role6, a6 , b6, name6);
		role6.setPerson(p6);
//		p6.msgWakeUp();
		p6.startThread();
		
		String a7 = "Bank";
		String b7 = "House2";
		String name7 = "Test Person 7";
		Role role7 = new BankTellerRole("Bank");
		PersonAgent p7 = new PersonAgent(role7, a7 , b7, name7);
		role7.setPerson(p7);
//		p7.msgWakeUp();
		p7.startThread();
		
		bus = new BusAgent(1);
		busGui = new BusGui(bus,1); //agent, starting StopNumber
		bus.setGui(busGui);
		macroAnimationPanel.addGui(busGui);
		bus.startThread();
		
		
		bus2 = new BusAgent(2);
		busGui2 = new BusGui(bus2,2);
		bus2.setGui(busGui2);
		macroAnimationPanel.addGui(busGui2);
		bus2.startThread();
		
//		Clock.sharedInstance().notifyTimeToWakeUp();
	}
	
	public void displayBuildingPanel( BuildingPanel buildingPanel ) { //How is this tied in with the Micro Panel?
		//System.out.println("abc");
		System.out.println("Accessing " + buildingPanel.getName() + " for MicroAnimation Panel." );
		cardLayout.show( buildingPanels, buildingPanel.getName());
	}
	public MacroAnimationPanel getMacroAnimationPanel() {
		return macroAnimationPanel;
	}
    //	public void displayMicroAnimationPanel(MicroAnimationPanel microAnimationPanel) {
    //			System.out.println("Accessing " + microAnimationPanel.getName() + " for MicroAnimationPanel.");
    //			cardLayout.show(buildingPanels, microAnimationPanel.getName());
    //	}
	
	public BusAgent getBus() {
		return bus;
	}
}
