package restaurant.stackRestaurant;

import agent.Role;
import restaurant.CashierAgent;
import restaurant.Restaurant;
import restaurant.stackRestaurant.helpers.Check;
import restaurant.stackRestaurant.helpers.Menu;
import restaurant.stackRestaurant.interfaces.*;
import market.MarketCheck;
import market.interfaces.*;

import java.util.*;


public class StackCashierAgent extends CashierAgent implements Cashier {
	
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private List<MyCheck> checks = Collections.synchronizedList(new ArrayList<MyCheck>());
	private List<MyEmployee> employees = Collections.synchronizedList(new ArrayList<MyEmployee>());
	
	public enum CustomerState
	{NeedComputing, Computed, NeedPaying, Paid};
	
	public enum CheckState
	{NeedPaying, Paid};
	
	public enum EmployeeState
	{NeedPaying, Paid};
	
	Restaurant restaurant;
	
	public String getName() {
		return "Money Machine 5000";
	}
	
	public List<MyCustomer> getCustomers() {
		return customers;
	}
	
	public List<MyCheck> getChecks() {
		return checks;
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(customers) {
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.NeedComputing) {
					computeCheck(customer);
					return true;
				}
			}
		}
		synchronized(customers) {
			for(MyCustomer customer : customers) {
				if(customer.state == CustomerState.NeedPaying) {
					handleCustomerPayment(customer);
					return true;
				}
			}
		}
		synchronized(checks) {
			for(MyCheck check : checks) {
				if(check.state == CheckState.NeedPaying) {
					payMarket(check);
					return true;
				}
			}
		}
		synchronized(employees) {
			for(MyEmployee employee : employees) {
				payEmployee(employee);
				return true;
			}
		}
		return false;
	}
	
	//actions
	private void payMarket(MyCheck check) {
		print("Paying " + check.market + " " + check.check.cost());
		check.market.msgPayForOrder(this, check.check.cost());
		setTill(getTill() - check.check.cost());
		print("The till now has: " + getTill());
		check.state = CheckState.Paid;
	}
	
	private void computeCheck(MyCustomer customer) {
		Check check = new Check(customer.debt + Menu.sharedInstance().getInventoryPrice(customer.choice), customer.customer, customer.choice);
		print("computing check for waiter for " + customer.customer + "'s " + customer.choice + ", totaling to " + check.cost());
		customer.waiter.msgHereIsCheck(check);
		customer.state = CustomerState.Computed;
	}
	
	private void handleCustomerPayment(MyCustomer customer) {
		if(customer.availableMoney < customer.check.cost()) {
			if(customer.availableMoney >=0) {
				customer.debt += (customer.check.cost() - customer.availableMoney);
				setTill(getTill() + customer.availableMoney);
				print("Till now has: " + getTill());
			}
			else {
				customer.debt += (customer.check.cost());
			}
			print(customer.customer + " does not have enough money, and now has a debt of " + customer.debt);
			customer.availableMoney = 0;
		}
		else if (customer.availableMoney > customer.check.cost()) {
			customer.availableMoney -= customer.check.cost();
			setTill(getTill() + customer.check.cost());
			print("Till now has: " + getTill());
		}
		
		customer.customer.msgHereIsChange(customer.availableMoney);
		customer.availableMoney = 0;
		customer.state = CustomerState.Paid;
	}
	
	private void payEmployee(MyEmployee employee) {
		employee.role.msgHereIsPaycheck(100);
		setTill(getTill() - 100);
		employees.remove(employee);
	}

	//messages
	
	public void msgComputeCheck(Waiter waiter, Customer cust, String choice) {
		print("computing business");
		for(MyCustomer customer : customers) {
			if(cust.equals(customer.customer)) {
				customer.choice = choice;
				customer.state = CustomerState.NeedComputing;
				customer.waiter = waiter;
				stateChanged();
				return;
			}
		}
		customers.add(new MyCustomer(waiter, cust, CustomerState.NeedComputing, choice));
		stateChanged();
		
	}
	
	public void msgPayCheck(Customer cust, Check check, double money) {
		for(MyCustomer customer : customers) {
			if(cust.equals(customer.customer)) {
				customer.state = CustomerState.NeedPaying;
				customer.check = check;
				customer.availableMoney = money;
			}
		}
		stateChanged();
	}
	
	public void msgNeedPaycheck(Role role) {
		employees.add(new MyEmployee(role));
		stateChanged();
	}
	
	public void msgGiveBill(MarketCheck check) {
		print("Please pay this bill");
		checks.add(new MyCheck(check.getMarket(), new Check(check.getCost(), check.getChoice()), CheckState.NeedPaying));
		stateChanged();
	}
	
	public double getTill() {
		return restaurant.getTill();
	}

	public void setTill(double till) {
		restaurant.setTill(till);
	}

	public class MyCustomer {
		MyCustomer(Waiter waiter, Customer customer, CustomerState state, String choice) {
			this.customer = customer;
			this.state = state;
			this.choice = choice;
			this.waiter = waiter;
		}
		Check check;
		Waiter waiter;
		Customer customer;
		String choice;
		public double debt = 0;
		public double availableMoney = 0;
		public CustomerState state;
	}
	
	public class MyEmployee {
		MyEmployee(Role role) {
			this.role = role;
		}
		Role role;
	}
	
	public class MyCheck {
		MyCheck(Market market, Check check, CheckState state) {
			this.market = market;
			this.check = check;
			this.state = state;
		}
		Market market;
		public Check check;
		public CheckState state;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
}
