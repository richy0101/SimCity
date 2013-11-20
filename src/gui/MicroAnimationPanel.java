package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MicroAnimationPanel extends JPanel implements ActionListener {
	
	private final int WINDOWX = 835;
    private final int WINDOWY = 400;
   
    BufferedImage restaurantImage;
    
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();

    public MicroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
 
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
    	try {
        	restaurantImage = ImageIO.read(getClass().getResource("stackRestaurant.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.drawImage(restaurantImage, 0, 0, null);
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
