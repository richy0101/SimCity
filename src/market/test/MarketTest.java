package market.test;

import market.MarketRole;
import market.test.mock.MockMarketCustomer;
import junit.framework.*;

public class MarketTest extends TestCase {

	//these are instantiated for each test separately via the setUp() method.
	MarketRole market;
	MockMarketCustomer customer;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		market = new MarketRole();		
		customer = new MockMarketCustomer("mockcustomer");
	}	

	
	public void testOneMarketOrder(){
		
		//check preconditions
		assertEquals("Market should have 0 orders. It doesn't", market.getMyOrders().size(), 0);
		assertEquals("Market should have 4 kinds of food. It doesn't", market.getInventory().size(), 4);
	}	
}
