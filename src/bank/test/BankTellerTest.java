package bank.test;

import java.util.HashMap;
import java.util.Map;

import bank.Bank;
import bank.BankTellerRole;
import bank.interfaces.BankTeller;
import bank.test.mock.MockBankCustomer;
import bank.test.mock.MockBankManager;
//import bank.test.mock.MockBankTeller;
import market.MarketRole;
import market.MarketRole.orderState;
import market.test.mock.*;
import junit.framework.*;

public class BankTellerTest extends TestCase {

	//these are instantiated for each test separately via the setUp() method.
	MockBankCustomer customer;
	MockBankManager manager;
	
	BankTellerRole teller;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		manager = new MockBankManager("mockbankmanager");		
		customer = new MockBankCustomer("mockcustomer",300,0);
		
		teller = new BankTellerRole("bankteller");
	}	
	
	//teller initial state Arrived At Work
	//teller state = AtManager 
	//manager messages msgGoToRegister
	//teller state = GoingToRegister
	
	//teller state = ReadyForCustomers;
	//manager messages teller .msgAssigningCustomer
	
	//teller's customer list should be empty
	//teller receives .msgAssigningCustomer
	//teller adds customer
	//mycustomer state should initially be NeedingAssistance

	//pickandexec
	
	//mycustomerstate = AskedAssistance
	//customerstate = GoingToTeller
	//customer receives message from gui mse
	//customer state = BeingHelped
	//customer messages teller msgOpenAccount
	//account should not exist for user
	//mycustomerstate == OpeningAccount
	//teller messages customer .msgHereIsYourAccount
	//mycustomer state == OpenedAccount	
	
	//Deposit w/ Account
	public void testOneBankInteraction(){	
		
		customer.teller = teller; 
		
			//empty customers
			assertEquals("Teller should have 0 customers in it. It doesn't.", teller.getCustomers().size(), 0);
			
			//empty customer log
			assertEquals("MockCustomer should have an empty event log before the Teller scheduler is called. Instead the MockWaiter's "
					+ "event log reads: " + customer.log.toString(), 0, customer.log.size());
			
			assertFalse("Market scheduler should've returned false. It didn't",
					teller.pick());
	}
	
	//Deposit w/o Account
	public void testTwoBankInteraction(){
		
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
