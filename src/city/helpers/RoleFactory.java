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
	public Role createRole(String role, PersonAgent p) {
		System.out.println(role);
		if(role.equals("StackRestaurant")) {
			newRole = new StackCustomerRole("StackRestaurant");
		}
		else if(role.equals("Market1") || role.equals("Market2")) {
			newRole = new MarketCustomerRole(p.getGroceriesList(), role);
		}
		else if(role.equals("StackWaiterNormal")) {
			newRole = new StackWaiterNormalRole("StackRestaurant");
			return newRole;
		}
		else if (role.equals("StackWaiterShared")) {
			newRole = new StackWaiterSharedRole("StackRestaurant");
			return newRole;
		}
		else if (role.equals("StackCook")) {
			newRole = new StackCookRole("StackRestaurant");
			return newRole;
		}
		else if (role.equals("BankTeller")) {
			newRole = new BankTellerRole("Bank");
			return newRole;
		}
		else if (role.equals("BankTeller2")) {
			newRole = new BankTellerRole("Bank2");
			return newRole;
		}
		else if (role.equals("Unemployed")) {
			newRole = new UnemployedRole();
			return newRole;
		}
		newRole.setPerson(p);
		//print("Set role complete.");
		return newRole;
	}
};