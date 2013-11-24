package market.test;

import java.util.HashMap;
import java.util.Map;

import market.MarketRole;
import market.MarketRole.orderState;
import market.test.mock.*;
import junit.framework.*;

public class MarketTest extends TestCase {

	//these are instantiated for each test separately via the setUp() method.
	MarketRole market;
	MockMarketCustomer customer;
	
	Map<String, Integer> groceryList;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		market = new MarketRole();		
		customer = new MockMarketCustomer("mockcustomer");
		
		groceryList = new HashMap<String, Integer>();
		groceryList.put("Steak", 1);
		groceryList.put("Chicken", 5);
	}	

	
	public void testOneMarketOrder(){

		//Check Preconditions----------------------------------------------------------------
		assertEquals("Market should have 0 orders. It doesn't", market.getMyOrders().size(), 0);
		assertEquals("Market should have 4 kinds of food. It doesn't", market.getInventory().size(), 4);
		
		assertEquals("Market should have an empty event log. Instead, the Market's event log reads: "
						+ market.log.toString(), 0, market.log.size());
		assertEquals("MockMarketCustomer should have an empty event log. Instead, the event log reads: "
						+ customer.log.toString(), 0, customer.log.size());
		
		assertFalse("Market scheduler should've returned false. It didn't",
				market.pickAndExecuteAnAction());

		//Step 1-----------------------------------------------------------------------------
		market.msgGetGroceries(customer, groceryList);

		//Check Postconditions of Step 1/Preconditions of Step 2-----------------------------
		assertEquals("Market should have one logged event. Instead, the Market's event log reads: "
				+ market.log.toString(), 1, market.log.size());
		
		assertEquals("Market should have 1 order. It doesn't", market.getMyOrders().size(), 1);
		
		assertEquals("Order should have state 'Ordered'. State is instead: " + 
						market.getMyOrders().get(0).getState(), 
						market.getMyOrders().get(0).getState(), orderState.Ordered);		
		assertEquals("Order should have MockMarketCustomer as Customer. It doesn't.", 
						market.getMyOrders().get(0).getCustomer(), customer);
		assertEquals("Order should have groceryList as order. It doesn't.", 
						market.getMyOrders().get(0).getGroceryList(), groceryList);

		//Step 2-----------------------------------------------------------------------------
		assertTrue("Market scheduler should've returned true. It didn't",
						market.pickAndExecuteAnAction());

		//Check Postconditions of Step 2/Preconditions of Step 3-----------------------------
		assertEquals("Market should have 2 logged events. Instead, the Market's event log reads: "
				+ market.log.toString(), 2, market.log.size());
		
		assertEquals("Order should have state 'Filled'. State is instead: " + 
				market.getMyOrders().get(0).getState(), 
				market.getMyOrders().get(0).getState(), orderState.Filled);	
		assertEquals("Order should have price of $7.00. Price is instead: $" + 
				market.getMyOrders().get(0).getPrice(), 
				market.getMyOrders().get(0).getPrice(), 7.00);	
		
		assertEquals("Order should have retrieved all groceries. It didn't.", 
				market.getMyOrders().get(0).getRetrievedGroceries(), groceryList);
		
		//Step 3-----------------------------------------------------------------------------
		assertTrue("Market scheduler should've returned true. It didn't",
						market.pickAndExecuteAnAction());
		
		//Check Postconditions of Step 3/Preconditions of Step 4-----------------------------
		assertEquals("Market should have 3 logged events. Instead, the Market's event log reads: "
				+ market.log.toString(), 3, market.log.size());
		
		assertEquals("Order should have state 'Billed'. State is instead: " + 
				market.getMyOrders().get(0).getState(), 
				market.getMyOrders().get(0).getState(), orderState.Billed);	
		
		assertEquals("MarketCustomer should have 1 logged event. Instead, the event log reads: "
				+ customer.log.toString(), 1, customer.log.size());
		assertEquals("MarketCustomer should have been charged $7.00. Instead, the price was: $"
				+ customer.price, customer.price, 7.00);
		
		//Step 4-----------------------------------------------------------------------------
		market.msgHereIsMoney(customer, 7.00);
		
		//Check Postconditions of Step 4/Preconditions of Step 5-----------------------------
		assertEquals("Market should have 4 logged events. Instead, the Market's event log reads: "
				+ market.log.toString(), 4, market.log.size());
		
		assertEquals("Order should have state 'Paid'. State is instead: " + 
				market.getMyOrders().get(0).getState(), 
				market.getMyOrders().get(0).getState(), orderState.Paid);	
		
		//Step 5-----------------------------------------------------------------------------
		assertTrue("Market scheduler should've returned true. It didn't",
						market.pickAndExecuteAnAction());
		
		//Check Postconditions of Step 5-----------------------------------------------------
		assertEquals("Market should have 5 logged events. Instead, the Market's event log reads: "
						+ market.log.toString(), 5, market.log.size());

		assertEquals("Market should have 0 orders. It doesn't", market.getMyOrders().size(), 0);

		assertEquals("MarketCustomer should have 2 logged events. Instead, the event log reads: "
						+ customer.log.toString(), 2, customer.log.size());
		assertEquals("MarketCustomer should have received groceries. It didn't",
						customer.groceries, groceryList);
		
	}	
}
