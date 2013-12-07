package home;

import java.util.*;

import city.helpers.Directory;
import agent.Role;
import gui.Building;
import home.gui.LandlordGui;
import home.helpers.TenantList;
import home.helpers.TenantList.Tenant;
import home.interfaces.HomePerson;
import home.interfaces.Landlord;
import home.*;

public class LandlordRole extends Role implements Landlord {
    
	private List<Tenant> tenants = new ArrayList<Tenant>();//Collections.synchronizedList(new ArrayList<MyTenant>());
	public enum PayState {NeedsToPay,WaitingForPayment,OwesMoney,PayLater,NothingOwed};
	int apartmentNum;
	String myLocation;
	
	LandlordGui gui;
	
	public LandlordRole(String location,int apartment){
    	myLocation = location;
    	this.apartmentNum = apartment;
    	tenants = TenantList.sharedInstance().getTenants(apartment);
    	
    	List<Building> buildings = Directory.sharedInstance().getCityGui().getMacroAnimationPanel().getBuildings();
    	gui = new LandlordGui(this);
		for(Building b : buildings) {
			if (b.getName() == myLocation) {
				b.addGui(gui);
			}
		}
	}
	
	//Messages
	public void msgTimeToCollectRent(){
		for(int i=0;i<TenantList.sharedInstance().getTenants(apartmentNum).size();i++){
			TenantList.sharedInstance().getTenant(i,apartmentNum).setPayState(PayState.NeedsToPay);
			TenantList.sharedInstance().getTenant(i,apartmentNum).setMoneyOwed(50);
		}
	}
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
		AccountSystem.sharedInstance().addAccount(uniqueNum);
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
//	TODO have to implement a pay later system
	public void payRentLater(MyTenant tenant){
//		tenant.inhabitant.msgPayRentLater(tenant.moneyOwed);
//		tenant.state = PayState.PayLater;
//		stateChanged();
	}
}