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
    private StackCookRole cook = new StackCookRole("Chef Boyardee");
    private StackWaiterRole waiter = new StackWaiterRole("Garcon");
    private StackCashierRole cashier = new StackCashierRole("Mr.Moneybags");
    private StackMarketRole market = new StackMarketRole("McNugs", 100, 100, 100, 100);
    
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
		host.msgAddWaiter(waiter);
		market.setCashier(cashier);
        cook.msgAddMarket(market);
		
		hostPerson.startThread();
		cookPerson.startThread();
		waiterPerson.startThread();
		marketPerson.startThread();
//		new Role wtvRole;
//		new PersonAgent p PersonAgent(wtvRole);
//		wtvRole.setPerson(p);
//		p.startThread();

		
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
