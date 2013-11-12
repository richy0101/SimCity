package stackRestaurant.gui;

import stackRestaurant.StackCustomerRole;
import stackRestaurant.StackWaiterRole;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
@SuppressWarnings("serial")
public class RestaurantGui extends JFrame implements ActionListener {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	AnimationPanel animationPanel = new AnimationPanel();
	
	JPanel namePanel;
	JLabel nameLabel;
	
	JPanel nameAndInfoPanel;
	JPanel optionsAndInfoPanel;
	
    /* restPanel holds 3 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     * 3) the waiterPanel
     */    
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    private JPanel infoPanel;
    private JLabel infoLabel; //part of infoPanel
    private JCheckBox stateCB;//part of infoLabel
    private JCheckBox waiterBreak;

    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
        int WINDOWX = 750;
        int WINDOWY = 850;

    	
    	setBounds(0, 0, WINDOWX, WINDOWY);
    	setLayout(new GridLayout(2,1,20,20));
    	
    	add(animationPanel);
        
        
        // Now, setup the info panel
        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));

        stateCB = new JCheckBox();
        stateCB.setVisible(false);
        stateCB.addActionListener(this);
        
        waiterBreak = new JCheckBox();
        waiterBreak.setVisible(false);
        waiterBreak.addActionListener(this);

        infoPanel.setLayout(new GridLayout(1, 0, 30, 0));
        
        infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
        infoPanel.add(infoLabel);
        infoPanel.add(stateCB);
        infoPanel.add(waiterBreak);
        
        optionsAndInfoPanel = new JPanel();
        optionsAndInfoPanel.setLayout(new GridLayout(2,1,20,20));
        optionsAndInfoPanel.add(restPanel);
        optionsAndInfoPanel.add(infoPanel);
        add(optionsAndInfoPanel);
        
        
    }
    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    public void updateInfoPanel(Object person) {
        currentPerson = person;

        if (person instanceof StackCustomerRole) {
            StackCustomerRole customer = (StackCustomerRole) person;
            stateCB.setVisible(true);
            waiterBreak.setVisible(false);
            stateCB.setText("Hungry?");
            stateCB.setSelected(customer.getGui().isHungry());
            stateCB.setEnabled(!customer.getGui().isHungry());
            infoLabel.setText(
               "<html><pre>     Name: " + customer.getName() + " </pre></html>");
        }
        if (person instanceof StackWaiterRole) {
        	StackWaiterRole waiter = (StackWaiterRole) person;
        	infoLabel.setText("<html><pre>     Name: " + waiter.getName()  + " </pre></html>");
        	stateCB.setVisible(false);
        	waiterBreak.setText("Go On Break?");
        	waiterBreak.setVisible(true);
        	waiterBreak.setSelected(waiter.isOnBreak());
        	if(restPanel.getWaiters().size() > 1) {
        		waiterBreak.setEnabled(true);
        	}
        	else {
        		waiterBreak.setEnabled(false);
        	}
        }
        infoPanel.validate();
    }
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof StackCustomerRole) {
                StackCustomerRole c = (StackCustomerRole) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
        else if (e.getSource() == waiterBreak) {
        	if(currentPerson instanceof StackWaiterRole) {
        		StackWaiterRole w = (StackWaiterRole) currentPerson;
        		if (waiterBreak.isSelected()) {
            		w.getGui().setWantsToGoOnBreak();
            	}
        		else {
        			w.getGui().setGoingOffBreak();
        		}
        	}
        }
    }
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    public void setCustomerEnabled(StackCustomerRole c) {
        if (currentPerson instanceof StackCustomerRole) {
            StackCustomerRole cust = (StackCustomerRole) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }
    
    public void setWaiterBreakOff(StackWaiterRole w) {
    	if (currentPerson instanceof StackWaiterRole) {
    		StackWaiterRole waiter = (StackWaiterRole) currentPerson;
    		if(w.equals(waiter)) {
    			waiterBreak.setSelected(false);
    		}
    	}
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
