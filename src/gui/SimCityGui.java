package gui;

import java.awt.EventQueue;

import javax.swing.*;

public class SimCityGui {

	private JFrame frame;
	private JTextField textField;

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
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1133, 855);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MacroAnimationPanel macroAnimationPanel = new MacroAnimationPanel();
		macroAnimationPanel.setBounds(5, 5, 835, 400);
		frame.getContentPane().add(macroAnimationPanel);
		
		MicroAnimationPanel microAnimationPanel = new MicroAnimationPanel();
		microAnimationPanel.setBounds(5, 425, 835, 400);
		frame.getContentPane().add(microAnimationPanel);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, macroAnimationPanel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, macroAnimationPanel, -25, SpringLayout.NORTH, microAnimationPanel);
		springLayout.putConstraint(SpringLayout.NORTH, microAnimationPanel, 429, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, microAnimationPanel, 0, SpringLayout.EAST, macroAnimationPanel);
		springLayout.putConstraint(SpringLayout.WEST, macroAnimationPanel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, microAnimationPanel, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.EAST, macroAnimationPanel, -6, SpringLayout.WEST, tabbedPane);
		springLayout.putConstraint(SpringLayout.SOUTH, microAnimationPanel, 0, SpringLayout.SOUTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 843, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JButton btnPopulateCity = new JButton("Populate City");
		sl_panel.putConstraint(SpringLayout.NORTH, btnPopulateCity, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, btnPopulateCity, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnPopulateCity, 249, SpringLayout.WEST, panel);
		panel.add(btnPopulateCity);
		
		JLabel lblName = new JLabel("Name:");
		sl_panel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblName);
		
		textField = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, lblName, 6, SpringLayout.NORTH, textField);
		sl_panel.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, panel);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblOccupation = new JLabel("Occupation");
		sl_panel.putConstraint(SpringLayout.WEST, lblOccupation, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblOccupation);
		
		JComboBox comboBox = new JComboBox();
		sl_panel.putConstraint(SpringLayout.NORTH, lblOccupation, 4, SpringLayout.NORTH, comboBox);
		sl_panel.putConstraint(SpringLayout.NORTH, comboBox, 6, SpringLayout.SOUTH, textField);
		sl_panel.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, textField);
		sl_panel.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		sl_panel.putConstraint(SpringLayout.NORTH, comboBox_1, 6, SpringLayout.SOUTH, comboBox);
		sl_panel.putConstraint(SpringLayout.WEST, comboBox_1, 0, SpringLayout.WEST, textField);
		sl_panel.putConstraint(SpringLayout.EAST, comboBox_1, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(comboBox_1);
		
		JLabel lblTransportation = new JLabel("Transportation");
		sl_panel.putConstraint(SpringLayout.NORTH, lblTransportation, 4, SpringLayout.NORTH, comboBox_1);
		sl_panel.putConstraint(SpringLayout.WEST, lblTransportation, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblTransportation);
		
		JLabel lblInitialFunds = new JLabel("Initial Funds");
		sl_panel.putConstraint(SpringLayout.WEST, lblInitialFunds, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblInitialFunds);
		
		JLabel label = new JLabel("$00");
		sl_panel.putConstraint(SpringLayout.NORTH, lblInitialFunds, 0, SpringLayout.NORTH, label);
		sl_panel.putConstraint(SpringLayout.EAST, label, -10, SpringLayout.EAST, panel);
		panel.add(label);
		
		JSlider slider = new JSlider();
		sl_panel.putConstraint(SpringLayout.NORTH, slider, 6, SpringLayout.SOUTH, lblInitialFunds);
		sl_panel.putConstraint(SpringLayout.WEST, slider, 0, SpringLayout.WEST, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, slider, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(slider);
		
		JLabel lblAggressiveness = new JLabel("Aggressiveness");
		sl_panel.putConstraint(SpringLayout.NORTH, lblAggressiveness, 6, SpringLayout.SOUTH, slider);
		sl_panel.putConstraint(SpringLayout.WEST, lblAggressiveness, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblAggressiveness);
		
		JLabel label_1 = new JLabel("0");
		sl_panel.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, lblAggressiveness);
		sl_panel.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(label_1);
		
		JSlider slider_1 = new JSlider();
		sl_panel.putConstraint(SpringLayout.NORTH, slider_1, 6, SpringLayout.SOUTH, lblAggressiveness);
		sl_panel.putConstraint(SpringLayout.WEST, slider_1, 0, SpringLayout.WEST, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, slider_1, 249, SpringLayout.WEST, panel);
		panel.add(slider_1);
		
		JButton btnCreatePerson = new JButton("Create Person");
		sl_panel.putConstraint(SpringLayout.NORTH, btnCreatePerson, 6, SpringLayout.SOUTH, slider_1);
		sl_panel.putConstraint(SpringLayout.WEST, btnCreatePerson, 0, SpringLayout.WEST, btnPopulateCity);
		sl_panel.putConstraint(SpringLayout.EAST, btnCreatePerson, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(btnCreatePerson);
		
		JLabel lblHousing = new JLabel("Housing");
		sl_panel.putConstraint(SpringLayout.WEST, lblHousing, 0, SpringLayout.WEST, btnPopulateCity);
		panel.add(lblHousing);
		
		JComboBox comboBox_2 = new JComboBox();
		sl_panel.putConstraint(SpringLayout.NORTH, label, 6, SpringLayout.SOUTH, comboBox_2);
		sl_panel.putConstraint(SpringLayout.NORTH, lblHousing, 4, SpringLayout.NORTH, comboBox_2);
		sl_panel.putConstraint(SpringLayout.NORTH, comboBox_2, 6, SpringLayout.SOUTH, comboBox_1);
		sl_panel.putConstraint(SpringLayout.WEST, comboBox_2, 0, SpringLayout.WEST, textField);
		sl_panel.putConstraint(SpringLayout.EAST, comboBox_2, 0, SpringLayout.EAST, btnPopulateCity);
		panel.add(comboBox_2);
	}
	private void runSuperNorm() {
//		Role role = new BankManagerRole();
//		PersonAgent p = new PersonAgent(role);
//		role.setPerson(p);
//		p.startThread();
//		p.msgWakeUp();
		//Example Code
		//Instantiate directory to have Stack restaurant in it. 
		//Instantiate 1 person to go to stack restaurant. give it an arbitrary name for job and home and role.
		//Something needs to call this person's msgWakeUp. And scenario should run from there. Person should wake up eat and then idle.
		//change boolean Cook in person.Decide eat to false to make person go to a restaurant.
	}
}
