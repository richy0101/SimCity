package restaurant.nakamuraRestaurant;

import agent.Agent;
import agent.Role;
import restaurant.nakamuraRestaurant.helpers.Check;
import restaurant.nakamuraRestaurant.helpers.Check.state;
import restaurant.nakamuraRestaurant.helpers.Menu;
import restaurant.nakamuraRestaurant.interfaces.Cashier;
import restaurant.nakamuraRestaurant.interfaces.Customer;
import restaurant.nakamuraRestaurant.interfaces.Market;
import restaurant.nakamuraRestaurant.interfaces.Waiter;
import restaurant.nakamuraRestaurant.test.mock.EventLog;
import restaurant.nakamuraRestaurant.test.mock.LoggedEvent;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Cook Agent
 */
public class NakamuraCashierAgent extends Agent implements Cashier{
	public List<Check> Checks
	= Collections.synchronizedList(new ArrayList<Check>());
	public List<MarketBill> Bills = Collections.synchronizedList(new ArrayList<MarketBill>());

	private Menu menu;
	private String name;
	private double cash;
	Timer timer = new Timer();
	public EventLog log;

//	public HostGui hostGui = null;
//  private List<WaiterAgent> waiters = new ArrayList<WaiterAgent>();

	public NakamuraCashierAgent(String name) {
		super();

		this.name = name;
		menu = new Menu();
		cash = 50.00;
		log = new EventLog();
	}

	public String getCookName() {
		return name;
	}

	public String getName() {
		return name;
	}
	
	public double getCash() {
		return cash;
	}
	
	public void setMenu(Menu m) {
		this.menu = m;
	}

	public void msgComputeCheck(Waiter w, Customer c, String choice) {
		print("Received msgComputeCheck");
		Double total = 0.0;
		
		synchronized(Checks) {
			for(Check check : Checks) {
				if(check.getCustomer() == c) {
					total += check.getTotal();
				}
			}
		}
		print("Old debt: $" + total);
		Checks.add(new Check(w, c, choice, total));
		log.add(new LoggedEvent("Received msgComputeCheck from Waiter"));
		stateChanged();
	}

	public void msgPayment(Customer c, Check check, double payment) {
		print("Received msgPayment");
		if(check.getTotal() == payment) {
			check.setState(state.paid);
			log.add(new LoggedEvent("Received msgPayment from customer. Payment = $" + payment));
		}
		else {
			check.setState(state.shortChange);
			log.add(new LoggedEvent("No payment from customer"));
		}
		cash += payment;
		stateChanged();
	}
	
	public void msgMarketBill(Market market, double marketpayment) {
		print("Received msgMarketBill");
		Bills.add(new MarketBill(market, marketpayment));
		log.add(new LoggedEvent("Received msgMarketBill. Total = $" + marketpayment));
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		
		synchronized(Checks) {
			for(Check c : Checks) {
				if(c.getState() == state.paid) {
					GiveChange(c);
					return true;
				}
			}
			
			for(Check c : Checks) {
				if(c.getState() == state.shortChange) {
					PayLater(c);
					return true;
				}
			}
			
			for (Check c : Checks) {
				if (c.getState() == state.pending) {
					Calculate(c);
					return true;
				}
			}
		}
		
		synchronized(Bills) {
			for(MarketBill m : Bills) {
				if(cash >= m.payment) {
					m.market.msgHeresPayment(m.payment);
					cash -= m.payment;
					log.add(new LoggedEvent("Paid Bill. Total = $" + m.getPayment()));
					Bills.remove(m);					
					return true;
				}
			}
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void Calculate(Check check) {
		print("Calculating Check");
		check.setTotal(menu.prices.get(check.getChoice()));
		check.getWaiter().msgCheckReady(check);
		check.setState(state.delivered);
		log.add(new LoggedEvent("Calculated check. Total = $" + check.getTotal()));
	}

	private void GiveChange(Check check) {
		print("Giving Change");
		check.getCustomer().msgHeresChange(check.getChange());
		Checks.remove(check);
		log.add(new LoggedEvent("Deleted check."));
	}
	
	private void PayLater(Check check) {
		print("Pay Later");
		check.getCustomer().msgPayNextTime(check.getTotal());
		check.setState(state.debt);
		log.add(new LoggedEvent("Let customer pay later. Debt = $" + check.getTotal()));
	}

	public class MarketBill {
		Market market;
		double payment;
		
		MarketBill (Market market, double p) {
			this.market = market;
			payment = p;
		}
		public double getPayment() {
			return payment;
		}
		public Market getMarket() {
			return market;
		}
	}
}