package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.HostAgent;
import restaurant.WaiterAgent;
import restaurant.CookAgent;
import restaurant.MarketAgent;
import restaurant.CashierAgent;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel implements ActionListener {

    //Host, cook, waiters and customers
	private int waiterNum = 0,customerNum = 0;
    private HostAgent host = new HostAgent("Sarah");
    private WaiterAgent waiter = new WaiterAgent("Richard");
    private WaiterGui waiterGui = new WaiterGui(waiter,waiterNum);
    private CookAgent cook = new CookAgent();
    private CookGui cookGui = new CookGui(cook);
    private MarketAgent market1 = new MarketAgent();
    private MarketAgent market2 = new MarketAgent();
    private MarketAgent market3 = new MarketAgent();
    private CashierAgent cashier = new CashierAgent("John");
    
    private Vector<CustomerAgent> customers = new Vector<CustomerAgent>();
    private Vector<WaiterAgent> waiters = new Vector<WaiterAgent>();

    private JPanel restLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private WaiterListPanel waiterPanel = new WaiterListPanel(this, "Waiters");
    private JPanel group = new JPanel();
    
    public JTextField waiterTxtField = new JTextField(2);
    public JTextField custTxtField = new JTextField(2);
    public JCheckBox chkbox = new JCheckBox("Hungry?");
    public JButton pause = new JButton("Pause");
    public JButton wBreak = new JButton("Break");
    
    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;
        //host.setGui(hostGui);
        cook.setGui(cookGui);
        cook.setCashier(cashier);
        waiter.setGui(waiterGui);
        
        waiter.setCook(cook);
        waiter.setHost(host);
        waiter.setCashier(cashier);
        waiters.add(waiter);
        
        market1.startThread();
        cook.setMarket(market1);
        market1.setCook(cook);
        
        market2.startThread();
        cook.setMarket(market2);
        market2.setCook(cook);
        
        market3.startThread();
        cook.setMarket(market3);
        market3.setCook(cook);
        
        cashier.setWaiter(waiter);
        cashier.startThread();
        //gui.animationPanel.addGui(hostGui);
        host.startThread();
        gui.animationPanel.addGui(cookGui);
        gui.animationPanel.addGui(waiterGui);
        waiters.get(0).startThread();
        host.addWaiter(waiter);
        cook.startThread();
  

        setLayout(new GridLayout(1, 2, 20, 20));
        group.setLayout(new GridLayout(1, 2, 10, 10));
        
        chkbox.setVisible(true);
        pause.addActionListener(this);
        wBreak.addActionListener(this);
        
        group.add(chkbox);
        group.add(pause);
        group.add(wBreak);
        group.add(customerPanel);
        group.add(custTxtField);
        group.add(waiterPanel);
        group.add(waiterTxtField);

        initRestLabel();
        add(restLabel);
        add(group);
    }
    public void actionPerformed(ActionEvent e){
    	if (e.getSource() == pause){
    		host.pause();
    		cook.pause();
    		market1.pause();
    		market2.pause();
    		market3.pause();
    		cashier.pause();
    		for(WaiterAgent w : waiters){
    			w.pause();
    		}
    		for (CustomerAgent c : customers){
    			c.pause();
    		}
    	}
    	if(e.getSource() == wBreak){
    	//	waiter.askHostForBreak();
    	}
    	
    }
    
/*
    public void updateGroupPanel(Object person) {
        chkbox.setVisible(true);
        currentPerson = person;

        if (person instanceof CustomerAgent) {
            CustomerAgent customer = (CustomerAgent) person;
            chkbox.setText("Hungry?");
          //Should checkmark be there? 
            chkbox.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            chkbox.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            infoLabel.setText(
               "<html><pre>     Name: " + customer.getName() + " </pre></html>");
        }
    }*/
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
        restLabel.add(new JLabel("       "), BorderLayout.EAST);
        restLabel.add(new JLabel("       "), BorderLayout.WEST);
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
    		CustomerGui g = new CustomerGui(c, gui,customerNum);
    		customerNum++;
    		
    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setCashier(cashier);
    		c.setGui(g);
    		customers.add(c);
    		c.startThread();
    		if(chkbox.isSelected())
    		{
    			c.getGui().setHungry();
    		}
    	}
    	if (type.equals("Waiter")) {
    		waiterNum++;
    		WaiterAgent w = new WaiterAgent(name);	
    		WaiterGui g = new WaiterGui(w,waiterNum);
    		
    		w.setGui(g);
    		w.setCook(cook);
    		w.setHost(host);
    		w.setCashier(cashier);
    		//waiters.add(w);
    		gui.animationPanel.addGui(g);
    		w.startThread();
    		waiters.add(w);
    		host.addWaiter(w);
    		//host.setWaiter(w);

    	}
    }
    

}
