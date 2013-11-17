package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class SimCityGui extends JFrame implements ActionListener {

	ControlPanel controlPanel = new ControlPanel();
	MacroAnimationPanel macroPanel = new MacroAnimationPanel();
	MicroAnimationPanel microPanel = new MicroAnimationPanel();
	GridBagConstraints constraints = new GridBagConstraints();
	private final JPanel panel = new JPanel();
	private final MacroAnimationPanel macroAnimationPanel = new MacroAnimationPanel();
	
	public SimCityGui() {
		int WINDOWX = 1050;
        int WINDOWY = 850;

		
		controlPanel.setVisible(true);
		pack();
		
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel.add(macroAnimationPanel);
		macroAnimationPanel.setLayout(new BorderLayout(0, 0));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		SimCityGui gui = new SimCityGui();
        gui.setTitle("SimCity201");
        gui.setVisible(true);
//        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
