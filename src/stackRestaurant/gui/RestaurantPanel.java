package stackRestaurant.gui;

import stackRestaurant.StackCustomerRole;
import stackRestaurant.StackHostRole;
import stackRestaurant.StackWaiterRole;
import stackRestaurant.StackCookRole;
import stackRestaurant.StackMarketRole;
import stackRestaurant.StackCashierRole;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
@SuppressWarnings("serial")
public class RestaurantPanel extends JPanel implements ActionListener {

    //Host, cook, waiters and customers
    private StackHostRole host = new StackHostRole("Sarah");
    private StackCookRole cook = new StackCookRole("Chef Boyardee");
    private StackCashierRole cashier = new StackCashierRole("Mr.Moneybags");
    
    //market hack
    /**
     * To edit the markets, simply change the number parameters.
     * The numbers are the inventory for (in this order) steak, chicken, salad, and pizza.
     */
    private StackMarketRole marketSteak = new StackMarketRole("Steak Mandini", 1, 0, 0, 0);
    private StackMarketRole marketChicken = new StackMarketRole("Chicken Flickin'", 0, 1, 0, 0);
    private StackMarketRole marketSalad = new StackMarketRole("Vegan Nansy", 0, 0, 1, 0);
    private StackMarketRole marketPizza = new StackMarketRole("Papa Murphy", 0, 0, 0, 1);

    private Vector<StackCustomerRole> customers = new Vector<StackCustomerRole>();
    private Vector<StackWaiterRole> waiters = new Vector<StackWaiterRole>();

    private JPanel restLabel = new JPanel();
    private CustomerListPanel customerPanel = new CustomerListPanel(this, "Customers");
    private WaiterListPanel waiterPanel = new WaiterListPanel(this, "Waiters");
    private JPanel group = new JPanel();
    private JPanel group2 = new JPanel();
    private JButton pause = new JButton("Pause");
    
    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;

        host.startThread();
        cook.startThread();
        cashier.startThread();
        
        CookGui cg = new CookGui(cook, gui);
		cook.setGui(cg);
		gui.animationPanel.addGui(cg);
        
        marketSteak.startThread();
        marketChicken.startThread();
        marketSalad.startThread();
        marketPizza.startThread();
        
        marketSteak.setCashier(cashier);
        marketChicken.setCashier(cashier);
        marketSalad.setCashier(cashier);
        marketPizza.setCashier(cashier);
        
        cook.msgAddMarket(marketSteak);
        cook.msgAddMarket(marketChicken);
        cook.msgAddMarket(marketSalad);
        cook.msgAddMarket(marketPizza);
        
        pause.addActionListener(this);
        setLayout(new GridLayout(1, 3, 20, 20));
        group.setLayout(new GridLayout(1, 2, 10, 10));
        group2.setLayout(new BoxLayout(group2, BoxLayout.Y_AXIS));
        group2.add(waiterPanel);
        group2.add(pause);
        group.add(customerPanel);

        initRestLabel();
        add(restLabel);
        add(group);
        add(group2);
        
    }
    
    public Vector<StackWaiterRole> getWaiters() {
    	return waiters;
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == pause) {
    		if(pause.getText().equals("Pause")) {
    			pause.setText("Resume");
    		}
    		else {
    			pause.setText("Pause");
    		}
    		host.pauseAndResume();
    		cook.pauseAndResume();
    		for(StackCustomerRole customer : customers) {
    			customer.pauseAndResume();
    		}
    		for(StackWaiterRole waiter : waiters) {
    			waiter.pauseAndResume();
    		}
    	}
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

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
                StackCustomerRole temp = customers.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
        if (type.equals("Waiters")) {

            for (int i = 0; i < waiters.size(); i++) {
                StackWaiterRole temp = waiters.get(i);
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
    public void addPerson(String type, String name, boolean isHungry) {

    	if (type.equals("Customers")) {
    		StackCustomerRole c = new StackCustomerRole(name);
    		
    		CustomerGui g = new CustomerGui(c, gui);

    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setCashier(cashier);
    		c.setGui(g);	
    		customers.add(c);
    		if (isHungry) {
    			g.setHungry();
    		}
    		c.startThread();
    	}
    	else if (type.equals("Waiters")) {
    		StackWaiterRole w = new StackWaiterRole(name);
    		
    		WaiterGui wg = new WaiterGui(w, gui);
    		w.setGui(wg);
    		gui.animationPanel.addGui(wg);
    		waiters.add(w);
    		host.msgAddWaiter(w);
            w.setCook(cook);
            w.setHost(host);
            w.setCashier(cashier);
    		w.startThread();	
    	}
    		
    }

}
