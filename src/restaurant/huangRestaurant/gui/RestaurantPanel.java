package restaurant.huangRestaurant.gui;

import restaurant.huangRestaurant.CustomerAgent;
import restaurant.huangRestaurant.HostAgent;
import restaurant.huangRestaurant.WaiterAgent;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel implements ActionListener{
	
    //Host, cook, waiters and customers
    private HostAgent host = new HostAgent("Sarah");
    private HostGui hostGui = new HostGui(host);
    private boolean isPaused = false;
    private Vector<CustomerAgent> customers = new Vector<CustomerAgent>();

    private JPanel restLabel = new JPanel();
    //Customer Panel to be used by RestaurantGui
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    //Waiter Panel
    private WaiterListPanel waiterPanel = new WaiterListPanel(this, "Waiters");
    
    private Vector<WaiterAgent> waiters = new Vector<WaiterAgent>();
    //Picture panel for manager
    private BufferedImage myPicture;
    private JLabel picLabel;
    private JPanel ImagePanel = new JPanel();
    
    //private JPanel group = new JPanel();

    private RestaurantGui gui; //reference to main gui
    
    private JButton pause = new JButton("Pause");

    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;
        host.setGui(hostGui);
        pause.addActionListener(this);
        gui.animationPanel.addGui(hostGui);
        host.startThread();

        setLayout(new GridLayout(0, 2, 5, 5));
        //group.setLayout(new GridLayout(1, 2, 10, 10));
        try {
        	myPicture = ImageIO.read(this.getClass().getResource("pic1.png"));
        	picLabel = new JLabel(new ImageIcon(myPicture));
        	ImagePanel.add(picLabel);
        }
        catch (IOException e) {
        	System.out.println("Werder");
        }
        //group.add(ImagePanel);
        
        initRestLabel();
        add(ImagePanel);
        add(restLabel);
        
        //add(group);
        
        CookGui cgui = new CookGui(host.getCook(), gui);
        host.getCook().setGui(cgui);
        gui.animationPanel.addGui(cgui);
    }
    @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pause) {
			if (isPaused == false) {
				gui.pauseAll(waiters, customers, host, host.getCook());
				pause.setText("Resume");
				isPaused = true;
			}
			else {
				gui.resumeAll(waiters, customers, host, host.getCook());
				pause.setText("Pause");
				isPaused = false;
			}
        }
	}
    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        //restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);
        restLabel.add((pause), BorderLayout.EAST);
    }

    /**
     * When a customer or waiter is clicked, this function calls
     * updatedInfoPanel() from the main gui so that person's information
     * will be shown
     *
     * @param type indicates whether the person is a customer or waiter
     * @param name name of person
     */
    public void showInfo(String type, String name) {

        if (type.equals("Customers")) {

            for (int i = 0; i < customers.size(); i++) {
                CustomerAgent temp = customers.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
        if (type.equals("Waiters")) {
        	for (int i = 0; i < waiters.size(); i++) {
                WaiterAgent temp = waiters.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        	
        }
    }

    /**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     */
    public void addPerson(String type, String name) {

    	if (type.equals("Customers")) {
    		CustomerAgent c = new CustomerAgent(name);	
    		CustomerGui g = new CustomerGui(c, gui);

    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setGui(g);
    		customers.add(c);
    		c.startThread();
    	}
    	if (type.equals("Waiters")) {
    		WaiterAgent w = new WaiterAgent(name, host, host.getCook());
    		WaiterGui g = new WaiterGui(w, gui, waiters.size());
    		
    		gui.animationPanel.addGui(g);
    		w.setGui(g);
    		host.addWaiter(w);
    		waiters.add(w);
    		w.startThread();
    	}
    }
    public void addHungryPerson(String type, String name) {
    	if (type.equals("Customers")){
	    		CustomerAgent c = new CustomerAgent(name);
	    		CustomerGui g = new CustomerGui (c, gui);
	    		gui.animationPanel.addGui(g);// dw
	    		c.setHost(host);
	    		c.setGui(g);
	    		customers.add(c);
	    		c.startThread();
	    		c.getGui().setHungry();
	            gui.setCustomerDisabled(c);
            }
    }
    public ListPanel getCustomers() {
    	return customerPanel;
    	
    }
    public WaiterListPanel getWaiters() {
    	return waiterPanel;
    }
    public RestaurantGui getGui() {
    	return gui;
    }
    public void updateCustomerAddButton(boolean t) {
    	if (t == true){
    		customerPanel.enableAddPerson();
    	}
    	else {
    		customerPanel.disableAddPerson();
    	}
    }
    public void updateWaiterAddButton(boolean t) {
    	if (t == true){
    		waiterPanel.enableAddPerson();
    	}
    	else {
    		waiterPanel.disableAddPerson();
    	}
    }
	public HostAgent getHost() {
		return host;
	}
}
