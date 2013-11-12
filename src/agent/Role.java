package agent;

public class Role {
	Person myPerson;
	public setPerson(Person person) {
		myPerson = person; 
	}
	
	public PersonAgent getPersonAgent() {
		return myPerson;
	}
	private void stateChanged() {
		myPerson.stateChanged;
	}

}
