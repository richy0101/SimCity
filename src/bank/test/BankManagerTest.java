package bank.test;

import java.util.HashMap;
import java.util.Map;

import bank.Bank;
<<<<<<< HEAD
=======
import bank.BankManagerAgent;
>>>>>>> 83e9ba491623640a152c68f79b43d7b8d8571cb1
import bank.BankTellerRole;
import bank.interfaces.BankTeller;
import bank.test.mock.MockBankCustomer;
import bank.test.mock.MockBankManager;
import bank.test.mock.MockBankTeller;
import market.MarketRole;
import market.MarketRole.orderState;
import market.test.mock.*;
import junit.framework.*;

public class BankManagerTest extends TestCase {

	//these are instantiated for each test separately via the setUp() method.
	BankManagerAgent manager;
	MockBankCustomer customer1;
	MockBankCustomer customer2;
	MockBankCustomer customer3;
	MockBankCustomer customer4;
	MockBankCustomer customer5;
	MockBankCustomer customer6;
	MockBankCustomer customer7;
	MockBankTeller teller1;
	MockBankTeller teller2;
	MockBankTeller teller3;
	MockBankTeller teller4;
	MockBankTeller teller5;
	MockBankTeller teller6;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		
		manager = new MockBankManager("mockbankmanager");
		teller = new BankTellerRole("bankteller");		
		customer = new MockBankCustomer("mockcustomer",300,0);
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
=======
		manager = new BankManagerAgent();
		teller1 = new MockBankTeller("mockbankteller1");	
		teller2 = new MockBankTeller("mockbankteller2");	
		teller3 = new MockBankTeller("mockbankteller3");	
		teller4 = new MockBankTeller("mockbankteller4");	
		teller5 = new MockBankTeller("mockbankteller5");	
		teller6 = new MockBankTeller("mockbankteller6");	
		customer1 = new MockBankCustomer("mockcustomer1",300,0);
		customer2 = new MockBankCustomer("mockcustomer2",300,0);
		customer3 = new MockBankCustomer("mockcustomer3",300,0);
		customer4 = new MockBankCustomer("mockcustomer4",300,0);
		customer5 = new MockBankCustomer("mockcustomer5",300,0);
		customer6 = new MockBankCustomer("mockcustomer6",300,0);
		customer7 = new MockBankCustomer("mockcustomer7",300,0);
	}	
	
	//Manager assigning bankteller to teller register
	public void testOneBankMangerInteraction(){
		assertEquals("Manager should initially have 0 tellers in his list of tellers",manager.tellers.size(),0);
		
	}
	
	//Manager assigning bank customer to a bank teller
	public void testTwoBankManagerInteraction(){
		
	}
	
	//Manager not able to assign customer to bank teller because all tellers are full with customers
	public void testThreeBankManagerInteraction(){
		
	}
	
	//Manager allowing bank tellers to leave when finished work
	public void testFourBackManagerInteraction(){
>>>>>>> 83e9ba491623640a152c68f79b43d7b8d8571cb1
		
	}

}
