package restaurant.huangRestaurant.gui;


import restaurant.huangRestaurant.CustomerAgent;
import restaurant.huangRestaurant.HostAgent;

import java.awt.*;

public class HostGui implements Gui {

    private HostAgent agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position

    public int xTable = 200;
    public int yTable = 250;

    public HostGui(HostAgent agent) {
        this.agent = agent;
    }

    public void updatePosition() {

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
    }

    public boolean isPresent() {
        return true;
    }


    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
