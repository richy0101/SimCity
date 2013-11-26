package bank.test.mock;

import java.util.Map;

import bank.interfaces.BankCustomer;
import bank.interfaces.BankManager;
import bank.interfaces.BankTeller;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;
import bank.test.mock.LoggedEvent;

public class MockBankManager extends Mock implements BankManager {

	public MarketCustomer customer;
	public EventLog log;
	public Map<String, Integer> groceries;

	public MockBankManager(String name) {
		super(name);
		log = new EventLog();
	}
	/*
	@Override
	public void msgGetGroceries(MarketCustomer customer, Map<String, Integer> groceryList) {
		log.add(new LoggedEvent("Received msgGetGroceries from MarketCustomer"));
		groceries = groceryList;
	}

	@Override
	public void msgHereIsMoney(MarketCustomer customer, double money) {
		log.add(new LoggedEvent("Received msgHereIsMoney from MarketCustomer. Money = $" + money));
	}

	@Override
	public void msgCantAffordGroceries(MarketCustomer customer) {
		log.add(new LoggedEvent("Received msgCantAffordGroceries from MarketCustomer"));
	}
	*/

	@Override
	public void msgINeedAssistance(BankCustomer customer) {
		log.add(new LoggedEvent("Customer needs assistance"));
		
	}

	@Override
	public void msgTellerFree(BankTeller teller) {
		log.add(new LoggedEvent("Teller is free"));
		
	}

	@Override
	public void msgHereForWork(BankTeller teller) {
		log.add(new LoggedEvent("Teller is here for work"));
		
	}
}
