package restaurant.stackRestaurant;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import restaurant.Restaurant;
import restaurant.stackRestaurant.*;
import city.PersonAgent;


public class StackRestaurant extends Restaurant {

	private String name;
	
	 //Host, cook, waiters and customers
    private StackHostRole host = new StackHostRole();
    private StackCookRole cook = new StackCookRole();
    private StackWaiterRole waiter = new StackWaiterRole();
    private StackCashierRole cashier = new StackCashierRole();
    private StackMarketRole market = new StackMarketRole(100, 100, 100, 100);
    
    private PersonAgent hostPerson = new PersonAgent(host);
    private PersonAgent cookPerson = new PersonAgent(cook);
    private PersonAgent waiterPerson = new PersonAgent(waiter);
    private PersonAgent marketPerson = new  PersonAgent(market);
    private PersonAgent cashierPerson = new PersonAgent(cashier);
    

    private Vector<PersonAgent> customers = new Vector<PersonAgent>();
    private Vector<PersonAgent> waiters = new Vector<PersonAgent>();
	
	public StackRestaurant(String name) {
		this.name = name;
		
		waiters.add(waiterPerson);
		
		host.setPerson(hostPerson);
		cook.setPerson(cookPerson);
		waiter.setPerson(waiterPerson);
		market.setPerson(marketPerson);
		cashier.setPerson(cashierPerson);
		
		waiter.setCashier(cashier);
		waiter.setCook(cook);
		waiter.setHost(host);
		
		market.setCashier(cashier);
      
		
		hostPerson.startThread();
		cookPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		marketPerson.startThread();
		
		cook.msgAddMarket(market);
		host.msgAddWaiter(waiter);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Object getHost() {
		// TODO Auto-generated method stub
		return host;
	}

}
