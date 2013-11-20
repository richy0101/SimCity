package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MacroAnimationPanel extends JPanel implements ActionListener {
       
	BufferedImage cityImage;
	BufferedImage cityImageTop;
	BufferedImage cityImageNight;
	BufferedImage cityImageNightTop;

	private final int WINDOWX = 827;
    private final int WINDOWY = 406;
   
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();

    public MacroAnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.green);
        
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
    	try {
        	cityImage = ImageIO.read(getClass().getResource("SIMCITYBOTTOM.png"));
        	cityImageTop = ImageIO.read(getClass().getResource("SIMCITYTOP.png"));
        	cityImageNight = ImageIO.read(getClass().getResource("SIMCITYBOTTOMNIGHT.png"));
        	cityImageNightTop = ImageIO.read(getClass().getResource("SIMCITYTOPNIGHT.png"));
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

        g2.drawImage(cityImage, 0, 0, null);
        
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
        
        g2.drawImage(cityImageTop, 0, 0, null);
        
    }

    public void addGui(Gui gui) {
        guis.add(gui);
    }
}
