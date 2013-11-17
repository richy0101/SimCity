package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ControlPanel extends JPanel implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	public ControlPanel() {
		JTabbedPane tabbedPane = new JTabbedPane();
//		ImageIcon icon = createImageIcon("images/middle.gif");

		JPanel panel1 = new JPanel();// these can be other panels - for example, customer creation panel
		panel1.setPreferredSize(new Dimension(400,50));
		tabbedPane.addTab("Tab 1", panel1);
//		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel panel2 = new JPanel();// these can be other panels - for example, customer creation panel
		panel2.setPreferredSize(new Dimension(400,50));
		panel2.add(new JButton("press"));
		tabbedPane.addTab("Tab 2", panel2);
//		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JPanel panel3 = new JPanel(); // these can be other panels - for example, customer creation panel
		panel3.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 3", panel3);
//		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		add(tabbedPane);
		tabbedPane.setVisible(true);
	}

}
