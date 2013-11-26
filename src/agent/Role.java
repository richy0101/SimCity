package agent;
import market.MarketRole;
import city.interfaces.Person;

public class Role {
	Person myPerson;
	public void setPerson(Person person) {
		myPerson = person; 
		System.out.println(myPerson.getName() + "WOAH");
	}
	
	public Person getPersonAgent() {
		return myPerson;
	}
	
	protected void stateChanged() {
		myPerson.stateChanged();
	}
	
	public boolean pickAndExecuteAnAction() {
		return true;
	}
	
	protected void print(String msg) {
		myPerson.print(msg);
	}
	
	protected void Do(String msg) {
		myPerson.Do(msg);
	}
	//Restaurant Methods
	public void msgGotHungry() {
		
	}

	public void setHost(Agent agent) {
		// TODO Auto-generated method stub
		
	}

	public void setCashier(Agent agent) {
		// TODO Auto-generated method stub
		
	}
	//Bank Methods
	public void setManager(Agent agent) {
		
	}

	public void setMarket(MarketRole marketRole) {
		// TODO Auto-generated method stub
		
	}
}
