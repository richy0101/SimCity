package bank.test;

import java.util.HashMap;
import java.util.Map;

import bank.Bank;
import bank.BankManagerAgent;
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
		
	}

}
