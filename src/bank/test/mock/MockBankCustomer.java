package bank.test.mock;

import java.util.Map;

import bank.interfaces.BankCustomer;
import bank.interfaces.BankTeller;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;


public class MockBankCustomer extends Mock implements BankCustomer {

	public Market market;
	public EventLog log;
	public double price;
	public Map<String, Integer> groceries;

	public MockBankCustomer (String name) {
		super(name);
		log = new EventLog();

	}
	
	//MARKET
	/*
	@Override
	public void msgHereIsBill(double price) {
		log.add(new LoggedEvent("Received msgHereIsBill from Market. Price = $" + price));
		this.price = price;
	}

	@Override
	public void msgHereAreYourGroceries(Map<String, Integer> groceries) {
		log.add(new LoggedEvent("Received msgHereAreYourGroceries from Market."));
		this.groceries = groceries;
	}

	@Override
	public void msgCantFillOrder(Map<String, Integer> groceries) {
		log.add(new LoggedEvent("Received msgCantFillOrder from Market."));
		this.groceries = groceries;
	}

	@Override
	public void msgHowCanIHelpYou(BankTeller teller, int tellerNumber) {
		// TODO Auto-generated method stub
		
	}
	*/
	
	//BANK 

	@Override
	public void msgLoanDenied() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereAreFunds(double funds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsYourAccount(int accountNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDepositSuccessful() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHowCanIHelpYou(BankTeller teller, int tellerNumber) {
		// TODO Auto-generated method stub
		
	}
}
