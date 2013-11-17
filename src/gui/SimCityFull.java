package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

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
		macroAnimationPanel.setBounds(5, 5, 835, 400);
		frame.getContentPane().add(macroAnimationPanel);
		
		MicroAnimationPanel microAnimationPanel = new MicroAnimationPanel();
		microAnimationPanel.setBounds(5, 425, 835, 400);
		frame.getContentPane().add(microAnimationPanel);
		
		ControlPanel controlPanel = new ControlPanel();
		controlPanel.setBounds(844, 5, 450, 845);
		frame.getContentPane().add(controlPanel);
	}
}
