package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

public class SimCity {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimCity window = new SimCity();
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
	public SimCity() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][grow]", "[238.00,grow][grow][]"));
		
		MacroAnimationPanel macroAnimationPanel = new MacroAnimationPanel();
		frame.getContentPane().add(macroAnimationPanel, "cell 0 0 42 1,grow");
		
		ControlPanel controlPanel = new ControlPanel();
		frame.getContentPane().add(controlPanel, "cell 42 0 1 2,alignx right,growy");
		
		MicroAnimationPanel microAnimationPanel = new MicroAnimationPanel();
		frame.getContentPane().add(microAnimationPanel, "cell 0 1 42 2,grow");
	}

}
