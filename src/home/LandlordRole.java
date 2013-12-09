package home;

import java.util.*;

import city.helpers.Directory;
import city.interfaces.Person;
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
			if(TenantList.sharedInstance().getTenant(i,apartmentNum).getState() == "NothingOwed"
				|| TenantList.sharedInstance().getTenant(i,apartmentNum).getState() == "OwesMoney"){
				TenantList.sharedInstance().getTenant(i,apartmentNum).setState(PayState.NeedsToPay);
				TenantList.sharedInstance().getTenant(i,apartmentNum).setMoneyOwed(50);
			}
		}
	}
	public void msgHereIsRent(Person person, double money){
		for(int i=0;i<TenantList.sharedInstance().getTenants(apartmentNum).size();i++){
			if(TenantList.sharedInstance().getTenant(i,apartmentNum).getPerson() == person){
				if(money == TenantList.sharedInstance().getTenant(i,apartmentNum).getMoneyOwed()){
					getPersonAgent().setFunds(getPersonAgent().getFunds() + money);
					TenantList.sharedInstance().getTenant(i,apartmentNum).setMoneyOwed(0);
					TenantList.sharedInstance().getTenant(i,apartmentNum).setState(PayState.NothingOwed);
				}
				else{
					getPersonAgent().setFunds(getPersonAgent().getFunds() + money);
					TenantList.sharedInstance().getTenant(i,apartmentNum).setMoneyOwed(
							TenantList.sharedInstance().getTenant(i,apartmentNum).getMoneyOwed() - money);
					TenantList.sharedInstance().getTenant(i,apartmentNum).setState(PayState.OwesMoney);
                }
			}
		}
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		synchronized(TenantList.sharedInstance().getTenants(apartmentNum)){
			for(int i=0;i<TenantList.sharedInstance().getTenants(apartmentNum).size();i++){
				if(TenantList.sharedInstance().getTenant(i,apartmentNum).getState().equals("NeedsToPay")){
					payRent(TenantList.sharedInstance().getTenant(i,apartmentNum));
					return true;
				}
			}
		}
		return false;
	}
	//Actions
	public void payRent(Tenant tenant){
		tenant.getPerson().msgPayRent(this,tenant.getMoneyOwed());
		tenant.setState(PayState.WaitingForPayment);
		stateChanged();
	}

}