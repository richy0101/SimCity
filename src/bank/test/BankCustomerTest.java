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
		customer = new BankCustomerRole("Deposit",300.0,0,"FakeCustomer");
		customer.manager = manager;
		customer.setPerson(person);
		customer.setAccountNumber(1);
		assertTrue(customer.getPersonAgent().equals(person));
		customer.getPersonAgent().setFunds(400.0);
		
		//precondition
		assertEquals("Customers account number should be 1", customer.getAccountNumber(),1);
		assertEquals("Customer should have the manager from setUp()", customer.manager,manager);
		assertEquals("Customer's state should be DoingNothing ",customer.getState(),"DoingNothing");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be waiting ",customer.getState(),"Waiting");
		assertEquals("Customer's x gui position/destination should be at x manager",
				customer.customerGui.getxDestination(),400);
		assertEquals("Customer's y gui position/destination should be at y manager",
				customer.customerGui.getyDestination(),68);
		
		
		assertEquals("Customer's teller number to go to should be -1 (null)",customer.getTellerNumber(),-1);
		customer.msgHowCanIHelpYou(teller, 1);
		assertEquals("Customer's teller number to go to should be 1",customer.getTellerNumber(),1);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's x gui position/destination should be x teller",
				customer.customerGui.getxDestination(),customer.customerGui.getxTeller());
		assertEquals("Customer's y gui position/destination should be y teller",
				customer.customerGui.getyDestination(),customer.customerGui.getyTeller());
		
		customer.msgAtTeller();
		
		assertEquals("Customer's state should be BeingHelped ",customer.getState(),"BeingHelped");
		assertEquals("Customer's task should be Deposit ",customer.getTask(),"Deposit");
		
		assertEquals("Customer's funds should be 400 before depositing",
				customer.getPersonAgent().getFunds(),400.0);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be WaitingForHelpResponse ",
				customer.getState(),"WaitingForHelpResponse");
		assertEquals("Customer's funds should be 100 now that hes deposited",
				customer.getPersonAgent().getFunds(),100.0);
		
		customer.msgDepositSuccessful();
		
		assertEquals("Customer's state should be InTransit ",customer.getState(),"InTransit");
		
		customer.msgAnimationFinishedLeavingBank();
		
		assertEquals("Customer's x gui position/destination should be at x exit",
				customer.customerGui.getxDestination(),customer.customerGui.getxExit());
		assertEquals("Customer's y gui position/destination should be at y exit",
				customer.customerGui.getyDestination(),customer.customerGui.getyExit());
		assertEquals("Customer's state should be Gone ",customer.getState(),"Gone");
		
	}
	
	//Deposit w/o Account
	public void testTwoBankInteraction(){
		customer = new BankCustomerRole("Deposit",300.0,0,"FakeCustomer");
		customer.manager = manager;
		customer.setPerson(person);
		customer.setAccountNumber(0);
		assertTrue(customer.getPersonAgent().equals(person));
		customer.getPersonAgent().setFunds(400.0);
		
		//precondition
		assertEquals("Customers account number should not be set- 0", customer.getAccountNumber(),0);
		assertEquals("Customer should have the manager from setUp()", customer.manager,manager);
		assertEquals("Customer's state should be DoingNothing ",customer.getState(),"DoingNothing");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be waiting ",customer.getState(),"Waiting");
		assertEquals("Customer's x gui position/destination should be at x manager",
				customer.customerGui.getxDestination(),400);
		assertEquals("Customer's y gui position/destination should be at y manager",
				customer.customerGui.getyDestination(),68);
		
		
		assertEquals("Customer's teller number to go to should be -1 (null)",customer.getTellerNumber(),-1);
		
		customer.msgHowCanIHelpYou(teller, 1);
		
		assertEquals("Customer's teller number to go to should be 1",customer.getTellerNumber(),1);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's x gui position/destination should be x teller",
				customer.customerGui.getxDestination(),customer.customerGui.getxTeller());
		assertEquals("Customer's y gui position/destination should be y teller",
				customer.customerGui.getyDestination(),customer.customerGui.getyTeller());
		
		customer.msgAtTeller();
		
		assertEquals("Customer's state should be BeingHelped ",customer.getState(),"BeingHelped");
		assertEquals("Customer's task should be Deposit ",customer.getTask(),"Deposit");
		
		assertEquals("Customer's funds should be 400 before depositing",
				customer.getPersonAgent().getFunds(),400.0);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be WaitingForHelpResponse ",customer.getState(),"WaitingForHelpResponse");
		assertNotSame("Customer's funds should not be 100 because he doesn't have an open account",customer.getPersonAgent().getFunds(),100.0);
		
		customer.msgHereIsYourAccount(1);
		
		assertEquals("Customer's account number should now be 1 ",customer.getAccountNumber(),1);
		assertEquals("Customer's state should now be BeingHelped ",customer.getState(),"BeingHelped");
		
		customer.pickAndExecuteAnAction();
		
		customer.msgDepositSuccessful();
		
		assertEquals("Customer's state should be InTransit ",customer.getState(),"InTransit");
		
		customer.msgAnimationFinishedLeavingBank();
		
		assertEquals("Customer's state should be Gone ",customer.getState(),"Gone");
		
		
		
		
	}
	
	//Withdraw w/ Account
	public void testThreeBankInteraction(){
		customer = new BankCustomerRole("Withdraw",300.0,0,"FakeCustomer");
		customer.manager = manager;
		customer.setPerson(person);
		customer.setAccountNumber(1);
		assertTrue(customer.getPersonAgent().equals(person));
		customer.getPersonAgent().setFunds(400.0);
		
		//precondition
		assertEquals("Customers account number should be 1", customer.getAccountNumber(),1);
		assertEquals("Customer should have the manager from setUp()", customer.manager,manager);
		assertEquals("Customer's state should be DoingNothing ",customer.getState(),"DoingNothing");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be waiting ",customer.getState(),"Waiting");
		assertEquals("Customer's x gui position/destination should be at x manager",
				customer.customerGui.getxDestination(),400);
		assertEquals("Customer's y gui position/destination should be at y manager",
				customer.customerGui.getyDestination(),68);
		
		
		assertEquals("Customer's teller number to go to should be -1 (null)",customer.getTellerNumber(),-1);
		customer.msgHowCanIHelpYou(teller, 1);
		assertEquals("Customer's teller number to go to should be 1",customer.getTellerNumber(),1);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's x gui position/destination should be x teller",
				customer.customerGui.getxDestination(),customer.customerGui.getxTeller());
		assertEquals("Customer's y gui position/destination should be y teller",
				customer.customerGui.getyDestination(),customer.customerGui.getyTeller());
		
		customer.msgAtTeller();
		
		assertEquals("Customer's state should be BeingHelped ",customer.getState(),"BeingHelped");
		assertEquals("Customer's task should be Withdraw ",customer.getTask(),"Withdraw");
		
		assertEquals("Customer's funds should be 400 before withdrawing",
				customer.getPersonAgent().getFunds(),400.0);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be WaitingForHelpResponse ",
				customer.getState(),"WaitingForHelpResponse");
		
		customer.msgHereAreFunds(customer.getMoneyToWithdraw());
		
		assertEquals("Customer's funds should be 500 now that hes withdrawn 100",
				customer.getPersonAgent().getFunds(),500.0);
		assertEquals("Customer's state should be InTransit ",customer.getState(),"InTransit");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's x gui position/destination should be at x exit",
				customer.customerGui.getxDestination(),customer.customerGui.getxExit());
		assertEquals("Customer's y gui position/destination should be at y exit",
				customer.customerGui.getyDestination(),customer.customerGui.getyExit());
		
		customer.msgAnimationFinishedLeavingBank();
		
		assertEquals("Customer's state should be Gone ",customer.getState(),"Gone");
	}
	
	//Withdraw w/o Account
	public void testFourBankInteraction(){
		customer = new BankCustomerRole("Withdraw",300.0,0,"FakeCustomer");
		customer.manager = manager;
		customer.setPerson(person);
		customer.setAccountNumber(0);
		assertTrue(customer.getPersonAgent().equals(person));
		customer.getPersonAgent().setFunds(400.0);
		
		//precondition
		assertEquals("Customers account number should be 0", customer.getAccountNumber(),0);
		assertEquals("Customer should have the manager from setUp()", customer.manager,manager);
		assertEquals("Customer's state should be DoingNothing ",customer.getState(),"DoingNothing");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be waiting ",customer.getState(),"Waiting");
		assertEquals("Customer's x gui position/destination should be at x manager",
				customer.customerGui.getxDestination(),400);
		assertEquals("Customer's y gui position/destination should be at y manager",
				customer.customerGui.getyDestination(),68);
		
		
		assertEquals("Customer's teller number to go to should be -1 (null)",customer.getTellerNumber(),-1);
		customer.msgHowCanIHelpYou(teller, 1);
		assertEquals("Customer's teller number to go to should be 1",customer.getTellerNumber(),1);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's x gui position/destination should be x teller",
				customer.customerGui.getxDestination(),customer.customerGui.getxTeller());
		assertEquals("Customer's y gui position/destination should be y teller",
				customer.customerGui.getyDestination(),customer.customerGui.getyTeller());
		
		customer.msgAtTeller();
		
		assertEquals("Customer's state should be BeingHelped ",customer.getState(),"BeingHelped");
		assertEquals("Customer's task should be Withdraw ",customer.getTask(),"Withdraw");
		
		assertEquals("Customer's funds should be 400 before withdrawing",
				customer.getPersonAgent().getFunds(),400.0);
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's state should be WaitingForHelpResponse ",
				customer.getState(),"WaitingForHelpResponse");
		assertNotSame("Customer's funds should not be 500 because he doesn't have an open account",
				customer.getPersonAgent().getFunds(),500.0);
		
		customer.msgHereIsYourAccount(1);
		
		assertEquals("Customer's account number should now be 1 ",customer.getAccountNumber(),1);
		assertEquals("Customer's state should now be BeingHelped ",customer.getState(),"BeingHelped");
		
		customer.pickAndExecuteAnAction();
		
		customer.msgHereAreFunds(customer.getMoneyToWithdraw());
		
		assertEquals("Customer's funds should be 500 now that hes withdrawn 100",
				customer.getPersonAgent().getFunds(),500.0);
		assertEquals("Customer's state should be InTransit ",customer.getState(),"InTransit");
		
		customer.pickAndExecuteAnAction();
		
		assertEquals("Customer's x gui position/destination should be at x exit",
				customer.customerGui.getxDestination(),customer.customerGui.getxExit());
		assertEquals("Customer's y gui position/destination should be at y exit",
				customer.customerGui.getyDestination(),customer.customerGui.getyExit());
		
		customer.msgAnimationFinishedLeavingBank();
		
		assertEquals("Customer's state should be Gone ",customer.getState(),"Gone");
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
	
	//Bank Tellers leave work and get paid
	public void testNineBackInteraction(){
		
		
	}

}
