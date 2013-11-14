package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SimCityGui extends JFrame implements ActionListener {

	ControlPanel controlPanel = new ControlPanel();
	
	public SimCityGui() {
		add(controlPanel);
		controlPanel.setVisible(true);
		pack();
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
