package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class MacroAnimationPanel extends JPanel implements ActionListener {
	
	private final int WINDOWX = 750;
    private final int WINDOWY = 425;
   
    
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();

    public MacroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
 
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
    }

    public void addGui(Gui gui) {
        guis.add(gui);
    }

}
