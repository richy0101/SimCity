package city.helpers;

import home.LandlordRole;
import city.PersonAgent;
import city.UnemployedRole;
import market.MarketCustomerRole;
import agent.Role;
import bank.BankTellerRole;
import restaurant.shehRestaurant.ShehCookRole;
import restaurant.stackRestaurant.*;


import restaurant.huangRestaurant.HuangCookRole;
import restaurant.huangRestaurant.HuangCustomerRole;
import restaurant.huangRestaurant.HuangWaiterNormalRole;
import restaurant.huangRestaurant.HuangWaiterSharedRole;
import restaurant.stackRestaurant.StackCookRole;
import restaurant.stackRestaurant.StackWaiterNormalRole;
import restaurant.stackRestaurant.StackWaiterSharedRole;


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
		else if(role.equals("HuangRestaurant")) {
			newRole = new HuangCustomerRole("HuangRestaurant");
		}
		else if(role.equals("Market1") || role.equals("Market2")) {
			newRole = new MarketCustomerRole(p.getGroceriesList(), role);
		}
		else if(role.equals("HuangWaiterNormal")) {
			newRole = new HuangWaiterNormalRole("HuangRestaurant");
			return newRole;
		}
		else if (role.equals("HuangWaiterShared")) {
			newRole = new HuangWaiterSharedRole("HuangRestaurant");
			return newRole;
		}
		else if (role.equals("HuangCook")) {
			newRole = new HuangCookRole("HuangRestaurant");
			return newRole;
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
//		else if(role.equals("ShehWaiterNormal")) {
//			newRole = new ShehWaiterNormalRole("ShehRestaurant");
//			return newRole;
//		}
//		else if (role.equals("ShehWaiterShared")) {
//			newRole = new ShehWaiterSharedRole("ShehRestaurant");
//			return newRole;
//		}
//		else if (role.equals("ShehCook")) {
//			newRole = new ShehCookRole("ShehRestaurant");
//			return newRole;
//		}
//		else if(role.equals("TanWaiterNormal")) {
//			newRole = new TanWaiterNormalRole("TanRestaurant");
//			return newRole;
//		}
//		else if (role.equals("TanWaiterShared")) {
//			newRole = new TanWaiterSharedRole("TanRestaurant");
//			return newRole;
//		}
//		else if (role.equals("TanCook")) {
//			newRole = new TanCookRole("TanRestaurant");
//			return newRole;
//		}
//		else if(role.equals("NakamuraWaiterNormal")) {
//			newRole = new NakamuraWaiterNormalRole("NakamuraRestaurant");
//			return newRole;
//		}
//		else if (role.equals("NakamurakWaiterShared")) {
//			newRole = new NakamuraWaiterSharedRole("NakamuraRestaurant");
//			return newRole;
//		}
//		else if (role.equals("NakamuraCook")) {
//			newRole = new NakamuraCookRole("NakamuraRestaurant");
//			return newRole;
//		}
//		else if(role.equals("PhillipsWaiterNormal")) {
//			newRole = new PhillipsWaiterNormalRole("PhillipsRestaurant");
//			return newRole;
//		}
//		else if (role.equals("PhillipsWaiterShared")) {
//			newRole = new PhillipsWaiterSharedRole("PhillipsRestaurant");
//			return newRole;
//		}
//		else if (role.equals("PhillipsCook")) {
//			newRole = new PhillipsCookRole("PhillipsRestaurant");
//			return newRole;
//		}
//		else if(role.equals("HuangWaiterNormal")) {
//			newRole = new HuangWaiterNormalRole("HuangRestaurant");
//			return newRole;
//		}
//		else if (role.equals("HuangWaiterShared")) {
//			newRole = new HuangWaiterSharedRole("HuangRestaurant");
//			return newRole;
//		}
//		else if (role.equals("HuangCook")) {
//			newRole = new HuangCookRole("HuangRestaurant");
//			return newRole;
//		}
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
		else if (role.equals("LandlordA")) {
			newRole = new LandlordRole("AppartmentA", 1);
			return newRole;
		}
		else if (role.equals("LandlordB")) {
			newRole = new LandlordRole("AppartmentB", 2);
			return newRole;
		}
		else if (role.equals("LandlordC")) {
			newRole = new LandlordRole("AppartmentC", 3);
			return newRole;
		}
		newRole.setPerson(p);
		//print("Set role complete.");
		return newRole;
	}
};