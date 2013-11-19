package home;

import java.util.*;

import agent.Role;
import home.interfaces.Landlord;
import home.*;

public class LandlordRole extends Role implements Landlord {
    
	private class MyTenant{
		HomePersonRole inhabitant;
	 	double moneyOwed;
	 	PayState state;
	 	
	 	public void Tenant(HomePersonRole tenant, double money, PayState state){
	 		this.inhabitant = tenant;
	 		this.moneyOwed = money;
	 		this.state = state;
	 	}
	}
	private List<MyTenant> tenants = new ArrayList<MyTenant>();//Collections.synchronizedList(new ArrayList<MyTenant>());
	public enum PayState {NeedsToPay,WaitingForPayment,OwesMoney,PayLater,NothingOwed}
	private double funds = 0;
	
	//Messages
	public void msgNeedsToPayRent(HomePersonRole person, double moneyOwed){
		/*** How do we add new tenants to the list? ***
         bool newTenant = true;
         for(Tenant t : tenants){
         if(t.inhabitant == person){
         newTenant = false;
         }
         }
         if(newTenant == true){
         tenants.add(person,moneyOwed,PayState.NeedsToPay);
         }
         */
		for(MyTenant t : tenants){
			if(t.inhabitant == person){
				t.state = PayState.NeedsToPay;
				t.moneyOwed = moneyOwed;
			}
		}
		stateChanged();
	}
	public void msgHereIsRent(HomePersonRole person, double money){
		for(MyTenant t : tenants){
			if(t.inhabitant == person){
				if(money == t.moneyOwed){
					funds += money;
					t.moneyOwed -= money;
					t.state = PayState.NothingOwed;
				}
				else{
                    t.moneyOwed -= money;
					t.state = PayState.OwesMoney;
                }
			}
		}
		stateChanged();
        
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		synchronized(this.tenants){
			for(int i=0;i<tenants.size();i++){
				if(tenants.get(i).state == PayState.NeedsToPay){
					payRent(tenants.get(i));
					return true;
				}
			}
		}
		synchronized(this.tenants){
			for(int i=0;i<tenants.size();i++){
				if(tenants.get(i).state == PayState.OwesMoney){
					payRentLater(tenants.get(i));
					return true;
				}
			}
		}
		return false;
	}
	//Actions
	public void payRent(MyTenant tenant){
		tenant.inhabitant.msgPayRent(tenant.moneyOwed);
		tenant.state = PayState.WaitingForPayment;
		stateChanged();
	}
	public void payRentLater(MyTenant tenant){
		tenant.inhabitant.msgPayRentLater(tenant.moneyOwed);
		tenant.state = PayState.PayLater;
		stateChanged();
	}
}