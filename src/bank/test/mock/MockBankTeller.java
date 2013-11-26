package bank.test.mock;

import java.util.Map;

import bank.interfaces.BankCustomer;
import bank.interfaces.BankTeller;
import market.interfaces.Market;
import market.interfaces.MarketCustomer;

public class MockBankTeller extends Mock implements BankTeller {

	public MarketCustomer customer;
	public EventLog log;
	public Map<String, Integer> groceries;

	public MockBankTeller(String name) {
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
	public void msgAssigningCustomer(BankCustomer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgOpenAccount(BankCustomer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDepositMoney(int accountNumber, double money) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWithdrawMoney(int accountNumber, double money) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgIWantLoan(int accountNumber, double moneyRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgThankYouForAssistance(BankCustomer bankCustomer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGoToRegister(int registerNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneWorking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAtRegister() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAtManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAnimationFinishedLeavingBank() {
		// TODO Auto-generated method stub
		
	}
}
