package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class MacroAnimationPanel extends JPanel implements ActionListener {
	
	private final int WINDOWX = 835;
    private final int WINDOWY = 400;
   
    
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();
    private ImageIcon background = new ImageIcon("/team02/src/gui/SIMCITY.jpg");
    //private ImageIcon background = new ImageIcon("/images/SIMCITY.png");
    private Image image = background.getImage();


    public MacroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.darkGray);
        
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
        g2.fillRect(0, 0, getWidth(), getHeight());
        //g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);

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
