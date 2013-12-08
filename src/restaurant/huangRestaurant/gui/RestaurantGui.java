package restaurant.huangRestaurant.gui;

import restaurant.huangRestaurant.CookAgent;
import restaurant.huangRestaurant.CustomerAgent;
import restaurant.huangRestaurant.HostAgent;
import restaurant.huangRestaurant.WaiterAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    /*restTab contains restPanel
	 * 
	 */
	private JTabbedPane TabPane = new JTabbedPane();
    /* infoPanel holds information about the clicked customer, if there is one*/
	private JPanel infoPanel;
	//customer gui parts
    private JPanel customerInfoPanel;
    private JPanel customerLabelPanel; 
    private JPanel customerNamePanel;
    private JLabel customerInfoLabel; 
    private String defaultCustomerNameState = "Customer Name";
    private JTextField customerNameField = new JTextField (defaultCustomerNameState);
    private JCheckBox stateCB;//part of infoLabel
    //waiter gui parts
    private JPanel waiterInfoPanel;
    private JPanel waiterTextPanel;
    private JLabel waiterInfoLabel;
    private JPanel waiterNamePanel;
    private String defaultWaiterNameState = "Waiter Name";
    private JTextField waiterNameField = new JTextField (defaultWaiterNameState);
    private JCheckBox stateWA;//Waiter break CB
 
    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
        int WINDOWX = 1500;
        int WINDOWY = 500;

    	setBounds(50, 50, WINDOWX, WINDOWY);

        setLayout(new GridLayout(1,3,0,0));
        
        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .6));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);

        
        // Now, setup the info panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        customerInfoPanel = new JPanel();
        customerInfoPanel.setLayout(new GridLayout(0, 2, 15, 0));
        
        stateCB = new JCheckBox();
        stateCB.setVisible(false);
        stateCB.addActionListener(this);
        
        customerInfoLabel = new JLabel(); 
        customerInfoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
     
        
        customerNamePanel = new JPanel();
        //customerNamePanel.setLayout(new GridLayout(0, 2, 0 , 0));
        
        customerNameField.setPreferredSize(new Dimension(100, 20));
        customerNamePanel.add(customerNameField);
        customerNameField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				updateCustomerNameField(customerNameField);
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				updateCustomerNameField(customerNameField);
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				updateCustomerNameField(customerNameField);
				
			}
        	
        });
     
        customerNamePanel.add(stateCB);
        customerLabelPanel = new JPanel();
        customerLabelPanel.setLayout(new GridLayout (0, 1, 0, 0));
        customerLabelPanel.add(customerInfoLabel);
        customerLabelPanel.add(customerNamePanel);
        
        customerInfoPanel.add(restPanel.getCustomers());
        customerInfoPanel.add(customerLabelPanel);
        
        waiterInfoPanel = new JPanel();
        waiterInfoPanel.setLayout(new GridLayout(0, 2, 15, 0));
        
        waiterInfoLabel = new JLabel(); 
        waiterInfoLabel.setText("<html><pre><i>Click Add to make waiters</i></pre></html>");
        
        stateWA = new JCheckBox();
        stateWA.setVisible(false);
        stateWA.addActionListener(this);
        
        waiterNamePanel = new JPanel();
        waiterNamePanel.setLayout(new GridLayout(0, 1, 0, 15));
        waiterTextPanel = new JPanel();
        waiterNameField.setPreferredSize(new Dimension(100, 20));
        waiterTextPanel.add(waiterNameField);
        waiterTextPanel.add(stateWA);
        
        waiterNameField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				updateWaiterNameField(waiterNameField);
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				updateWaiterNameField(waiterNameField);
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				updateWaiterNameField(waiterNameField);
				
			}
        	
        });
        

        
        waiterNamePanel.add(waiterInfoLabel);
        waiterNamePanel.add(waiterTextPanel);
        waiterInfoPanel.add(restPanel.getWaiters());
        waiterInfoPanel.add(waiterNamePanel);
        
   
        infoPanel.add(customerInfoPanel);
        infoPanel.add(waiterInfoPanel);
        


        add(restPanel);
        add(animationPanel);
        add(infoPanel);
       
    }
    
    /**
     * updatecustomerNameField() Checks the text field customerNameField to see if the user has
     * input a new customer. It will then show the check box it the user has.
     * @param cookAgent 
     * @param host 
     * @param customers 
     * @param waiters 
     *
     * @param customerNameField, JTextField
     */
    public void pauseAll(Vector<WaiterAgent> waiters, Vector<CustomerAgent> customers, HostAgent host, CookAgent cook) {
    	for(WaiterAgent w : waiters) {
    		w.pause();
    	}
    	for(CustomerAgent c : customers) {
    		c.pause();
    	}
    	host.pause();
    	cook.pause();
    }
    public void resumeAll(Vector<WaiterAgent> waiters, Vector<CustomerAgent> customers, HostAgent host, CookAgent cook) {
    	for(WaiterAgent w : waiters) {
    		w.resume();
    	}
    	for(CustomerAgent c : customers) {
    		c.resume();
    	}
    	host.resume();
    	cook.resume();
    }
    public void updateCustomerNameField(JTextField customerNameField) {
    	if (!customerNameField.getText().equals(defaultCustomerNameState) && !customerNameField.getText().equals("")) {
    		stateCB.setText("Hungry?");
            stateCB.setVisible(true);
            currentPerson = null;
            stateCB.setEnabled(true);
            restPanel.updateCustomerAddButton(true);
    	}
    	else {
    		stateCB.setVisible(false);
    		restPanel.updateCustomerAddButton(false);
    	}
    }
    public String getCustomerNameField() {
    	return customerNameField.getText();
    }
    
    
    //Waiter
    public void updateWaiterNameField(JTextField waiterNameField) {
    	if (!waiterNameField.getText().equals(defaultWaiterNameState) && !waiterNameField.getText().equals("")) {
            currentPerson = null;
            stateWA.setVisible(false);
            stateWA.setEnabled(true);
            stateWA.setSelected(false);
            restPanel.updateCustomerAddButton(true);
            restPanel.updateWaiterAddButton(true);
    	}
    	else {
    		restPanel.updateWaiterAddButton(false);
    	}
    }
    public String getWaiterNameField() {
    	return waiterNameField.getText();
    }
    /** 
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    public void updateInfoPanel(Object person) {
        currentPerson = person;
        if (person instanceof CustomerAgent) {
            stateCB.setVisible(true);
            CustomerAgent customer = (CustomerAgent) person;
            stateCB.setText("Hungry?");
          //Should checkmark be there? 
            stateCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            stateCB.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            customerInfoLabel.setText("<html><pre> Selected Customer: " + customer.getName() + " </pre></html>");
            customerNameField.setText(customer.getName());
        }
        customerInfoPanel.validate();
        if (person instanceof WaiterAgent) {
        	stateWA.setVisible(true);
        	WaiterAgent w = (WaiterAgent) person;
        	stateWA.setText("Break?");
        	stateWA.setSelected(w.getGui().isBreak());
        	stateWA.setEnabled(!w.getGui().isBreak());
        	waiterInfoLabel.setText("<html><pre> Selected Waiter: " + w.getName() + " </pre></html>");
        	waiterNameField.setText(w.getName());
        }
        waiterInfoPanel.validate();
    }
    public void askHungry(Object person) {
    	 if (person instanceof CustomerAgent) {
             CustomerAgent customer = (CustomerAgent) person;
             customer.gotHungry();
             stateCB.setEnabled(!customer.getGui().isHungry());
    	 }
    }
    public boolean getStateSelection() {
    	if (stateCB.isSelected()) {
    		return true;
    	}
    	else {
    		return false;
    	}
   
    }
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof CustomerAgent) {
                CustomerAgent c = (CustomerAgent) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
        if (e.getSource() == stateWA) {
        	if (currentPerson instanceof WaiterAgent) {
        		WaiterAgent w = (WaiterAgent) currentPerson;
        		stateWA.setEnabled(false);
        		w.getGui().setBreak();
        		setWaiterDisabled(w);
        	}
        }
    }
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    public void setCustomerEnabled(CustomerAgent c) {
        if (currentPerson instanceof CustomerAgent) {
            CustomerAgent cust = (CustomerAgent) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }
    public void setCustomerDisabled(CustomerAgent c) {
    	CustomerAgent cust = (CustomerAgent) currentPerson;
        if (c.equals(cust)) {
            stateCB.setEnabled(false);
            stateCB.setSelected(true);
        }
    } 
    public void setWaiterEnabled(WaiterAgent w) {
            WaiterAgent waiter = (WaiterAgent) currentPerson;
            if (w.equals(waiter)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
   }
    public void setWaiterDisabled(WaiterAgent w) {
    	WaiterAgent waiter = (WaiterAgent) currentPerson;
    	if (w.equals(waiter)) {
    		stateWA.setSelected(true);
    	}
    }
    public CustomerAgent getCurrentPerson() {
    	if(currentPerson instanceof CustomerAgent) {
            CustomerAgent c = (CustomerAgent) currentPerson;
            return c;
    	}
    	return null;
    }
    /**
     * Main routine to get gui started
     */
    public static void main(String[] args) {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
