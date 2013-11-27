package city.test.mock;

import market.MarketRole;
import agent.Agent;
import city.PersonAgent;
import city.interfaces.*;


public class MockRole extends Mock implements RoleInterface {

	public MockRole(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgJobDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPerson(PersonAgent p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGotHungry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHost(Agent agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCashier(Agent agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMarket(MarketRole worker) {
		// TODO Auto-generated method stub
		
	}

	
}
