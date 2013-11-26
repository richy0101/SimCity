package bank.test;

import java.util.HashMap;
import java.util.Map;

import bank.Bank;
import bank.BankTellerRole;
import bank.interfaces.BankTeller;
import bank.test.mock.MockBankCustomer;
import bank.test.mock.MockBankManager;
import bank.test.mock.MockBankTeller;
import market.MarketRole;
import market.MarketRole.orderState;
import market.test.mock.*;
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
		customer = new MockBankCustomer("mockcustomer");
		
		groceryList = new HashMap<String, Integer>();
		groceryList.put("Steak", 1);
		groceryList.put("Chicken", 5);
	}	
	
	//Deposit w/ Account
	public void testOneBankOrder(){
		
	}
	
	//Deposit w/o Account
	public void testTwoBankOrder(){
		
	}
	
	//Withdraw w/ Account
	public void testThreeBankOrder(){
		
	}
	
	//Withdraw w/o Account
	public void testFourBankOrder(){
		
	}
	
	//Withdraw w/ < $100 in Account
	public void testFiveBankOrder(){
		
	}
	
	//Loan w/ Good Credit (No taking a loan)
	public void testSixBankOrder(){
		
	}
	
	//Loan w/ Bad Credit (Has taken loan)
	public void testSevenBankOrder(){
		
	}
	
	//Bank tellers are busy
	public void testEightBankOrder(){
		
	}

}
