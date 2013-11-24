package agent;
import city.PersonAgent;

public class Role {
	PersonAgent myPerson;
	public void setPerson(PersonAgent person) {
		myPerson = person; 
		//System.out.println(myPerson.name + "WOAH");
	}
	
	public PersonAgent getPersonAgent() {
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
}
