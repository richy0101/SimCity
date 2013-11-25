package agent;
import city.interfaces.Person;

public class Role {
	Person myPerson;
	public void setPerson(Person person) {
		myPerson = person; 
		//System.out.println(myPerson.name + "WOAH");
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
}
