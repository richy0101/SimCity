package restaurant;
import java.util.ArrayList;
import restaurant.*;
import restaurant.interfaces.*;

import restaurant.CashierAgent.OrderState;
public class Check{
	//Check for a customer
	public String name;
	public double moneyOwed;
	public OrderState state;
	public Waiter waiter;
	
	//Check for paying a market
	public MarketAgent market = null;
	
	public Check(double money,OrderState s,MarketAgent m){
		market = m;
		moneyOwed = money;
		state = s;
	}
	public Check(String n, double mon, OrderState s,Waiter w){
		name = n;
		moneyOwed = mon;
		state = s;
		waiter = w;
	}
}
