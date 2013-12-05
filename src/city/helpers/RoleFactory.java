package city.helpers;

import city.PersonAgent;
import city.UnemployedRole;
import market.MarketCustomerRole;
import agent.Role;
import bank.BankTellerRole;
import restaurant.stackRestaurant.*;

public class RoleFactory {
	Role newRole;
	public RoleFactory() {
		newRole = null;
	}
	public Role createRole(String order, PersonAgent p) {
		if(order == "StackRestaurant") {
			this.newRole = new StackCustomerRole("StackRestaurant");
		}
		else if(order == "Market1" || order == "Market2") {
			this.newRole = new MarketCustomerRole(p.getGroceriesList(), order);
		}
		else if(order == "StackWaiterNormal") {
			this.newRole = new StackWaiterNormalRole("StackRestaurant");
			return newRole;
		}
		else if (order == "StackWaiterShared") {
			this.newRole = new StackWaiterSharedRole("StackRestaurant");
			return newRole;
		}
		else if (order == "StackCook") {
			this.newRole = new StackCookRole("StackRestaurant");
			return newRole;
		}
		else if (order == "BankTeller") {
			this.newRole = new BankTellerRole("Bank");
			return newRole;
		}
		else if (order == "Unemployed") {
			this.newRole = new UnemployedRole();
			return newRole;
		}
		newRole.setPerson(p);
		//print("Set role complete.");
		return newRole;
	}
};