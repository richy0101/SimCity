package stackRestaurant.gui;

import gui.Gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.ArrayList;

import stackRestaurant.helpers.TableList;

@SuppressWarnings("serial")
public class AnimationPanel extends JPanel implements ActionListener {

	private final int WINDOWX = 750;
    private final int WINDOWY = 425;
    
    private final int TABLESIZE = 50;
    
    private final int CASHIERX = 100;
    private final int CASHIERY = 200;
    
    private final int COOKTOPX = 250;
    private final int COOKTOPY = 300;
    
    private final int WAITINGX = 3;
    private final int WAITINGY = 3;
    
    private final int PLATINGX = 250;
    private final int PLATINGY = 220;
    
    private final int FRIDGEX = 310;
    private final int FRIDGEY = 250;
    
    private final int AGENTSIZE = 20;
    
    private final int DELAY = 5;

    private List<Gui> guis = new ArrayList<Gui>();
    private TableList tableList = new TableList();

    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        setBackground(Color.lightGray);
 
    	Timer timer = new Timer(DELAY, this );
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

        for(Point table: tableList.getTables()) {
        	g2.setColor(Color.ORANGE);
        	g2.fillRect((int)table.getX(), (int)table.getY(), TABLESIZE, TABLESIZE);
        }

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
        //waiting area
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke());
        g2.draw(new Rectangle2D.Double(WAITINGX, WAITINGY, 20, 20));
        
        
        //for cooktop
        g2.setColor(Color.BLACK);
        g2.fillRect(COOKTOPX, COOKTOPY, 60, 20);
        g2.setColor(Color.white);
        g2.fillRect(COOKTOPX + 17, COOKTOPY + 7, 6, 6);
        g2.fillRect(COOKTOPX + 37, COOKTOPY + 7, 6, 6);
        
        g2.fillRect(FRIDGEX, FRIDGEY, 20, 40);
        
        g2.setColor(Color.RED);
        g2.fillRect(PLATINGX, PLATINGY, 60, 20);
        
        
        g2.setColor(Color.cyan);
        g2.fillRect(CASHIERX, CASHIERY, AGENTSIZE, AGENTSIZE);
    }

    public void addGui(Gui gui) {
        guis.add(gui);
    }
}
