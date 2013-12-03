//MAIN WINDOW FOR RESTAURANT AND INFO PANEL

package restaurant.gui;

import restaurant.CustomerAgent;
import restaurant.WaiterAgent;

//import restaurant.gui.AnimationPanel;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	//JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
	JPanel leftPanel = new JPanel();
	
    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    //private JPanel topPanel;
    //private JPanel bottomPanel;
    
    private JPanel infoPanel;
    private JLabel infoLabel; //part of infoPanel
    private JCheckBox stateCB;//part of infoLabel

    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
        int WINDOWX = 450;
        int WINDOWY = 350;
        int WINDOWBOUND = 50;

        animationPanel.setBounds(WINDOWBOUND, WINDOWBOUND, WINDOWX, WINDOWY);
        animationPanel.setVisible(true);
    	setBounds(WINDOWBOUND, WINDOWBOUND, WINDOWX * 2, WINDOWY);
    	
    	leftPanel.setLayout(new BorderLayout());

        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .6));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
        leftPanel.add(restPanel,BorderLayout.WEST);
        
        // Now, setup the info panel
        Dimension infoDim = new Dimension(WINDOWX, (int) (WINDOWY * .25));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));

        stateCB = new JCheckBox();
        stateCB.setVisible(false);
        stateCB.addActionListener(this);
        
        infoPanel.setLayout(new GridLayout(1, 2, 30, 0));
        
        infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
        infoPanel.add(infoLabel);
        infoPanel.add(stateCB);
        leftPanel.add(infoPanel, BorderLayout.SOUTH);
        
        setLayout(new BorderLayout());
        add(animationPanel, BorderLayout.CENTER);
        animationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        animationPanel.setBackground(Color.WHITE);
        add(leftPanel, BorderLayout.WEST);
        
        
        }
    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    public void updateInfoPanel(Object person) {
    	stateCB.setVisible(true);
        currentPerson = person;

        if (person instanceof CustomerAgent) {
            CustomerAgent customer = (CustomerAgent) person;
            stateCB.setText("Hungry?");
            stateCB.setSelected(customer.getGui().isHungry()); 
            stateCB.setEnabled(!customer.getGui().isHungry()); 
            infoLabel.setText(
               "<html><pre>     Name: " + customer.getName() + " </pre></html>");
        }
        
        if (person instanceof WaiterAgent) {
            WaiterAgent waiter = (WaiterAgent) person;
            stateCB.setText("Break");
            stateCB.setSelected(waiter.getGui().isBreak());
            stateCB.setEnabled(!waiter.getGui().isBreak());
            infoLabel.setText(
               "<html><pre>     Name: " + waiter.getName() + " </pre></html>");  
        }
        
        
        
        infoPanel.validate();
    }
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox,  it will make him hungry
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
        
        if(e.getSource() == stateCB) {
        	if (currentPerson instanceof WaiterAgent) {
        		WaiterAgent w = (WaiterAgent) currentPerson;
        		if(stateCB.getText() == "Break") {
	        		stateCB.setText("Return");
	        		//stateCB.setEnabled(true);
	        		stateCB.setSelected(false);
	        		w.getGui().setOnBreak();
        		}
        		else if(stateCB.getText() == "Return") {
	        		w.getGui().returnFromBreak();
	        		stateCB.setText("Break");
	        		stateCB.setEnabled(true);
	        		stateCB.setSelected(false);
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
    public void setCustomerEnabled(CustomerAgent c) {
        if (currentPerson instanceof CustomerAgent) {
            CustomerAgent cust = (CustomerAgent) currentPerson;
            if (c.equals(cust)) {
            	stateCB.setEnabled(true);
            	stateCB.setSelected(false);
            }
        }
    }
  
    public void setWaiterEnabled(WaiterAgent w) {
    	if (currentPerson instanceof WaiterAgent) {
    		WaiterAgent waiter = (WaiterAgent) currentPerson;
    		if (w.equals(waiter)) {
    			stateCB.setEnabled(true);
    			stateCB.setSelected(false);
    		}
    	}
    }
    
    /**
     * Main routine to get gui started
     */
    public static void main(String[] args) {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("CSCI201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}