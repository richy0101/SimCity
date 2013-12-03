package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.HostAgent;
import restaurant.WaiterAgent;
import restaurant.CookAgent;
import restaurant.MarketAgent;
import restaurant.CashierAgent;
import restaurant.interfaces.Cook;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel implements ActionListener {
	
	private Vector<WaiterAgent> waiters = new Vector<WaiterAgent>();
    
	FoodData Steak1 = new FoodData("Steak", 15, 5000, 10); 
	FoodData Chicken1 = new FoodData("Chicken", 10, 5000, 1); 
	FoodData Fish1 = new FoodData("Fish", 10, 5000, 10); 
	FoodData Vegetarian1 = new FoodData("Vegetarian", 10, 5000, 10);
	
	private Map<String, FoodData> inventory1 = new HashMap<String, FoodData>(); {
		inventory1.put("Steak", Steak1);
		inventory1.put("Chicken", Chicken1);
		inventory1.put("Fish", Fish1);
		inventory1.put("Vegetarian", Vegetarian1);
	}
	
	FoodData Steak2 = new FoodData("Steak", 20, 5000, 10); 
	FoodData Chicken2 = new FoodData("Chicken", 15, 5000, 10); 
	FoodData Fish2 = new FoodData("Fish", 20, 5000, 10); 
	FoodData Vegetarian2 = new FoodData("Vegetarian", 15, 5000, 10);
	
	private Map<String, FoodData> inventory2 = new HashMap<String, FoodData>(); {
		inventory2.put("Steak", Steak2);
		inventory2.put("Chicken", Chicken2);
		inventory2.put("Fish", Fish2);
		inventory2.put("Vegetarian", Vegetarian2);
	}
	
    private HostAgent host = new HostAgent("Host", waiters);
    private MarketAgent market1 = new MarketAgent("Market1", inventory1);
    private MarketAgent market2 = new MarketAgent("Market2", inventory2);
    private HostGui hostGui = new HostGui(host);
    private CookAgent cook = new CookAgent("Chef", market1, market2);
    private CookGui cookGui = new CookGui(cook);
    private CashierAgent cashier = new CashierAgent("Cashier");

    private Vector<CustomerAgent> customers = new Vector<CustomerAgent>();

    private JPanel restLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private ListPanel waiterPanel = new ListPanel(this, "Waiters");
    private JPanel group = new JPanel();
    private JPanel pausePanel = new JPanel();
    private JButton pauseButton = new JButton("Pause");
    private JButton restartButton = new JButton("Restart");
    private int numOfWaiters = 0;


    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) { 
        this.gui = gui;
        //host.setGui(hostGui);
        cook.setGui(cookGui);
        gui.animationPanel.addGui(cookGui);
        
        host.startThread();
        market1.startThread();
        market2.startThread();
        cook.startThread();
        cashier.startThread();

        group.setLayout(new GridLayout(1, 2, 10, 10));
        setLayout(new BorderLayout());

        group.add(customerPanel/*, BorderLayout.EAST*/);
        group.add(waiterPanel/*, BorderLayout.WEST*/);
        
        pauseButton.addActionListener((ActionListener) this);
        restartButton.addActionListener((ActionListener) this);
        
        pausePanel.add(pauseButton, BorderLayout.WEST);
        pausePanel.add(restartButton, BorderLayout.EAST);
          
        initRestLabel();
        add(restLabel, BorderLayout.WEST);
        add(group, BorderLayout.CENTER);
        add(pausePanel, BorderLayout.SOUTH);
        
        
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == pauseButton) {
        	for(WaiterAgent w: waiters)
        		w.pause();
        	for(CustomerAgent c: customers)
        		c.pause();
        	
        	host.pause();
        	cook.pause();
        }
    	
    	if (e.getSource() == restartButton) {
        	for(WaiterAgent w: waiters)
        		w.restart();
        	for(CustomerAgent c: customers)
        		c.restart();
        	
        	host.restart();
        	cook.restart();
    	}
    	
    }
    
    private void initRestLabel() {
        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$30.00</td></tr><tr><td>Chicken</td><td>$20.00</td></tr><tr><td>Fish</td><td>$25.00</td></tr><tr><td>Vegetarian</td><td>$20.00</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);
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
    		WaiterAgent w = new WaiterAgent(name, cashier, cook, host);   
    		WaiterGui g = new WaiterGui(w, gui);
    		
    		gui.animationPanel.addGui(g);
    		w.setGui(g);
    		g.setHome(numOfWaiters);
    		numOfWaiters++;
    		w.setCook(cook);
 
    		waiters.add(w);
    		w.startThread();
    	} 	
    }

}