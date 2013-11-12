package agent;
import city.PersonAgent;

public class Role {
	PersonAgent myPerson;
	public void setPerson(PersonAgent person) {
		myPerson = person; 
	}
	
	public PersonAgent getPersonAgent() {
		return myPerson;
	}
	
	protected void stateChanged() {
		myPerson.stateChanged();
	}
	
	protected boolean pickAndExecuteAnAction() {
		return true;
	}
	
	protected void print(String msg) {
		myPerson.print(msg);
	}
	
	protected void Do(String msg) {
		myPerson.Do(msg);
	}
}
