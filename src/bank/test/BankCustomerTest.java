package bank.test;

import java.util.HashMap;
import java.util.Map;

import bank.Bank;
import bank.BankTellerRole;
import bank.interfaces.BankTeller;
import bank.test.mock.MockBankCustomer;
import bank.test.mock.MockBankManager;
import bank.test.mock.MockBankTeller;
import bank.test.mock.*;
import junit.framework.*;

public class BankTest extends TestCase {

	//these are instantiated for each test separately via the setUp() method.
	MockBankCustomer customer;
	MockBankManager manager;
	MockBankTeller teller;
	
	Map<String, Integer> groceryList;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		manager = new MockBankManager("mockbankmanager");
		teller = new MockBankTeller("mockbankteller");		
		//customer = new MockBankCustomer("mockcustomer",300,0);
	}	
	
	//Deposit w/ Account
	public void testOneBankInteraction(){
		//teller size should be empty
		assertEquals("Bank should have empty tellers. Instead, the Market's event log reads: "
				+ market.log.toString(), 1, market.log.size());
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
		customer = new MockBankCustomer("WantsToDeposit",300,0);
		teller.registerNumber = -1; //teller not at register
		
		assertEquals("Teller should not be at a register",teller.registerNumber, -1);
		teller.msgGoToRegister(1);
		assertFalse("Teller scheduler should return true,
				teller.pickAndExecuteAction(),true);
		assertEquals("Teller should be at register 1",teller.registerNumber, 1);
		
		
		
		
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
