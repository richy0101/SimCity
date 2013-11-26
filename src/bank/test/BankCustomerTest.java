package bank.test;

import java.util.HashMap;
import java.util.Map;

import market.test.mock.MockPerson;
import bank.Bank;
import bank.BankCustomerRole;
import bank.BankTellerRole;
import bank.interfaces.BankTeller;
import bank.test.mock.*;
import junit.framework.*;

public class BankCustomerTest extends TestCase {

	//these are instantiated for each test separately via the setUp() method.
	BankCustomerRole customer;
	MockBankManager manager;
	MockBankTeller teller;
	MockPerson person;
	
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		manager = new MockBankManager("mockbankmanager");
		teller = new MockBankTeller("mockbankteller");		
		person = new MockPerson("person");
		//customer = new MockBankCustomer("mockcustomer",300,0);
	}	
	
	//Deposit w/ Account
	public void testOneBankInteraction(){
		customer = new BankCustomerRole("WantsToDeposit",300,0,"FakeCustomer");
		customer.manager = manager;
		customer.setPerson(person);
		customer.setAccountNumber(1);
		
		//precondition
		assertEquals("Customers account number should be 1", customer.getAccountNumber(),1);
		assertEquals("Customer should have the manager from setUp()", customer.manager,manager);
		assertEquals("Customer's state should be DoingNothing ",customer.getState(),"DoingNothing");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be waiting ",customer.getState(),"Waiting");
		
		assertEquals("Customer's teller number to go to should be -1 (null)",customer.getTellerNumber(),-1);
		customer.msgHowCanIHelpYou(teller, 1);
		assertEquals("Customer's teller number to go to should be 1",customer.getTellerNumber(),1);
		//assertEquals("Customer's x gui position/destination should be 450",customer.customerGui.getxDestination(),customer.customerGui.getxTeller());
		//assertEquals("Customer's y gui position/destination should be 450",customer.customerGui.getyDestination(),customer.customerGui.getyTeller());
		
		//customer.pickAndExecuteAnAction();
		
		//assertEquals("Customer's state should be waiting ",customer.getState(),"");
		
		
		//teller initial state Arrived At Work
		//teller state = AtManager 
		//manager messages msgGoToRegister
		//teller state = GoingToRegister
		//teller state = ReadyForCustomers;
		//customers initial state should be "DoingNothing"
		//customer messages manager.msgINeedAssistance
		//customer state = Waiting
		//managers initial state should be "Idle"
		//customers.size should be empty
		//manager adds customer to customers list
		//customers.size should be one
		//teller size should be one
		//myteller initial state should be Idle
		//manager messages teller .msgAssigningCustomer
		//myteller state should be busy
		//manager changes tellerstate busy
		//manager removes customer from customers
		//customer size should be empty
		//teller's customer list should be empty
		//teller receives .msgAssigningCustomer
		//teller adds customer
		//mycustomer state should initially be NeedingAssistance
		//teller messages customer .msgHowCanIHelpYou?
		//mycustomerstate = AskedAssistance
		//customerstate = GoingToTeller
		//customer receives message from gui msgAtTeller
		//customer state = BeingHelped
		//customer messages teller msgOpenAccount
		//account should not exist for user
		//mycustomerstate == OpeningAccount
		//teller messages customer .msgHereIsYourAccount
		//mycustomer state == OpenedAccount	
		
	}
	
	//Deposit w/o Account
	public void testTwoBankInteraction(){
		/*customer = new BankCustomerRole("WantsToDeposit",300,0);
		
		assertEquals("Customer should not be assigned a bank teller",customer.registerNumber, -1);
		assertEquals("Customer should not be at manager ",customer.registerNumber, -1);
		customer.msgGoToRegister(1);
		assertFalse("Teller scheduler should return true,
				teller.pickAndExecuteAction(),true);
		assertEquals("Teller should be at register 1",teller.registerNumber, 1);
		*/
		
		
		
	}
	
	//Withdraw w/ Account
	public void testThreeBankInteraction(){
		
	}
	
	//Withdraw w/o Account
	public void testFourBankInteraction(){
		
	}
	
	//Withdraw w/ < $100 in Account
	public void testFiveBankInteraction(){
		
	}
	
	//Loan w/ Good Credit (No taking a loan)
	public void testSixBankInteraction(){
		
	}
	
	//Loan w/ Bad Credit (Has taken loan)
	public void testSevenBankInteraction(){
		
	}
	
	//Bank tellers are busy
	public void testEightBankInteraction(){
		
	}
	
	//No Bank Tellers
	public void testNineBackInteraction(){
		
	}

}
