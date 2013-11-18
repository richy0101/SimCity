package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import bank.BankManagerRole;
import city.PersonAgent;
import agent.Role;

public class SimCityFull {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimCityFull window = new SimCityFull();
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
	public SimCityFull() {
		initialize();
		runSuperNorm();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1300, 855);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		MacroAnimationPanel macroAnimationPanel = new MacroAnimationPanel();
		macroAnimationPanel.setBounds(5, 5, 835, 415);
		frame.getContentPane().add(macroAnimationPanel);
		
		MicroAnimationPanel microAnimationPanel = new MicroAnimationPanel();
		microAnimationPanel.setBounds(5, 425, 835, 400);
		frame.getContentPane().add(microAnimationPanel);
		
		ControlPanel controlPanel = new ControlPanel();
		controlPanel.setBounds(844, 5, 450, 845);
		frame.getContentPane().add(controlPanel);
	}
	private void runSuperNorm() {
		Role role = new BankManagerRole();
		PersonAgent p = new PersonAgent(role);
		role.setPerson(p);
		p.startThread();
		p.msgWakeUp();
		//Example Code
		//Instantiate directory to have Stack restaurant in it. 
		//Instantiate 1 person to go to stack restaurant. give it an arbitrary name for job and home and role.
		//Something needs to call this person's msgWakeUp. And scenario should run from there. Person should wake up eat and then idle.
		//change boolean Cook in person.Decide eat to false to make person go to a restaurant.
	}
}
