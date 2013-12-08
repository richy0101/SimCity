package restaurant.huangRestaurant.gui;


import javax.swing.*;

import java.awt.Graphics;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {
	

    private final int WINDOWX = 600;
    private final int WINDOWY = 500;
    private Image bufferImage;
    private Dimension bufferSize;
    private Border blackBorder;
    /*Getting rid of magic numbers for table sprite */
    private static final int tableSpawnX = 100;
	private static final int tableSpawnY = 200;
	private static final int tableOffSetX = 100;
	private static final int tableOffSetY = 100;
	/*Getting rid of magic numbers for timer that dictates animated speed of the host agent */
	private static final int hostSeatingLoopSpeed = 10;

    private List<Gui> guis = new ArrayList<Gui>();
    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        blackBorder = BorderFactory.createLineBorder(Color.black);
        
        setBorder(blackBorder);
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(hostSeatingLoopSpeed, this );
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

        //Here is the table
        g2.setColor(Color.ORANGE);
        g2.fillRect(tableSpawnX, tableSpawnY, 50, 50);//200 and 250 need to be table params
        g2.fillRect(tableSpawnX + tableOffSetX, tableSpawnY, 50, 50);//200 and 250 need to be table params
        g2.fillRect(tableSpawnX + (2 * tableOffSetX), tableSpawnY, 50, 50);
        g2.fillRect(tableSpawnX + (3 * tableOffSetX), tableSpawnY, 50, 50);


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
    public void drawFoodOnTable(int x, int y, String choice) {
    	
    }
    public void addGui(CustomerGui gui) {
        guis.add(gui);
    }
    public void addGui(WaiterGui gui) {
    	guis.add(gui);
    }
    public void addGui(HostGui gui) {
        guis.add(gui);
    }

	public void addGui(CookGui cgui) {
		guis.add(cgui);
		
	}
}
